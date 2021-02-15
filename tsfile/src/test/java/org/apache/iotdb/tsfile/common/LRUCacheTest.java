/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iotdb.tsfile.common;

import java.io.IOException;
import org.apache.iotdb.tsfile.common.cache.LRUCache;
import org.junit.Assert;
import org.junit.Test;

public class LRUCacheTest {

    private LRUCache<Integer, Integer> cache;

    @Test
    public void test() {
        try {
            int testCount = 1000;
            int cacheSize = 5;
            cache =
                    new LRUCache<Integer, Integer>(cacheSize) {

                        @Override
                        public Integer loadObjectByKey(Integer key) {
                            return key * 10;
                        }
                    };

            for (int i = 1; i < testCount; i++) {
                Assert.assertEquals(i * 10, (int) cache.get(i));
                Assert.assertEquals((i - 1) * 10, (int) cache.get(i - 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
