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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CoinTossTest {

    @Test
    public void testTossCoinGuaranteedSelection() {
        assertTrue(AmplitudeAnalytics.tossCoin(1)); // 1 implies 100% success
    }

    @Test
    public void testTossCoinInvalidValues() {
        assertTrue(AmplitudeAnalytics.tossCoin(-1)); // -1 is treated as 1
        assertTrue(AmplitudeAnalytics.tossCoin(0));  // 0 is treated as 1
    }

    @Test
    public void testTossCoinProbabilisticSelection() {
        // for positive values, we can't guarantee results, but can make a probabilistic estimate
        // for 10,000 tries, we should get approximately 2,000 true for 1:5 chance
        int count = 0;
        for (int i = 0; i < 10000; ++i) {
            boolean success = AmplitudeAnalytics.tossCoin(5);
            if (success) ++count;
        }
        System.err.println("Count: " + count);
        assertTrue("count: " + count, count > 1750 && count < 2250);
    }
}
