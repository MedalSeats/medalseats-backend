rootProject.name = "medalseats"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    "medalseats-core:domain"
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
