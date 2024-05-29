group = "com.medalseats.adapter"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    implementation(projects.medalseatsCore.domain)
    implementation(libs.spring.security.crypto)
    implementation(libs.bcprov.jdk15on)
    testImplementation(libs.kotlinx.coroutines.reactor)
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
