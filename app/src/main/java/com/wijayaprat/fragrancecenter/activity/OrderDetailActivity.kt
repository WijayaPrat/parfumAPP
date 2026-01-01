package com.wijayaprat.fragrancecenter.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.adapter.CartAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityOrderDetailBinding
import com.wijayaprat.fragrancecenter.model.OrderModel

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val order: OrderModel? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("ORDER_DATA", OrderModel::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getSerializableExtra("ORDER_DATA") as? OrderModel
            }

        if (order != null) {
            binding.recyclerOrderDetail.layoutManager = LinearLayoutManager(this)

            val adapter = CartAdapter(
                this,
                ArrayList(order.items)
            ) {}

            binding.recyclerOrderDetail.adapter = adapter

            binding.txtTotal.text = getString(
                R.string.total_price,
                order.totalPrice.toString()
            )
        }
    }
}
