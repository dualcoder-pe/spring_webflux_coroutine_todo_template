package app.dualcoder.spring.todo.domain.service

import app.dualcoder.spring.todo.domain.model.TodoModel
import app.dualcoder.spring.todo.domain.repository.TodoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class TodoService(
    private val repository: TodoRepository
) {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    suspend fun findAll(): List<TodoModel> {
        return repository.findAll()
    }

    suspend fun findById(id: Long): TodoModel {
        return repository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
    }

    @Transactional
    suspend fun create(model: TodoModel): TodoModel {
        return repository.save(model) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create todo")
    }

    @Transactional
    suspend fun update(id: Long, model: TodoModel): TodoModel {
        log.info("Updating todo - id: {}, model: {}", id, model)
        return repository.update(id, model) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
    }

    @Transactional
    suspend fun delete(id: Long) {
        repository.delete(id)
    }
}