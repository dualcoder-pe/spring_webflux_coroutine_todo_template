package app.dualcoder.spring.todo.app.data

import app.dualcoder.spring.todo.app.data.converter.DatabaseTypeConverter
import io.r2dbc.spi.Readable
import org.springframework.stereotype.Component

@Component
class R2dbcExtensions(private val converter: DatabaseTypeConverter) {
    fun Readable.getLong(name: String): Long? =
        converter.getLong(this, name)

    fun Readable.getBoolean(name: String): Boolean? =
        converter.getBoolean(this, name)

    fun Readable.getString(name: String): String? =
        converter.getString(this, name)
}
