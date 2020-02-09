<!--

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->
# TsFile-Flink-Connector User Guide

## 1. About TsFile-Flink-Connector

TsFile-Flink-Connector implements the support of Flink for external data sources of Tsfile type. This enables users to read, write and query Tsfile by Flink.

With this connector, you can
* load a single TsFile, from either the local file system or hdfs, into Flink
* load all files in a specific directory, from either the local file system or hdfs, into Flink
* write data from Flink into TsFile

## 2. System Requirements

|Flink Version | Scala Version | Java Version | TsFile |
|------------- | ------------- | ------------ |------------ |
| `1.9.2`        | `2.11.8`        | `1.8`        | `0.10.0`|

> Note: For more information about how to download and use TsFile, please see the following link: https://github.com/apache/incubator-iotdb/tree/master/tsfile.

## 3. Quick Start
### Local Mode

### Distributed Mode

## 4. Data Type Correspondence

| TsFile data type | SparkSQL data type|
| --------------| -------------- |
| BOOLEAN       		   | BooleanType    |
| INT32       		   | IntegerType    |
| INT64       		   | LongType       |
| FLOAT       		   | FloatType      |
| DOUBLE      		   | DoubleType     |
| TEXT      				| StringType     |

## 5. Schema Inference

The way to display TsFile is dependent on the schema. Take the following TsFile structure as an example: There are three Measurements in the TsFile schema: status, temperature, and hardware. The basic information of these three measurements is as follows:

<center>
<table style="text-align:center">
	<tr><th colspan="2">Name</th><th colspan="2">Type</th><th colspan="2">Encode</th></tr>
	<tr><td colspan="2">status</td><td colspan="2">Boolean</td><td colspan="2">PLAIN</td></tr>
	<tr><td colspan="2">temperature</td><td colspan="2">Float</td><td colspan="2">RLE</td></tr>
	<tr><td colspan="2">hardware</td><td colspan="2">Text</td><td colspan="2">PLAIN</td></tr>
</table>
</center>

The existing data in the TsFile is as follows:


<center>
<table style="text-align:center">
	<tr><th colspan="4">device:root.ln.wf01.wt01</th><th colspan="4">device:root.ln.wf02.wt02</th></tr>
	<tr><th colspan="2">status</th><th colspan="2">temperature</th><th colspan="2">hardware</th><th colspan="2">status</th></tr>
	<tr><th>time</th><th>value</td><th>time</th><th>value</td><th>time</th><th>value</th><th>time</th><th>value</td></tr>
	<tr><td>1</td><td>True</td><td>1</td><td>2.2</td><td>2</td><td>"aaa"</td><td>1</td><td>True</td></tr>
	<tr><td>3</td><td>True</td><td>2</td><td>2.2</td><td>4</td><td>"bbb"</td><td>2</td><td>False</td></tr>
	<tr><td>5</td><td> False </td><td>3</td><td>2.1</td><td>6</td><td>"ccc"</td><td>4</td><td>True</td></tr>
</table>
</center>



## 6. Scala API

NOTE: Remember to assign necessary read and write permissions in advance.

### Example 1: read from the local file system


### Example 2: read from the hadoop file system


### Example 3: read from a specific directory


### Example 4: query in wide form

### Example 5: query in narrow form

### Example 6: write in wide form

## Example 6: write in narrow form


mvn clean install -DskipTests -Dmaven.multiModuleProjectDirectory=/Users/jincheng/work/iotdb_dev/
