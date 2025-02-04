package app.dualcoder.spring.todo.app.data

import app.dualcoder.spring.todo.domain.model.TodoModel
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("todo")
data class TodoEntity(
    @Id
    val id: Long? = null,
    val title: String,
    val completed: Boolean = false
) {
    fun toModel(): TodoModel {
        return TodoModel(
            id = id,
            title = title,
            completed = completed
        )
    }

    companion object {
        fun from(model: TodoModel): TodoEntity {
            return TodoEntity(
                id = model.id,
                title = model.title,
                completed = model.completed
            )
        }
    }
}
