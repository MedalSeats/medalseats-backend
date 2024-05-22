plugins {
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
}

group = "com.unicamp.medalseats.deployments"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    implementation(libs.kotlin.logging)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.micrometer.core)
    implementation(libs.r2dbc.pool)
    implementation(libs.r2dbc.postgresql)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.core)
    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(libs.spring.boot.starter.log4j2)
    implementation(libs.spring.boot.starter.webflux)

    implementation(projects.medalseatsCore.domain)
    implementation(projects.medalseatsCore.adapters.flyway)
    implementation(projects.medalseatsCore.adapters.http.common)
    implementation(projects.medalseatsCore.adapters.http.query)
    implementation(projects.medalseatsCore.adapters.r2dbc)
    implementation(projects.medalseatsCore.application.query)

    runtimeOnly(libs.micrometer.registry.prometheus)
    runtimeOnly(libs.disruptor)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotest.extensions.spring)

    testRuntimeOnly(projects.medalseatsCore.adapters.flyway)
}

tasks {
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    minimum = "0.00".toBigDecimal()
                }
            }
        }
    }
}

configurations.all {
    exclude(module = "spring-boot-starter-logging")
}
