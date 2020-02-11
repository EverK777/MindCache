package com.example.mindvalleychallenge.models

data class Publication(
    val categories: List<Category>,
    val color: String,
    val created_at: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: LinksX,
    val urls: Urls,
    val user: User,
    val width: Int
)