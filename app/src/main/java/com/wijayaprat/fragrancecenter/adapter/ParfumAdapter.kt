package com.wijayaprat.fragrancecenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.R   // ✅ WAJIB
import com.wijayaprat.fragrancecenter.databinding.ItemParfumBinding
import com.wijayaprat.fragrancecenter.model.ParfumModel
import android.widget.Toast
import com.wijayaprat.fragrancecenter.helper.ManagementCart

class ParfumAdapter(
    private val list: List<ParfumModel>
) : RecyclerView.Adapter<ParfumAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemParfumBinding)
        : RecyclerView.ViewHolder(binding.root)

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

        holder.binding.txtTitle.text = item.title
        holder.binding.txtExtra.text = item.extra
        holder.binding.txtPrice.text = "Rp ${item.price}"

        Glide.with(holder.itemView.context)
            .load(
                if (item.picUrl.isNotEmpty())
                    item.picUrl
                else
                    R.drawable.ic_launcher_foreground
            )
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.binding.imgParfum)
    }

    override fun getItemCount(): Int = list.size
}
