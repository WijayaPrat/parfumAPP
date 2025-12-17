package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.adapter.OrderAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityOrderHistoryBinding
import com.wijayaprat.fragrancecenter.helper.ProductRepository

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var adapter: OrderAdapter
    private val repository = ProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = OrderAdapter(mutableListOf())

        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = adapter

        loadOrders()
    }

    private fun loadOrders() {
        repository.getOrders(
            onSuccess = { orders ->
                adapter.updateData(orders)
            },
            onFailure = {
                Toast.makeText(
                    this,
                    "Gagal memuat order",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}
