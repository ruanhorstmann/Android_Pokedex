package br.com.up.android_pokedex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.up.android_pokedex.api.PokemonRepository
import br.com.up.android_pokedex.domain.Pokemon

@Suppress("NAME_SHADOWING")
class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()

    init {
        Thread(Runnable {
            loadPokemon()
        }).start()
    }

    private fun loadPokemon() {
        val pokemonApiResult = PokemonRepository.listPokemons()

        pokemonApiResult?.results?.let {
            pokemons.postValue(it.map { pokemonResult ->
                val number = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        }
                    )
                }
            })
        }
    }
}
