package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.databinding.ItemParfumBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class ParfumAdapter(
    private val list: List<ParfumModel>,
    private val onClick: (ParfumModel) -> Unit
) : RecyclerView.Adapter<ParfumAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemParfumBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemParfumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvPrice.text = "Rp ${item.price}"

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.imgProduct)

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    override fun getItemCount(): Int = list.size

    // 🔥 INI YANG MEMPERBAIKI ERROR KAMU
    fun updateData(newList: List<ParfumModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
