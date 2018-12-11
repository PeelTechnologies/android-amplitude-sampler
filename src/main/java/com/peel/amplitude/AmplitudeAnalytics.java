/*
 * Copyright (C) 2018 Peel Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peel.amplitude;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.amplitude.api.Amplitude;
import com.peel.prefs.SharedPrefs;
import com.peel.prefs.TypedKey;

import android.app.Application;
import android.util.Log;

/**
 * This class is a wrapper over Amplitude. Its purpose is to allow using Amplitude while sampling users.
 * Sampling is essential if the app's user base is large and we want to stay under Amplitude quota
 * under our chosen plan.
 *
 * @author Inderjeet Singh
 */
public class AmplitudeAnalytics {
    private static final String LOG_TAG = AmplitudeAnalytics.class.getName();

    private static final TypedKey<String> AMPLITUDE_PROJECT_ID = new TypedKey<>("amplitudeProjectId", String.class);

    private volatile boolean initialized = false;
    private final int samplingRatio;

    /**
     * Create an Amplitude instance per specified sampling ratio. Ensure {@link SharedPrefs} is
     * initialized before this constructor is called.
     *
     * @param app the Android application instance
     * @param amplitudeProjectId the Amplitude project ID to use if the user is selected for sending data to Amplitude
     * @param samplingRatio the ratio that needs to be maintained while sampling users. 
     * For example 100 means that the user has 1:100 chance of getting chosen in Amplitude.
     */
    public AmplitudeAnalytics(Application app, String amplitudeProjectId, int samplingRatio) {
        this.samplingRatio  = samplingRatio;

        if (!selected()) return;
        try {
            if (!initialized) {
                SharedPrefs.put(AMPLITUDE_PROJECT_ID, amplitudeProjectId);
                Amplitude.getInstance()
                        .initialize(app.getApplicationContext(), amplitudeProjectId)
                        .enableForegroundTracking(app);
                initialized = true;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, LOG_TAG, e);
            initialized = false;
        }
    }

    synchronized boolean selected() {
        Boolean selected = null;
        if (SharedPrefs.contains(AMPLITUDE_PROJECT_ID)) {
            selected = Boolean.TRUE; // already in Amplitude
        }
        
        if (selected == null) {
            TypedKey<Boolean> selectedKey = new TypedKey<>("amplitude_selected", Boolean.class);
            if (SharedPrefs.contains(selectedKey)) { // since SharedPrefs return false if not present, so need to check with contains
                selected = SharedPrefs.get(selectedKey);
            } else { // coin toss hasn't happened yet, so do it
                selected = tossCoin(samplingRatio);  // a user is given 1 in 100 (if samplingRatio == 100) chances to be selected
                SharedPrefs.put(selectedKey, selected);
            }
        }
        return selected;
    }

    private static final Random rand = new Random();

    /**
     * Returns true if the toss results in success. false otherwise.
     *
     * @param numOptions number of options for toss. The probability of returning success is 1/numOptions.
     *                   Must be >= 1
     */
    static boolean tossCoin(int numOptions) {
        if (numOptions <= 0) numOptions = 1;
        return rand.nextInt(numOptions) % numOptions == 0;
    }

    public void setUserProperty(String name, String value) {
        if (!selected()) return;
        try {
            JSONObject prop = new JSONObject();
            prop.put(name, value);
            Amplitude.getInstance().setUserProperties(prop);
        } catch (JSONException e) {
            Log.e(LOG_TAG, LOG_TAG, e);
        }
    }

    public void setUserProperties(JSONObject properties) {
        if (!selected()) return;
        Amplitude.getInstance().setUserProperties(properties);
    }

    @SuppressWarnings("rawtypes")
    public <T extends Enum> void logEvent(T eventName) {
        logEvent(eventName.toString());
    }

    public void logEvent(String eventName) {
        if (!selected()) return;
        Amplitude.getInstance().logEvent(eventName);
    }

    @SuppressWarnings("rawtypes")
    public <T extends Enum> void logEvent(T eventName, JSONObject params) {
        logEvent(eventName.toString(), params);
    }

    public void logEvent(String eventName, JSONObject params) {
        if (!selected()) return;
        Amplitude.getInstance().logEvent(eventName, params);
    }
}
