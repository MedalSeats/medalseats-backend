package com.medalseats.adapter.cyrptograph

import com.unicamp.medalseats.CryptographyService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

data class HashCryptographyService(
    val passwordEncoderConfiguration: PasswordEncoderConfiguration
) : CryptographyService {

    private val passwordEncoder = Argon2PasswordEncoder(
        passwordEncoderConfiguration.saltLength,
        passwordEncoderConfiguration.hashLength,
        passwordEncoderConfiguration.parallelism,
        passwordEncoderConfiguration.memory,
        passwordEncoderConfiguration.iterations,
    )

    override suspend fun encrypt(plainText: String): String {
        return passwordEncoder.encode(plainText)
    }

    override suspend fun validate(plainText: String, cipherText: String): Boolean {
        return passwordEncoder.matches(plainText, cipherText)
    }
}
