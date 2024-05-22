plugins {
    alias(libs.plugins.flyway)
}

group = "com.medalseats.adapter"

dependencies {
    implementation(libs.flyway.core)
    implementation(libs.kotest.framework.api)
    runtimeOnly(libs.jdbc.postgresql)
}

flyway {
    url = "jdbc:postgresql://localhost:5432/medalseats"
    user = "medalseats"
    password = "medalseats"
    schemas = arrayOf("public")
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
