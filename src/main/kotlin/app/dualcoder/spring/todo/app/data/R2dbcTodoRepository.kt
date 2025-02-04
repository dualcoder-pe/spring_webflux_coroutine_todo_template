package app.dualcoder.spring.todo.app.data

import app.dualcoder.spring.todo.domain.model.TodoModel
import app.dualcoder.spring.todo.domain.repository.TodoRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.r2dbc.core.awaitSingle
import org.springframework.r2dbc.core.awaitSingleOrNull
import org.springframework.stereotype.Repository

@Repository
class R2dbcTodoRepository(
    private val r2dbcEntityTemplate: R2dbcEntityTemplate,
) : TodoRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun findAll(): List<TodoModel> {
        return try {
            r2dbcEntityTemplate
                .select(TodoEntity::class.java)
                .all()
                .collectList()
                .awaitSingle()
                .map { it.toModel() }
        } catch (e: Exception) {
            log.error("Failed to find all todos: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun findById(id: Long): TodoModel? {
        return try {
            r2dbcEntityTemplate
                .databaseClient
                .sql("SELECT * FROM todo WHERE id = :id")
                .bind("id", id)
                .map { row, _ ->
                    TodoEntity(
                        id = row.getLong("id")!!,
                        title = row.getString("title")!!,
                        completed = row.getBoolean("completed")!!
                    )
                }
                .awaitSingleOrNull()
                ?.toModel()
        } catch (e: Exception) {
            log.error("Failed to find todo by id: ${e.message}", e)
            null
        }
    }

    override suspend fun save(model: TodoModel): TodoModel? {
        return try {
            val id = r2dbcEntityTemplate
                .databaseClient
                .sql("INSERT INTO todo (title, completed) VALUES (:title, :completed)")
                .bind("title", model.title)
                .bind("completed", model.completed)
                .filter { statement -> statement.returnGeneratedValues("id") }
                .map { row -> row.getLong("id")!! }
                .awaitSingleOrNull()

            id?.let { findById(it) }
        } catch (e: Exception) {
            log.error("Failed to save todo: ${e.message}", e)
            null
        }
    }

    override suspend fun update(id: Long, model: TodoModel): TodoModel? {
        return try {
            val rowsAffected = r2dbcEntityTemplate
                .databaseClient
                .sql("UPDATE todo SET title = :title, completed = :completed WHERE id = :id")
                .bind("id", id)
                .bind("title", model.title)
                .bind("completed", model.completed)
                .fetch()
                .rowsUpdated()
                .awaitSingle()

            if (rowsAffected > 0) findById(id) else null
        } catch (e: Exception) {
            log.error("Failed to update todo: ${e.message}", e)
            null
        }
    }

    override suspend fun delete(id: Long) {
        try {
            r2dbcEntityTemplate
                .databaseClient
                .sql("DELETE FROM todo WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .awaitSingle()
        } catch (e: Exception) {
            log.error("Failed to delete todo: ${e.message}", e)
        }
    }
}