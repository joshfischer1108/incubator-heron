/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.heron.sql;

import java.util.Map;

import org.apache.calcite.sql.SqlNode;

public class HeronSqlImpl extends HeronSql {

  private final HeronSqlContext sqlContext;

  public HeronSqlImpl() {
    sqlContext = new HeronSqlContext();
  }

  @Override
  public void submit(String name, Iterable<String> statements,
                     Map<String, Object> topoConf) throws Exception {
    for (String sql: statements) {
      HeronParser parser = new HeronParser(sql);
      SqlNode node = parser.impl().parseSqlStmtEof();
      if (node instanceof SqlCreateTable) {
        sqlContext.interpretCreateTable((SqlCreateTable) node);
      } else if (node instanceof SqlCreateFunction) {
        sqlContext.interpretCreateFunction((SqlCreateFunction) node);
      } else {
        // Topology statements have been parsed
        // Create Heron Topology Here
      }
    }

  }

  @Override
  public void explain(Iterable<String> statements) throws Exception {
    for (String sql : statements) {
      HeronParser parser = new HeronParser(sql);
      SqlNode node = parser.impl().parseSqlStmtEof();

      System.out.println("===========================================================");
      System.out.println("query>");
      System.out.println(sql);
      System.out.println("-----------------------------------------------------------");

      if (node instanceof SqlCreateTable) {
        sqlContext.interpretCreateTable((SqlCreateTable) node);
        System.out.println("No plan presented on DDL");
      } else if (node instanceof SqlCreateFunction) {
        sqlContext.interpretCreateFunction((SqlCreateFunction) node);
        System.out.println("No plan presented on DDL");
      } else {
        String plan = sqlContext.explain(sql);
        System.out.println("plan>");
        System.out.println(plan);
      }

      System.out.println("===========================================================");
    }
  }
}

