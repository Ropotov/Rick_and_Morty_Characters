package ru.nikita.rickmorty.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.nikita.rickmorty.CharacterStatusEnums
import ru.nikita.rickmorty.databinding.CharacterItemBinding
import ru.nikita.rickmorty.model.Result
import java.util.*
import kotlin.collections.ArrayList

var idCharacter: Int = 0


class Adapter(private var listResponse: ArrayList<Result>) :
    RecyclerView.Adapter<Adapter.ViewHolder>(),
    Filterable {

    var listFilterResponse = ArrayList<Result>()

    init {
        listFilterResponse = listResponse
    }

    var onCharacterClickListener: OnCharacterClickListener? = null

    class ViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Result) {
            with(binding) {
                characterName.text = pos.name
                characterStatus.text = pos.status
                when (pos.status) {
                    CharacterStatusEnums.CHARACTER_ALIVE.value ->
                        binding.statusTv.setBackgroundColor(Color.parseColor("#02B31A"))
                    CharacterStatusEnums.CHARACTER_DEAD.value ->
                        binding.statusTv.setBackgroundColor(Color.parseColor("#FF0800"))
                    CharacterStatusEnums.CHARACTER_UNKNOWN.value ->
                        binding.statusTv.setBackgroundColor(Color.parseColor("#E3E3E3"))

                    else -> binding.statusTv.setBackgroundColor(Color.parseColor("#F8F816"))
                }
                Picasso.get().load(pos.image).into(binding.characterPhoto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = listFilterResponse[position]
        holder.bind(pos)
        holder.itemView.setOnClickListener {
            onCharacterClickListener?.onCharacterClick(pos)
            idCharacter = pos.id
        }
    }
    override fun getItemCount(): Int {
        return listFilterResponse.size
    }

    interface OnCharacterClickListener {
        fun onCharacterClick(result: Result) {

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    listFilterResponse = listResponse
                } else {
                    val resultList = ArrayList<Result>()
                    for (row in listResponse) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }

                    }
                    listFilterResponse = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = listFilterResponse
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFilterResponse = results?.values as ArrayList<Result>
                notifyDataSetChanged()
            }
        }
    }
}
