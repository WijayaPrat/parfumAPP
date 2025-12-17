package com.wijayaprat.fragrancecenter.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wijayaprat.fragrancecenter.activity.OrderDetailActivity
import com.wijayaprat.fragrancecenter.databinding.ItemOrderBinding
import com.wijayaprat.fragrancecenter.model.OrderModel
import java.text.SimpleDateFormat
import java.util.*

class OrderAdapter(
    private val list: MutableList<OrderModel>
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = list[position]

        val formatter =
            SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())

        holder.binding.txtOrderDate.text =
            formatter.format(Date(order.timestamp))

        holder.binding.txtItemCount.text =
            "${order.items.size} item"

        holder.binding.txtTotalPrice.text =
            "Rp ${order.totalPrice}"

        holder.itemView.setOnClickListener {
            val intent = Intent(
                holder.itemView.context,
                OrderDetailActivity::class.java
            )
            intent.putExtra("order", order)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<OrderModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}
