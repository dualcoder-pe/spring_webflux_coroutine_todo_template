package app.dualcoder.spring.todo.app.web.model

import app.dualcoder.spring.todo.domain.model.TodoModel

data class SetTodoReq(
    val title: String,
    val completed: Boolean = false
) {
    fun toModel(): TodoModel {
        return TodoModel(
            title = title,
            completed = completed
        )
    }
}