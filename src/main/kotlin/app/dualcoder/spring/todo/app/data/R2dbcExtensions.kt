package app.dualcoder.spring.todo.app.data

import io.r2dbc.spi.Readable
import java.math.BigDecimal

fun Readable.getLong(name: String): Long? =
    this.get(name, BigDecimal::class.java)?.longValueExact()

fun Readable.getBoolean(name: String): Boolean? =
    this.get(name, BigDecimal::class.java)?.longValueExact()?.let { it > 0 }

fun Readable.getString(name: String): String? =
    this.get(name, String::class.java)