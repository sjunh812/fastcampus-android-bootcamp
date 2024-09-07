package com.sjhstudio.compose.pokemon

import com.sjhstudio.compose.pokemon.data.dto.PokemonResponse
import com.sjhstudio.compose.pokemon.data.dto.PokemonsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemons(): PokemonsResponse

    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonsResponse

    @GET("pokemon/{pid}/")
    suspend fun getPokemon(@Path("pid") pid: Int): PokemonResponse
}