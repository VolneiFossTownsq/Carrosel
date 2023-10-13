package com.example.carroselbasic.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carroselbasic.data.Carousel
import com.example.carroselbasic.databinding.CarouselItemBinding
import com.squareup.picasso.Picasso

class CarouselAdapter: RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    private val items: MutableList<Carousel> = mutableListOf()

    fun setItems(newItems: List<Carousel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class CarouselViewHolder(private val binding: CarouselItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Carousel) {
            Picasso.get()
                .load(item.img)
                .into(binding.imgCarousel)
        }
    }
}
