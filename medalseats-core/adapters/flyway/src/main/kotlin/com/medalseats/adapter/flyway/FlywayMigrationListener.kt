package com.medalseats.adapter.flyway

import io.kotest.core.listeners.ProjectListener
import io.kotest.core.spec.AutoScan
import org.flywaydb.core.Flyway

@AutoScan
object FlywayMigrationListener : ProjectListener {

    override suspend fun beforeProject() {
        Flyway.configure()
            .dataSource(
                "jdbc:postgresql://localhost:5432/medalseats",
                "medalseats",
                "medalseats"
            )
            .load()
            .migrate()
    }
}
