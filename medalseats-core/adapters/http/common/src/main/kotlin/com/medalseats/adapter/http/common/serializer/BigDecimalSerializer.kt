package com.medalseats.adapter.http.common.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.DOUBLE
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

object BigDecimalSerializer : KSerializer<BigDecimal> {

    override fun deserialize(decoder: Decoder): BigDecimal =
        decoder.decodeDouble().toBigDecimal()

    override fun serialize(encoder: Encoder, value: BigDecimal): Unit =
        encoder.encodeDouble(value.toDouble())

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serialName = "BigDecimal", kind = DOUBLE)
}
