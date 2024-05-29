package com.unicamp.medalseats

import com.medalseats.adapter.cyrptograph.PasswordEncoderConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "medalseats", ignoreUnknownFields = true)
data class MedalseatsManagementProperties(
    val passwordEncoder: PasswordEncoderConfiguration
)
