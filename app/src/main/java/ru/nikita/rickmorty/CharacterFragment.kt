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
import ru.nikita.rickmorty.adapter.Adapter
import ru.nikita.rickmorty.databinding.FragmentCharacterBinding
import ru.nikita.rickmorty.model.Result
import ru.nikita.rickmorty.viewModel.MyViewModel

class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private var responseList = arrayListOf<Result>()
    private var currentList = arrayListOf<Result>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        val view = binding.root
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        recyclerView = binding.rvCharacters
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
        viewModel.getMyCharacters(1)
        viewModel.myCharacterList.observe(viewLifecycleOwner, { response ->
            response.body()?.results?.let { updateAdapterList(it) }
        })
        binding.svFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.searchNextPage()
                        viewModel.myCurrentList.observe(viewLifecycleOwner, { response ->
                            response.body()?.results?.let { updateAdapterList(it) }

                        })
                    }
                }
            }
        )
        binding.swipe.setOnRefreshListener {
            viewModel.myCharacterList.observe(viewLifecycleOwner, { response ->
                response.body()?.results?.let { updateAdapterList(it) }
            })
            binding.swipe.isRefreshing = false
        }
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapterList(list: List<Result>) {
        currentList.clear()
        currentList.addAll(list)
        responseList.addAll(currentList)
        adapter.notifyDataSetChanged()
    }
}
