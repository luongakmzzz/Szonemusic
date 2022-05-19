package com.example.test_main_music_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.test_main_music_app.Model.AllCategory

import com.example.test_main_music_app.R


class CategoryItemRecyclerViewAdapter(
    private val fragment: Fragment,
    private val allCategory: ArrayList<AllCategory>,
    private val onItemClick: ((AllCategory) -> Unit)
) : RecyclerView.Adapter<CategoryItemRecyclerViewAdapter.CategoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cat_row_items, parent, false)
        return CategoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = allCategory[position]
        holder.namemusic.text = category.namemusic
        holder.authormusic.text = category.author
        Glide.with(holder.itemView.context)
            .load(category.imageUrl)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            onItemClick(allCategory[position])
        }

    }

    override fun getItemCount(): Int {
        return allCategory.size
    }

    class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val namemusic: TextView = itemView.findViewById(R.id.item_title_music)
        val authormusic: TextView = itemView.findViewById(R.id.item_author_music)

    }


}