package app.dualcoder.spring.todo.app.web.model

import app.dualcoder.spring.todo.domain.model.TodoModel

data class SetTodoRes(
    val id: Long,
    val title: String,
    val completed: Boolean
) {
    companion object {
        fun from(model: TodoModel): SetTodoRes {
            return SetTodoRes(
                id = model.id!!,
                title = model.title,
                completed = model.completed
            )
        }
    }
}