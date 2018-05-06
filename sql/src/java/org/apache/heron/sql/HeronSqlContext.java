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
package org.apache.heron.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.util.ChainedSqlOperatorTable;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.ValidationException;
import org.apache.heron.sql.compiler.HeronSqlTypeFactory;
import org.apache.heron.sql.parser.SqlCreateFunction;
import org.apache.heron.sql.parser.SqlCreateTable;
import org.apache.heron.sql.planner.HeronRelUtils;

public class HeronSqlContext {
  private boolean hasUdf = false;
  private final SchemaPlus schema = Frameworks.createRootSchema(true);
  private final JavaTypeFactory typeFactory = new HeronSqlTypeFactory(
      RelDataTypeSystem.DEFAULT);

  public void interpretCreateTable(SqlCreateTable sqlCreateTable)  {

  }

  public void interpretCreateFunction(SqlCreateFunction sqlCreateFunction) {

  }

  public String explain(String query) throws SqlParseException, ValidationException, RelConversionException {
    FrameworkConfig config = buildFrameWorkConfig();
    Planner planner = Frameworks.getPlanner(config);
    SqlNode parse = planner.parse(query);
    SqlNode validate = planner.validate(parse);
    RelNode tree = planner.convert(validate);

    return HeronRelUtils.explain(tree, SqlExplainLevel.ALL_ATTRIBUTES);
  }

  public FrameworkConfig buildFrameWorkConfig() {
    // todo: understand what needs to be set into config
    Properties props = new Properties();
    CalciteConnectionConfig cfg = new CalciteConnectionConfigImpl(props);
    if (hasUdf) {
      List<SqlOperatorTable> sqlOperatorTables = new ArrayList<>();
      sqlOperatorTables.add(SqlStdOperatorTable.instance());
      sqlOperatorTables.add(new CalciteCatalogReader(CalciteSchema.from(schema),
          Collections.<String>emptyList(), typeFactory, cfg));
      return Frameworks.newConfigBuilder().defaultSchema(schema)
          .operatorTable(new ChainedSqlOperatorTable(sqlOperatorTables)).build();
    } else {
      return Frameworks.newConfigBuilder().defaultSchema(schema).build();
    }
  }
}
