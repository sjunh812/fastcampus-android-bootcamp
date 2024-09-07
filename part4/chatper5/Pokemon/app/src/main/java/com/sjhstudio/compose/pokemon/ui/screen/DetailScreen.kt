package com.sjhstudio.compose.pokemon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sjhstudio.compose.pokemon.viewmodel.PokemonViewModel

@Composable
fun DetailScreen(
    pokemonId: Int,
    viewModel: PokemonViewModel = hiltViewModel(),
    onUpButtonClick: () -> Unit
) {
    viewModel.getPokemon(pokemonId)

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Column {
            val pokemonResult = viewModel.pokemonResult

            AsyncImage(
                model = pokemonResult.sprites.frontDefault,
                contentDescription = pokemonResult.species.name,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = pokemonResult.species.name,
                fontSize = 18.sp
            )
        }
    }
}