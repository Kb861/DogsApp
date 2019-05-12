package com.example.dogs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

public class DogsListAdapter(val dogs: ArrayList<String>, val listener: RecyclerViewClickListener) :
    RecyclerView.Adapter<DogsListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemLayoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, null)

        return ViewHolder(itemLayoutView, listener)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setDogBreed(dogs[position])

    }


    override fun getItemCount(): Int {

        return dogs.size

    }


    inner class ViewHolder(itemView: View, private val listener: RecyclerViewClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {



        init {

            itemView.button_showBreed.setOnClickListener(this)

        }


        fun setDogBreed(dogBreed: String) {

            itemView.textView_breed.text = dogBreed

        }


        override fun onClick(view: View) {

            listener.onClick(view, adapterPosition, itemView.textView_breed.text.toString())

        }

    }

}