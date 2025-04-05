package com.example.physiobuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IntroAdapter(private val items: List<IntroItem>) :
    RecyclerView.Adapter<IntroAdapter.IntroViewHolder>() {

    class IntroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIntro: ImageView = itemView.findViewById(R.id.imageIntro)
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textSubtitle: TextView = itemView.findViewById(R.id.textSubtitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_intro, parent, false)
        return IntroViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val item = items[position]
        holder.imageIntro.setImageResource(item.imageResId)
        holder.textTitle.text = item.title
        holder.textSubtitle.text = item.subtitle
        holder.textDescription.text = item.description
    }

    override fun getItemCount(): Int = items.size
}
