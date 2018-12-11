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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.powermock.api.mockito.PowerMockito;

import com.amplitude.api.Amplitude;
import com.amplitude.api.AmplitudeClient;
import com.amplitude.api.Identify;
import com.amplitude.api.Revenue;

import android.app.Application;
import android.content.Context;
import android.util.Pair;
import okhttp3.OkHttpClient;

/**
 * A mock for AmplitudeClient that collects events in a list instead of sending
 * them to Amplitude. It also collects user properties in a JSONObject.
 *
 * @author Inderjeet Singh
 */
public final class AmplitudeClientMock extends AmplitudeClient {
    private List<Pair<String, JSONObject>> events = new ArrayList<>();
    private JSONObject userProperties = new JSONObject();

    @Override
    public void setUserProperties(final JSONObject userProps) {
        @SuppressWarnings("unchecked")
        Iterator<String> it = userProps.keys();
        while (it.hasNext()) {
            String k = it.next();
            try {
                userProperties.put(k, userProps.get(k));
            } catch (JSONException e) {
                // do nothing
            }
        }
    }

    @Override
    public void logEvent(String eventType, JSONObject eventProperties) {
        events.add(Pair.create(eventType, eventProperties));
    }

    @Override
    public AmplitudeClient initialize(Context context, String apiKey, String userId) {
        return this;
    }

    @Override
    public AmplitudeClient enableForegroundTracking(Application app) {
        return this;
    }

    public void clearUserProperties() {
        userProperties = new JSONObject();
    }

    public JSONObject getUserProperties() {
        return userProperties;
    }

    public List<Pair<String, JSONObject>> getEvents() {
        return events;
    }

    @Override
    public AmplitudeClient initialize(Context context, String apiKey) {
        return this;
    }

    @Override
    public AmplitudeClient enableNewDeviceIdPerInstall(boolean newDeviceIdPerInstall) {
        return this;
    }

    @Override
    public AmplitudeClient useAdvertisingIdForDeviceId() {
        return this;
    }

    @Override
    public AmplitudeClient enableLocationListening() {
        return this;
    }

    @Override
    public AmplitudeClient disableLocationListening() {
        return this;
    }

    @Override
    public AmplitudeClient setEventUploadThreshold(int eventUploadThreshold) {
        return this;
    }

    @Override
    public AmplitudeClient setEventUploadMaxBatchSize(int eventUploadMaxBatchSize) {
        return this;
    }

    @Override
    public AmplitudeClient setEventMaxCount(int eventMaxCount) {
        return this;
    }

    @Override
    public AmplitudeClient setEventUploadPeriodMillis(int eventUploadPeriodMillis) {
        return this;
    }

    @Override
    public AmplitudeClient setMinTimeBetweenSessionsMillis(long minTimeBetweenSessionsMillis) {
        return this;
    }

    @Override
    public AmplitudeClient setSessionTimeoutMillis(long sessionTimeoutMillis) {
        return this;
    }

    @Override
    public AmplitudeClient setOptOut(boolean optOut) {
        return this;
    }

    @Override
    public boolean isOptedOut() {
        return false;
    }

    @Override
    public AmplitudeClient enableLogging(boolean enableLogging) {
        return this;
    }

    @Override
    public AmplitudeClient setLogLevel(int logLevel) {
        return this;
    }

    @Override
    public AmplitudeClient setOffline(boolean offline) {
        return this;
    }

    @Override
    public AmplitudeClient setFlushEventsOnClose(boolean flushEventsOnClose) {
        return this;
    }

    @Override
    public AmplitudeClient trackSessionEvents(boolean trackingSessionEvents) {
        return this;
    }

    @Override
    public void logEvent(String eventType) {
        // do nothing
    }

    @Override
    public void logEvent(String eventType, JSONObject eventProperties, boolean outOfSession) {
        // do nothing
    }

    @Override
    public void logEvent(String eventType, JSONObject eventProperties, JSONObject groups) {
        // do nothing
    }

    @Override
    public void logEvent(String eventType, JSONObject eventProperties, JSONObject groups, boolean outOfSession) {
        // do nothing
    }

    @Override
    public void logEvent(String eventType, JSONObject eventProperties, JSONObject groups, long timestamp,
        boolean outOfSession) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType, JSONObject eventProperties) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType, JSONObject eventProperties, boolean outOfSession) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType, JSONObject eventProperties, JSONObject groups) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType, JSONObject eventProperties, JSONObject groups, boolean outOfSession) {
        // do nothing
    }

    @Override
    public void logEventSync(String eventType, JSONObject eventProperties, JSONObject groups, long timestamp,
        boolean outOfSession) {
        // do nothing
    }

    @Override
    protected boolean validateLogEvent(String eventType) {
        return false;
    }

    @Override
    protected long saveEvent(String eventType, JSONObject event) {
        return -1;
    }

    @Override
    public long getSessionId() {
        return -1;
    }

    @Override
    public void logRevenue(double amount) {
        // do nothing
    }

    @Override
    public void logRevenue(String productId, int quantity, double price) {
        // do nothing
    }

    @Override
    public void logRevenue(String productId, int quantity, double price, String receipt, String receiptSignature) {
        // do nothing
    }

    @Override
    public void logRevenueV2(Revenue revenue) {
        // do nothing
    }

    @Override
    public void setUserProperties(JSONObject userProperties, boolean replace) {
        // do nothing
    }

    @Override
    public void identify(Identify identify) {
        // do nothing
    }

    @Override
    public void identify(Identify identify, boolean outOfSession) {
        // do nothing
    }

    @Override
    public void setGroup(String groupType, Object groupName) {
        // do nothing
    }

    @Override
    public JSONObject truncate(JSONObject object) {
        return null;
    }

    @Override
    public JSONArray truncate(JSONArray array) {
        return null;
    }

    @Override
    public String truncate(String value) {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public AmplitudeClient setUserId(String userId) {
        return this;
    }

    @Override
    public AmplitudeClient setDeviceId(String deviceId) {
        return this;
    }

    @Override
    public AmplitudeClient regenerateDeviceId() {
        return this;
    }

    @Override
    public void uploadEvents() {
        // do nothing
    }

    @Override
    protected void updateServer() {
        // do nothing
    }

    @Override
    protected void updateServer(boolean limit) {
        // do nothing
    }

    @Override
    protected Pair<Pair<Long, Long>, JSONArray> mergeEventsAndIdentifys(List<JSONObject> events,
        List<JSONObject> identifys, long numEvents) {
        return null;
    }

    @Override
    protected void makeEventUploadPostRequest(OkHttpClient client, String events, long maxEventId, long maxIdentifyId) {
        // do nothing
    }

    @Override
    public String getDeviceId() {
        return null;
    }

    @Override
    protected void runOnLogThread(Runnable r) {
        // do nothing
    }

    @Override
    protected Object replaceWithJSONNull(Object obj) {
        return null;
    }

    @Override
    protected synchronized boolean contextAndApiKeySet(String methodName) {
        return false;
    }

    @Override
    protected String bytesToHexString(byte[] bytes) {
        return null;
    }

    @Override
    protected long getCurrentTimeMillis() {
        return -1;
    }

    /**
     * Returns a mocked client that will contain the amplitude events that are sent
     * when AmplitudeTracker.send(Map eventMap) is invoked, and this can be used to
     * assert against captured events
     */
    public static AmplitudeClientMock create(String amplitudeProjectId) throws Exception {
        PowerMockito.mockStatic(Amplitude.class);

        // Prevent initializations of problematic fields (such as WorkerThreads) of AmplitudeClient
        PowerMockito.mockStatic(AmplitudeClient.class);
        PowerMockito.suppress(PowerMockito.constructor(AmplitudeClient.class, String.class));

        AmplitudeClientMock amplitudeClientMock = new AmplitudeClientMock();
        PowerMockito.when(Amplitude.getInstance()).thenReturn(amplitudeClientMock);
        return amplitudeClientMock;
    }
}
