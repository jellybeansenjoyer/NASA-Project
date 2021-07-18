package com.example.nasa.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasa.Data.APOD
import com.example.nasa.R
import com.example.nasa.UI.ApodFragment

class ListAdapter(private val listener : onItemTouchListener) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(){

    private var oldList = emptyList<APOD>()

    class ListViewHolder(private val view : View,private val listener : ListAdapter.onItemTouchListener) : RecyclerView.ViewHolder(view){
        private val imageView : ImageView
        init{
             imageView = view.findViewById(R.id.imageView)

        }
        fun bind(data:APOD,position: Int) {
            Glide.with(ApodFragment().requireContext()).load(data.url).into(imageView)
            imageView.setOnClickListener{
                listener.interceptItem(position)
            }
        }
        companion object{
            fun create(parent : ViewGroup,listener : ListAdapter.onItemTouchListener): ListViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view_holder,parent,false)
                return ListViewHolder(view,listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder.create(parent,listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(oldList[position],position)
    }

    override fun getItemCount() = oldList.size

       fun submitList( newList : List<APOD>)
       {
            val callback = ListDiffUtil(oldList,newList)
            val calc = DiffUtil.calculateDiff(callback)
            oldList = newList
            calc.dispatchUpdatesTo(this)
       }
    interface onItemTouchListener{
        fun interceptItem(position: Int)
    }

}