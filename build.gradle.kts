import com.kageiit.jacobo.JacoboTask

plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlinter)
	alias(libs.plugins.test.logger)
	id("jacoco")
	alias(libs.plugins.jacobo)
	alias(libs.plugins.jacoco.log)
}

group = "com.unicamp.medalseats"
version = "0.1.0"

val kotlinVersion: String by project
val jvmVersion: String by project
val Project.fullName: String get() = (parent?.fullName?.plus("-") ?: "") + name


tasks {
	register<JacoboTask>("jacobo") {
		jacocoReport = file("$buildDir/reports/jacoco/jacocoAggregatedReport/jacocoAggregatedReport.xml")
		coberturaReport = file("$buildDir/reports/cobertura/cobertura.xml")
		setSrcDirs(*subprojects.map { prj -> "${prj.name}/src/main/kotlin" }.toTypedArray())

		dependsOn(jacocoAggregatedReport)
	}

	test {
		maxParallelForks = 3
	}

	check {
		dependsOn(withType<JacoboTask>())
	}
}

subprojects {
	if (file("src/main/kotlin").isDirectory || file("src/main/resources").isDirectory) {
		apply {
			plugin("org.jetbrains.kotlin.jvm")
			plugin("org.jmailen.kotlinter")
			plugin("com.adarshr.test-logger")
			plugin("jacoco")
		}

		dependencies {

			constraints {
				implementation(rootProject.libs.google.guava) { because("Vulnerability found") }
				implementation(rootProject.libs.snakeyaml) { because("Vulnerability found") }
				implementation(rootProject.libs.jackson.databind) { because("Vulnerability found") }
			}

			implementation(platform(rootProject.libs.log4j.bom))

			implementation(rootProject.libs.kotlin.reflect)
			implementation(rootProject.libs.kotlinx.datetime)
			implementation(rootProject.libs.kotlinx.coroutines.core)
			implementation(rootProject.libs.kotlinx.collections.immutable)
			implementation(rootProject.libs.javax.money.api)

			runtimeOnly(rootProject.libs.javax.money.moneta)

			testImplementation(rootProject.libs.mockk)
			testImplementation(rootProject.libs.valiktor.test)
			testImplementation(rootProject.libs.kotest.runner.junit5)
			testImplementation(rootProject.libs.kotest.assertions.core)

			testRuntimeOnly(rootProject.libs.javax.money.moneta)
		}

		configurations.all {
			exclude(group = "org.mockito")
			exclude(group = "javax.validation")
			exclude(module = "hibernate-validator")
			exclude(module = "jakarta.validation-api")
		}

		tasks {
			jar {
				archiveBaseName.set(project.fullName.replaceFirst("${rootProject.name}-", ""))
				archiveVersion.set("")
			}

			compileKotlin {
				kotlinOptions {
					apiVersion = kotlinVersion
					languageVersion = kotlinVersion
					jvmTarget = jvmVersion
					freeCompilerArgs += listOf(
						"-Xjdk-release=$jvmVersion",
						"-Xjsr305=strict",
						"-Xjvm-default=all",
						"-opt-in=kotlin.time.ExperimentalTime"
					)
				}
			}

			compileTestKotlin {
				kotlinOptions {
					apiVersion = kotlinVersion
					languageVersion = kotlinVersion
					jvmTarget = jvmVersion
					freeCompilerArgs += listOf(
						"-Xjdk-release=$jvmVersion",
						"-Xjsr305=strict",
						"-Xjvm-default=all",
						"-opt-in=kotlin.time.ExperimentalTime"
					)
				}
			}

			test {
				useJUnitPlatform()
				jvmArgs(
					"--add-opens=java.base/java.lang=ALL-UNNAMED",
					"--add-opens=java.base/java.util=ALL-UNNAMED"
				)
			}

			lintKotlinMain {
				setSource("src/main/kotlin")
			}

			lintKotlinTest {
				setSource("src/test/kotlin")
			}

			jacocoTestReport {
				reports {
					xml.required.set(true)
					html.required.set(true)
				}

				classDirectories.setFrom(files(classDirectories.files.map {
					fileTree(it).apply {
						exclude("**/**\$beans$**")
						exclude("**/**\$LOGGER$**")
						exclude("**/**\$\$serializer**")
					}
				}))
			}

			jacocoTestCoverageVerification {
				dependsOn(jacocoTestReport)

				classDirectories.setFrom(files(classDirectories.files.map {
					fileTree(it).apply {
						exclude("**/**\$beans$**")
						exclude("**/**\$LOGGER$**")
						exclude("**/**\$\$serializer**")
					}
				}))
			}

			check {
				dependsOn(jacocoTestCoverageVerification)
			}

			register<DependencyReportTask>("allDeps")
		}

		kotlinter {
			disabledRules = arrayOf(
				"trailing-comma"
			)
		}
	}
}
