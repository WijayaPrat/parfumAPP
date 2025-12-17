package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wijayaprat.fragrancecenter.databinding.ItemOrderAdminBinding
import com.wijayaprat.fragrancecenter.model.OrderModel

class AdminOrderAdapter(
    private val list: List<OrderModel>,
    private val onUpdate: (OrderModel) -> Unit
) : RecyclerView.Adapter<AdminOrderAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOrderAdminBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.txtStatus.text = item.status
        holder.binding.txtTotalPrice.text = "Rp ${item.totalPrice}"

        holder.binding.btnUpdateStatus.setOnClickListener {
            onUpdate(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
