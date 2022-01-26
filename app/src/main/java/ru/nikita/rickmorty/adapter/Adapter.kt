package ru.nikita.rickmorty.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*
import ru.nikita.rickmorty.R
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView = itemView.character_photo
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = listFilterResponse[position]
        with(holder) {
            with(pos) {
                itemView.character_name.text = name
                itemView.character_status.text = "Status: " + status
                when (status) {
                    "Alive" -> itemView.status_tv.setImageResource(R.drawable.green_ind)
                    "Dead" -> itemView.status_tv.setBackgroundResource(R.drawable.red_ind)
                    "unknown" -> itemView.status_tv.setBackgroundResource(R.drawable.gray_ind)

                }
                Picasso.get().load(image).into(photo)
                itemView.setOnClickListener {
                    onCharacterClickListener?.onCharacterClick(this)
                    idCharacter = id
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return listFilterResponse.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<Result>) {
        listFilterResponse = list
        notifyDataSetChanged()
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
                listFilterResponse.clear()
                listFilterResponse.addAll(results?.values as ArrayList<Result>)
                notifyDataSetChanged()

            }
        }
    }
}

