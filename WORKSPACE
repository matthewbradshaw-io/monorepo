workspace(name = "io_matthewbradshaw")

load("//:repositories-bazel.bzl", "io_matthewbradshaw_bazel_repositories")

io_matthewbradshaw_bazel_repositories()

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("//:repositories-maven.bzl", "io_matthewbradshaw_maven_repositories")

io_matthewbradshaw_maven_repositories()

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains(version = "1.18.3")

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

load(
    "@io_bazel_rules_kotlin//kotlin:repositories.bzl",
    "kotlin_repositories",
)

kotlin_repositories()

load(
    "@io_bazel_rules_kotlin//kotlin:core.bzl",
    "kt_register_toolchains",
)

kt_register_toolchains()
