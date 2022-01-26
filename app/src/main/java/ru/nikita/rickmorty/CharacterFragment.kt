package ru.nikita.rickmorty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.nikita.rickmorty.adapter.Adapter
import ru.nikita.rickmorty.model.Result
import ru.nikita.rickmorty.viewModel.MyViewModel

class CharacterFragment : Fragment() {
    private var responseList = arrayListOf<Result>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter
    lateinit var searchView: SearchView
    lateinit var swipe: SwipeRefreshLayout


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var listItem: View = inflater.inflate(R.layout.fragment_character, container, false)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        searchView = listItem.findViewById(R.id.sv_filter)
        swipe = listItem.findViewById(R.id.swipe)
        recyclerView = listItem.findViewById(R.id.rv_characters) as RecyclerView
        adapter = Adapter(responseList)
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
            response.body()?.results?.let { adapter.setList(it as ArrayList<Result>) }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        swipe.setOnRefreshListener {
            viewModel.myCharacterList.observe(viewLifecycleOwner, { response ->
                response.body()?.results?.let { adapter.setList(it as ArrayList<Result>) }
            })
            swipe.isRefreshing = false
        }
        return listItem

    }

}
