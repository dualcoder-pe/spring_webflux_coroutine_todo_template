package app.dualcoder.spring.todo.domain.repository

import app.dualcoder.spring.todo.domain.model.TodoModel

interface TodoRepository {
    suspend fun findAll(): List<TodoModel>
    suspend fun findById(id: Long): TodoModel?
    suspend fun save(model: TodoModel): TodoModel?
    suspend fun delete(id: Long)
    suspend fun update(id: Long, model: TodoModel): TodoModel?
}