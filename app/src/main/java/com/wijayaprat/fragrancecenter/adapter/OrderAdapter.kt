package com.wijayaprat.fragrancecenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ItemCartBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class OrderAdapter(
    private val context: Context,
    private val cartList: ArrayList<ParfumModel>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cartList[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtPrice.text =
            context.getString(R.string.price_format, item.price)
        holder.binding.txtQuantity.text = item.quantity.toString()

        Glide.with(context)
            .load(item.imageUrl)
            .into(holder.binding.imgParfum)

        holder.binding.btnPlus.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onUpdate()
        }

        holder.binding.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onUpdate()
            }
        }
    }

    override fun getItemCount(): Int = cartList.size
}
