package com.unicamp.medalseats

import com.medalseats.adapter.cyrptograph.HashCryptographyService
import com.medalseats.adapter.http.command.account.AccountHttpHandler
import com.medalseats.adapter.http.query.match.MatchHttpHandler
import com.medalseats.adapter.http.query.router
import com.medalseats.adapter.http.command.routerManagement
import com.medalseats.adapter.r2dbc.R2dbcTransactionScope
import com.medalseats.adapter.r2dbc.account.AccountR2dbcRepository
import com.medalseats.adapter.r2dbc.match.MatchR2dbcRepository
import com.medalseats.application.command.account.CreateAccountCommandHandler
import com.medalseats.application.query.match.FindMatchByIdQueryHandler
import org.springframework.beans.factory.config.BeanDefinitionCustomizer
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter
import java.util.Locale
import kotlin.reflect.KClass

@SpringBootApplication
@ConfigurationPropertiesScan
class MedalseatsManagement

fun rootRouter() = coRouter {
    GET("") { ok().buildAndAwait() }
}

fun beans(context: GenericApplicationContext) = beans {
    // HTTP handlers
    bean(::rootRouter)
    bean(::router)
    bean(::routerManagement)

    // Repositories
    bean<MatchR2dbcRepository>()
    bean<AccountR2dbcRepository>()

    // Cyrptography
    bean {
        HashCryptographyService(
            ref<MedalseatsManagementProperties>().passwordEncoder
        )
    }

    // HTTP handlers
    bean<MatchHttpHandler>()
    bean<AccountHttpHandler>()

    // Query handlers
    bean<FindMatchByIdQueryHandler>()

    // Command handlers
    bean<CreateAccountCommandHandler>()

    // Transaction scope
    bean<R2dbcTransactionScope>()
}

fun <T : Any> BeanDefinitionDsl.bean(context: GenericApplicationContext, type: KClass<T>) {
    context.registerBean(
        BeanDefinitionReaderUtils.uniqueBeanName(type.java.name, context),
        type.java,
        BeanDefinitionCustomizer {}
    )
}

class MedalseatsManagementInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) = beans(context).initialize(context)
}

fun main(args: Array<String>) {
    Locale.setDefault(Locale("pt", "BR"))
    runApplication<MedalseatsManagement>(*args)
}
