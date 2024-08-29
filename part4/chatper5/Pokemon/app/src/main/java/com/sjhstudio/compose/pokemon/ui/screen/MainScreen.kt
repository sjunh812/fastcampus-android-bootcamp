package com.sjhstudio.compose.pokemon.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.sjhstudio.compose.pokemon.viewmodel.PokemonViewModel

@Composable
fun MainScreen(
    viewModel: PokemonViewModel = hiltViewModel(),
    onPokemonClick: (url: String) -> Unit
) {
    val lazyItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()
    LazyColumn {
        items(count = lazyItems.itemCount) { index ->
            lazyItems[index]?.let {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .clickable {
                            onPokemonClick.invoke(it.url)
                        }
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = it.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = it.url,
                            fontSize = 12.sp,
                            modifier = Modifier.alpha(0.4f)
                        )
                    }
                }
            }
        }
    }
}