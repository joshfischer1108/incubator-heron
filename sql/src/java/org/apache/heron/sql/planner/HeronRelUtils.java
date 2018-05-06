//  Copyright 2018 Twitter. All rights reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
package org.apache.heron.sql.planner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.sql.SqlExplainLevel;

public class HeronRelUtils {

  private static final Logger LOG = LoggerFactory.getLogger(HeronRelUtils.class);


  public static String explain(final RelNode rel, SqlExplainLevel detailLevel) {
    String explain = "";
    try {
      explain = RelOptUtil.toString(rel);
    } catch (StackOverflowError e) {
      LOG.error("StackOverflowError occurred while extracting plan. Please report it to the dev@ mailing list.");
      LOG.error("RelNode " + rel + " ExplainLevel " + detailLevel, e);
      LOG.error("Forcing plan to empty string and continue... SQL Runner may not working properly after.");
    }
    return explain;
  }

}
