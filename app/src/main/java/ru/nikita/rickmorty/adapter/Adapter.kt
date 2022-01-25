package ru.nikita.rickmorty.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_item.view.*
import ru.nikita.rickmorty.R
import ru.nikita.rickmorty.model.Result

var idCharacter: Int = 0

class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var listResponse: List<Result> = listOf()
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
        val pos = listResponse[position]
        with(holder) {
            with(pos) {
                itemView.character_name.text = name
                itemView.character_status.text = "Status: " + status
                when(status){
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
        return listResponse.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Result>) {
        listResponse = list
        notifyDataSetChanged()
    }

    interface OnCharacterClickListener {
        fun onCharacterClick(result: Result){

        }

    }

}
