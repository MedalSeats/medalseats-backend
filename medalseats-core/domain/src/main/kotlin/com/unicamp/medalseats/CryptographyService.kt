package com.unicamp.medalseats

interface CryptographyService {
    suspend fun encrypt(plainText: String): String

    suspend fun validate(plainText: String, cipherText: String): Boolean
}
