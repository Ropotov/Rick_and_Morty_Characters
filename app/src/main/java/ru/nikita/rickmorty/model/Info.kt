package ru.nikita.rickmorty.model

data class Info(
    var count: Int,
    var next: String,
    var pages: Int,
    var prev: Any
)