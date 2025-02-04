package app.dualcoder.spring.todo.app.data.converter

import io.r2dbc.spi.Readable
import java.math.BigDecimal

interface DatabaseTypeConverter {
    fun getLong(row: Readable, name: String): Long?
    fun getBoolean(row: Readable, name: String): Boolean?
    fun getString(row: Readable, name: String): String?
}

class OracleTypeConverter : DatabaseTypeConverter {
    override fun getLong(row: Readable, name: String): Long? =
        row.get(name, BigDecimal::class.java)?.longValueExact()

    override fun getBoolean(row: Readable, name: String): Boolean? =
        row.get(name, BigDecimal::class.java)?.longValueExact()?.let { it > 0 }

    override fun getString(row: Readable, name: String): String? =
        row.get(name, String::class.java)
}

class MySqlTypeConverter : DatabaseTypeConverter {
    override fun getLong(row: Readable, name: String): Long? =
        row.get(name, Long::class.java)

    override fun getBoolean(row: Readable, name: String): Boolean? =
        row.get(name, Boolean::class.java)

    override fun getString(row: Readable, name: String): String? =
        row.get(name, String::class.java)
}
