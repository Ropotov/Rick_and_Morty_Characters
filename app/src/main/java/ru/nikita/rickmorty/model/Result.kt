package ru.nikita.rickmorty.model

data class Result(
    var created: String,
    var episode: List<String>,
    var gender: String,
    var id: Int,
    var image: String,
    var location: Location,
    var name: String = "",
    var origin: Origin,
    var species: String,
    var status: String,
    var type: String,
    var url: String
)