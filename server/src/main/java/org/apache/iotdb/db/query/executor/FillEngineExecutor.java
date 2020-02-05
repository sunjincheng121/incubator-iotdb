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

package org.apache.iotdb.db.query.executor;

import javax.activation.UnsupportedDataTypeException;
import org.apache.iotdb.db.conf.IoTDBDescriptor;
import org.apache.iotdb.db.exception.StorageEngineException;
import org.apache.iotdb.db.exception.query.QueryProcessException;
import org.apache.iotdb.db.query.context.QueryContext;
import org.apache.iotdb.db.query.dataset.SingleDataSet;
import org.apache.iotdb.db.query.fill.IFill;
import org.apache.iotdb.db.query.fill.LinearFill;
import org.apache.iotdb.db.query.fill.PreviousFill;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;
import org.apache.iotdb.tsfile.read.TimeValuePair;
import org.apache.iotdb.tsfile.read.common.Field;
import org.apache.iotdb.tsfile.read.common.Path;
import org.apache.iotdb.tsfile.read.common.RowRecord;
import org.apache.iotdb.tsfile.read.query.dataset.QueryDataSet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FillEngineExecutor {

  private List<Path> selectedSeries;
  private List<TSDataType> dataTypes;
  private long queryTime;
  private Map<TSDataType, IFill> typeIFillMap;

  public FillEngineExecutor(List<Path> selectedSeries,
      List<TSDataType> dataTypes,
      long queryTime,
      Map<TSDataType, IFill> typeIFillMap) {
    this.selectedSeries = selectedSeries;
    this.queryTime = queryTime;
    this.typeIFillMap = typeIFillMap;
    this.dataTypes = dataTypes;
  }

  /**
   * execute fill.
   *
   * @param context query context
   */
  public QueryDataSet execute(QueryContext context)
      throws StorageEngineException, QueryProcessException, IOException {
    RowRecord record = new RowRecord(queryTime);

    for (int i = 0; i < selectedSeries.size(); i++) {
      Path path = selectedSeries.get(i);
      TSDataType dataType = dataTypes.get(i);
      IFill fill;
      long defaultFillInterval = IoTDBDescriptor.getInstance().getConfig().getDefaultFillInterval();
      if (!typeIFillMap.containsKey(dataType)) {
        switch (dataType) {
          case INT32:
          case INT64:
          case FLOAT:
          case DOUBLE:
            fill = new LinearFill(dataType, queryTime, defaultFillInterval, defaultFillInterval);
            break;
          case BOOLEAN:
          case TEXT:
            fill = new PreviousFill(dataType, queryTime,
                IoTDBDescriptor.getInstance().getConfig().getDefaultFillInterval());
            break;
          default:
            throw new UnsupportedDataTypeException("do not support datatype " + dataType);
        }
      } else {
        fill = typeIFillMap.get(dataType).copy();
      }
      fill.setDataType(dataType);
      fill.setQueryTime(queryTime);
      fill.constructReaders(path, context);

      TimeValuePair timeValuePair = fill.getFillResult().nextTimeValuePair();
      if (timeValuePair.getValue() == null) {
        record.addField(new Field(null));
      } else {
        record.addField(timeValuePair.getValue().getValue(), dataType);
      }
    }

    SingleDataSet dataSet = new SingleDataSet(selectedSeries, dataTypes);
    dataSet.setRecord(record);
    return dataSet;
  }
}
