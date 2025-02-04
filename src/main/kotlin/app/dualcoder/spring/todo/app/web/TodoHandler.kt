package app.dualcoder.spring.todo.app.web

import app.dualcoder.spring.todo.app.web.model.SetTodoReq
import app.dualcoder.spring.todo.app.web.model.SetTodoRes
import app.dualcoder.spring.todo.domain.service.TodoService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class TodoHandler(
    private val service: TodoService
) {
    suspend fun findAll(request: ServerRequest): ServerResponse {
        val todos = service.findAll()
        return ServerResponse.ok().bodyValueAndAwait(todos)
    }

    suspend fun findById(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        val todo = service.findById(id)
        return ServerResponse.ok().bodyValueAndAwait(todo)
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val todoReq = request.awaitBody<SetTodoReq>()
        val todo = service.create(todoReq.toModel())
        return ServerResponse.ok().bodyValueAndAwait(SetTodoRes.from(todo))
    }

    suspend fun update(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        val todoReq = request.awaitBody<SetTodoReq>()
        val todo = service.update(id, todoReq.toModel())
        return ServerResponse.ok().bodyValueAndAwait(SetTodoRes.from(todo))
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toLong()
        service.delete(id)
        return ServerResponse.noContent().buildAndAwait()
    }
}