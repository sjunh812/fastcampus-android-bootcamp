package com.sjhstudio.compose.pokemon.ui.screen.types

enum class MainNavRoute {
    Home,
    Detail;

    companion object {
        fun getDetailRoute() = "${Detail.name}/{pokemonId}"

        fun getDetailRealRoute(pokemonId: Int) = "${Detail.name}/$pokemonId"
    }
}