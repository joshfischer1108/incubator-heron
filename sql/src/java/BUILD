licenses(["notice"])

package(default_visibility = ["//visibility:public"])

sql_deps = [
    "@apache_calcite//jar",
    "@commons_cli_commons_cli//jar",
    "@apache_calcite_avatica//jar",
]
generated_files = [
    "ParseException.java",
    "Token.java",
    "TokenMgrError.java",
    "SimpleCharStream.java",
    "HeronSqlParserImpl.java",
    "HeronSqlParserImplConstants.java",
    "HeronSqlParserImplTokenManager.java",
]

#genrule(
#    name = "parser-srcs",
#    srcs = ["com/twitter/heron/sql/parser/impl/Parser.jj"],
#    outs = generated_files, 
#    tools = [
#        "//third_party/java:javacc",
#    ],
#    cmd = "\n".join([
#        'export INSTALL_DIR=$$(pwd)/$(@D)',
#        '$(location //third_party/java:javacc) -lookAhead=2 -OUTPUT_DIRECTORY=$$INSTALL_DIR $<',
#    ]),
#)

java_library(
    name = "sql-java",
    srcs = glob(["com/twitter/heron/sql/*.java"]),
    deps = sql_deps
)

