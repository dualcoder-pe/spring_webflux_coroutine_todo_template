package app.dualcoder.spring.todo.domain.model

data class TodoModel(
    val id: Long? = null,
    val title: String,
    val completed: Boolean = false
)