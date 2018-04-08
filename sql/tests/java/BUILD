test_deps_files = [
    "//third_party/java:powermock",
    "//third_party/java:mockito",
    "//third_party/java:junit4",
]


java_test(
    name = "SqlTest",
    srcs = glob(["com/twitter/heron/sql/HeronSqlRunnerTest.java"]),
    deps = test_deps_files,
    size = "small",
)