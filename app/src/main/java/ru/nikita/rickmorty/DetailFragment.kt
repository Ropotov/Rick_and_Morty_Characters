package ru.nikita.rickmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v: View = inflater.inflate(R.layout.fragment_detail, container, false)
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
