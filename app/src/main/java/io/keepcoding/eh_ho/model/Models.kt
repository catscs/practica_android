package io.keepcoding.eh_ho.model

sealed class LogIn {
    data class Success(val userName: String) : LogIn()
    data class Error(val error: String) : LogIn()
}

data class Topic(
    val id: Int,
    val title: String,
    val likeCount: Int,
    val postCount: Int,
    val lastPosterUsername: String
)


data class Post (val id: Int, val name: String, val username: String)
