package ru.nikita.rickmorty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.nikita.rickmorty.databinding.FragmentDetailBinding
import ru.nikita.rickmorty.viewModel.MyViewModel

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getDetailCharacters()
        viewModel.myDetailList.observe(viewLifecycleOwner, { response ->
            response.body()?.let {
                binding.detailName.text = it.name
                binding.detailGender.text = "Gender: " + it.gender
                binding.detailStatus.text = "Status: " + it.status
                binding.detailSpecies.text = "Species: " + it.species
                Picasso.get().load(it.image).into(binding.detailPhoto)
            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        binding.detailBack.setOnClickListener {
            val ma = (activity as MainActivity)
            ma.supportFragmentManager
                .popBackStack()


        }
    }
}
