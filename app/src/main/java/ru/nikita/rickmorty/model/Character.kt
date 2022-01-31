package ru.nikita.rickmorty.model

data class Character(
    var info: Info,
    var results: List<Result>
)
data class Result(
    var image: String,
    var name: String = "",
    var species: String,
    var status: String,
    var id: Int
)
data class Info(
    var count: Int,
    var next: String,
    var pages: String,
    var prev: String
)
