package com.example.networktest.view

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.networktest.R
import com.example.networktest.databinding.ItemLayoutBinding
import com.example.networktest.model.presentation.VolumeItem
import com.squareup.picasso.Picasso

class BookViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    private var TAG: String = "Book biew holfish and chips"


    fun onBind(volumeItem : VolumeItem,callback:(VolumeItem)->Unit){

        //String templates
        //Title : ASherf ali
        //Authors : [auth,auth2]

        binding.tvBookAuthors.text = binding.root.context.getString(R.string.book_authors,volumeItem.authors.toString())
        binding.tvBookTitle.text = binding.root.context.getString((R.string.book_title),volumeItem.title.toString())

        Log.d(TAG, "onBind: ${volumeItem.imageLinks.thumbnail}")
        Picasso.get().load(volumeItem.imageLinks.thumbnail).into(binding.ivBookCover)

        binding.root.setOnClickListener{
            callback(volumeItem)
        }
    }
}