package com.medalseats.adapter.cyrptograph

data class PasswordEncoderConfiguration(
    val saltLength: Int,
    val hashLength: Int,
    val parallelism: Int,
    val memory: Int,
    val iterations: Int
)
