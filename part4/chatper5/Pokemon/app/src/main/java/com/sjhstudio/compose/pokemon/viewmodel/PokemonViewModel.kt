package com.sjhstudio.compose.pokemon.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.sjhstudio.compose.pokemon.PokemonApi
import com.sjhstudio.compose.pokemon.data.dto.PokemonResponse
import com.sjhstudio.compose.pokemon.data.dto.PokemonsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonApi: PokemonApi
) : ViewModel() {

    val pokemonPagingData: Flow<PagingData<PokemonsResponse.Result>> = getPokemons().cachedIn(viewModelScope)
    val pokemonResult by mutableStateOf(
        PokemonResponse(
            species = PokemonResponse.Species(""),
            sprites = PokemonResponse.Sprites("")
        )
    )

    private fun getPokemons(): Flow<PagingData<PokemonsResponse.Result>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            object : PagingSource<Int, PokemonsResponse.Result>() {
                override fun getRefreshKey(state: PagingState<Int, PokemonsResponse.Result>): Int? {
                    return state.anchorPosition
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonsResponse.Result> {
                    return try {
                        val pokemonsResponse = params.key?.run {
                            pokemonApi.getPokemons(offset = this, limit = params.loadSize)
                        } ?: pokemonApi.getPokemons()

                        // "offset=20&limit=20" 형 주소값
                        LoadResult.Page(
                            data = pokemonsResponse.results,
                            prevKey = pokemonsResponse.previous?.substringAfter("offset=")?.substringBefore("&")?.toIntOrNull(),
                            nextKey = pokemonsResponse.next?.substringAfter("offset=")?.substringBefore("&")?.toIntOrNull()
                        )
                    } catch (e: Exception) {
                        Log.e("SJH", "error getPokemons() : $e")
                        e.printStackTrace()
                        LoadResult.Error(e)
                    }
                }
            }
        }
    ).flow

    fun getPokemon(pokemonId: Int) {
        viewModelScope.launch {
            pokemonApi.getPokemon(pokemonId)
        }
    }
}