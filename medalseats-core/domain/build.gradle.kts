group = "com.unicamp.medalseats.domain"

dependencies {
    implementation(libs.valiktor.core)
    implementation(libs.valiktor.javatime)
    implementation(libs.valiktor.javamoney)
}

tasks {
    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    minimum = "0.0".toBigDecimal()
                }
            }
        }
    }
}
