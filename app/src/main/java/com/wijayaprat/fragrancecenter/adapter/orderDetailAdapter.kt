package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.databinding.ItemOrderDetailBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class OrderDetailAdapter(
    private val list: List<ParfumModel>
) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderDetailBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtQty.text = "Qty: ${item.quantity}"
        holder.binding.txtPrice.text =
            "Rp ${item.price * item.quantity}"

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.imgParfum)
    }

    override fun getItemCount(): Int = list.size
}
