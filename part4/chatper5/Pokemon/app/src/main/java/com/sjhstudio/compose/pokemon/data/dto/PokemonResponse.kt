package com.sjhstudio.compose.pokemon.data.dto

data class PokemonResponse(
    val species: Species,
    val sprites: Sprites
) {

    data class Species(val name: String)

    data class Sprites(val frontDefault: String)
}