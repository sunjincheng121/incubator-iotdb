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
package org.apache.iotdb.tsfile.read.reader;

import java.io.IOException;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.common.BatchData;
import org.apache.iotdb.tsfile.read.filter.basic.Filter;

public class FakedMultiBatchReader implements IBatchReader {

  private long time = 0;
  private int batchSize;
  private int batches;
  private int batchIndex = 0;
  private Filter filter;

  FakedMultiBatchReader(int batchSize, int batches, Filter filter) {
    this.batchSize = batchSize;
    this.batches = batches;
    this.filter = filter;
  }

  @Override
  public boolean hasNextBatch() {
    return batchIndex < batches;
  }

  @Override
  public BatchData nextBatch() {
    batchIndex++;
    BatchData batchData = new BatchData(TSDataType.INT64);
    for (int i = 0; i < batchSize; i++) {
      if (filter.satisfy(time, time)) {
        batchData.putLong(time, time);
      }
      time++;
    }
    return batchData;
  }

  @Override
  public void close() {

  }
}
