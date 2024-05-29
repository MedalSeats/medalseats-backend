group = "com.medalseats.adapter.http"

plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlinx.coroutines.slf4j)
    implementation(libs.jwt)
    implementation(libs.valiktor.core)
    implementation(libs.kotlin.logging)

    implementation(projects.medalseatsCore.adapters.http.common)
    implementation(projects.medalseatsCore.domain)
    implementation(projects.medalseatsCore.application.command)

    testImplementation(libs.spring.boot.starter.test)
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
