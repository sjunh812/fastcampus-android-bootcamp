package com.sjhstudio.compose.pokemon.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sjhstudio.compose.pokemon.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    @Named("POKEMON_API_URL")
    fun providePokemonApiUrl(): String = "https://pokeapi.co/api/v2/"

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("POKEMON_API_URL") apiUrl: String,
        gsonConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Singleton
    @Provides
    fun providePokemonApi(retrofit: Retrofit): PokemonApi = retrofit.create(PokemonApi::class.java)
}