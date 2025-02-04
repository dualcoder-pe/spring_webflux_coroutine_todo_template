package app.dualcoder.spring.todo.app.config

import app.dualcoder.spring.todo.app.data.converter.DatabaseTypeConverter
import app.dualcoder.spring.todo.app.data.converter.MySqlTypeConverter
import app.dualcoder.spring.todo.app.data.converter.OracleTypeConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig {
    @Value("\${spring.r2dbc.url}")
    private lateinit var dbUrl: String

    @Bean
    fun databaseTypeConverter(): DatabaseTypeConverter =
        when {
            dbUrl.startsWith("r2dbc:oracle") -> OracleTypeConverter()
            dbUrl.startsWith("r2dbc:mysql") -> MySqlTypeConverter()
            else -> throw IllegalStateException("Unsupported database type")
        }
}
