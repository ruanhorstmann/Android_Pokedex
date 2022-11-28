package br.com.up.android_pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.up.android_pokedex.R
import br.com.up.android_pokedex.domain.Pokemon
import br.com.up.android_pokedex.viewmodel.PokemonViewModel
import br.com.up.android_pokedex.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.rvPokemon)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())[PokemonViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.pokemons.observe(this, Observer {
            loadRecyclerView(it)
        })
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}