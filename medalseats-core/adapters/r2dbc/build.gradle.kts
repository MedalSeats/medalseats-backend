group = "com.medalseats.adapter"

dependencies {
    implementation(platform(rootProject.libs.spring.boot.bom))

    implementation(libs.spring.boot.starter.data.r2dbc)
    implementation(projects.medalseatsCore.domain)

    testImplementation(libs.r2dbc.postgresql)
    testImplementation(libs.kotlinx.coroutines.reactor)
    //testRuntimeOnly(projects.medalseatsCore.adapters.flyway)
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

    test{
        maxParallelForks=1
    }
}
