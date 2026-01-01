package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ItemParfumBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class ParfumAdapter(
    private val list: MutableList<ParfumModel>,
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

        with(holder.binding) {
            txtTitle.text = item.title
            txtPrice.text = root.context.getString(R.string.price_format, item.price)
            txtStock.text = root.context.getString(R.string.stock_format, item.stock)

            Glide.with(root.context)
                .load(item.imageUrl)
                .into(imgParfum)

            root.setOnClickListener { onClick(item) }
            btnAddToCart.setOnClickListener { onClick(item) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<ParfumModel>) {
        list.clear()
        list.addAll(newList)
        notifyItemRangeChanged(0, list.size)
    }
}
