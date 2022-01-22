package ru.nikita.rickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ru.nikita.rickmorty.adapter.Adapter
import ru.nikita.rickmorty.viewModel.MyViewModel

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        recyclerView = rv_character
        adapter = Adapter()
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        viewModel.getMyCharacters()
        viewModel.myCharacterList.observe(this, { response ->
            response.body()?.results?.let { adapter.setList(it) }
        })

    }
}