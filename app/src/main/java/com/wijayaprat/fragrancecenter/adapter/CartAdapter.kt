package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ItemCartBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel

class CartAdapter(
    private val list: MutableList<ParfumModel>,
    private val onUpdate: () -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtTitle.text = item.title
        holder.binding.txtQuantity.text = item.quantity.toString()
        holder.binding.txtPrice.text =
            holder.itemView.context.getString(
                R.string.price_format,
                item.price * item.quantity
            )

        Glide.with(holder.itemView.context)
            .load(item.picUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgParfum)

        // ➕ Tambah quantity
        holder.binding.btnPlus.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onUpdate()
        }

        // ➖ Kurangi quantity
        holder.binding.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onUpdate()
            }
        }

        // ❌ Hapus item (long press)
        holder.itemView.setOnLongClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            onUpdate()
            true
        }
    }

    override fun getItemCount(): Int = list.size
}
