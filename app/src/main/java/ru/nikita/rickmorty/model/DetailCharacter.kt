package ru.nikita.rickmorty.model

data class DetailCharacter(
    var created: String,
    var episode: List<String>,
    var gender: String,
    var id: Int,
    var image: String,
    var name: String,
    var species: String,
    var status: String,
    var type: String,
    var url: String
)