package com.example.banaoassignment.models

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: MutableList<Photo>,
    val total: Int
)