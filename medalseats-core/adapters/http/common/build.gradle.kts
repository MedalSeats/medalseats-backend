group = "com.medalseats.adapter.http"

plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.valiktor.core)
    implementation(libs.kotlin.logging)

    implementation(projects.medalseatsCore.domain)

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
