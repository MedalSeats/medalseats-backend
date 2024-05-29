group = "com.medalseats.application"

dependencies {
    implementation(projects.medalseatsCore.domain)
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
