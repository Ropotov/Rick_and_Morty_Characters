package ru.nikita.rickmorty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.nikita.rickmorty.viewModel.MyViewModel

class DetailFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_detail, container, false)
        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getDetailCharacters()
        viewModel.myDetailList.observe(viewLifecycleOwner, { response ->
            response.body()?.let {
                detail_name.text = it.name
                detail_gender.text = "Gender: " + it.gender
                detail_status.text = "Status: " + it.status
                detail_species.text = "Species: " + it.species
                Picasso.get().load(it.image).into(detail_photo)
            }
        })
        return v

    }

    override fun onStart() {
        super.onStart()
        detail_back.setOnClickListener {
            val ma = (activity as MainActivity)
            ma.fragmentReplace(CharacterFragment())
        }
    }

}
