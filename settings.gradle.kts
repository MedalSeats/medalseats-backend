rootProject.name = "medalseats"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "medalseats-core:domain",
    "medalseats-core:adapters:r2dbc",
    "medalseats-core:adapters:flyway",
    "medalseats-core:adapters:cryptography",
    "medalseats-core:adapters:payment-process",
    "medalseats-core:adapters:http:common",
    "medalseats-core:adapters:http:query",
    "medalseats-core:adapters:http:command",
    "medalseats-core:application:query",
    "medalseats-core:application:command",
)

include(
    "medalseats-deployments:medalseats-management"
)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
