package com.wijayaprat.fragrancecenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Pastikan library Glide ada di build.gradle
import com.wijayaprat.fragrancecenter.databinding.ItemCartBinding
import com.wijayaprat.fragrancecenter.model.CartModel

class CartAdapter(private val context: Context, private val cartList: List<CartModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        holder.binding.tvProductName.text = item.productName
        holder.binding.tvPrice.text = item.price.toString()

        // Error Fix: Menggunakan Glide dengan syntax yang benar
        // Pastikan variabel di XML (id) adalah imgProduct
        Glide.with(context)
            .load(item.imageUrl) // Pastikan Model Cart punya imageUrl
            .into(holder.binding.imgProduct)
    }

    override fun getItemCount() = cartList.size
}