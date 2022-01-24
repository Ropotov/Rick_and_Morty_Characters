package ru.nikita.rickmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nikita.rickmorty.adapter.Adapter
import ru.nikita.rickmorty.model.Result
import ru.nikita.rickmorty.viewModel.MyViewModel

class CharacterFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var listItem: View = inflater.inflate(R.layout.fragment_character, container, false)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        recyclerView = listItem.findViewById(R.id.rv_characters) as RecyclerView
        adapter = Adapter()
        adapter.onCharacterClickListener = object : Adapter.OnCharacterClickListener {
            override fun onCharacterClick(result: Result) {
                super.onCharacterClick(result)
                val ma = (activity as MainActivity)
                ma.fragmentReplace(DetailFragment())
            }

        }
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
        viewModel.getMyCharacters()
        viewModel.myCharacterList.observe(viewLifecycleOwner, { response ->
            response.body()?.results?.let { adapter.setList(it) }
        })

        return listItem
    }
}
