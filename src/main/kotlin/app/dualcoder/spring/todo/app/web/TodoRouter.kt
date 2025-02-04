package app.dualcoder.spring.todo.app.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TodoRouter(private val handler: TodoHandler) {
    @Bean
    fun todoRoute(): RouterFunction<ServerResponse> = coRouter {
        "/api/todos".nest {
            GET("", handler::findAll)
            GET("/{id}", handler::findById)
            POST("", handler::create)
            PUT("/{id}", handler::update)
            DELETE("/{id}", handler::delete)
        }
    }
}
