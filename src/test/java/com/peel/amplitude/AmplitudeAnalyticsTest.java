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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.amplitude.api.Amplitude;
import com.amplitude.api.AmplitudeClient;
import com.google.gson.Gson;
import com.peel.prefs.SharedPrefs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Functional tests for {@link AmplitudeAnalytics}.
 *
 * @author Inderjeet Singh
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Application.class, Context.class, SharedPreferences.class, PreferenceManager.class,
    Log.class, Amplitude.class, AmplitudeClient.class})
public class AmplitudeAnalyticsTest {

    private Application app;
    @Before
    public void setUp() throws Exception {
        app = AndroidFixtures.createMockApplication();
        Mockito.mock(Log.class);
        SharedPrefs.init(app.getApplicationContext(), new Gson());
        AmplitudeClientMock.create("abc123");
    }

    @Test
    public void testAmplitudeSelected() {
        // set a 100% success coin toss
        AmplitudeAnalytics analytics = new AmplitudeAnalytics(app, "abc123", 1);
        assertTrue(analytics.selected());
    }

    @Test
    public void testSkipAmplitude() {
        // set a very low probability of coin toss to ensure this test passes
        AmplitudeAnalytics analytics = new AmplitudeAnalytics(app, "abc123", 10000);
        assertFalse(analytics.selected());
    }
}
