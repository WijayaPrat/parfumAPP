package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.adapter.CartAdapter // atau OrderDetailAdapter jika ada
import com.wijayaprat.fragrancecenter.databinding.ActivityOrderDetailBinding
import com.wijayaprat.fragrancecenter.model.OrderModel

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari Intent
        val order = intent.getSerializableExtra("ORDER_DATA") as? OrderModel

        if (order != null) {
            // Error Fix: ViewBinding ID 'recyclerOrderDetail' dan 'txtTotal' harus sesuai XML
            binding.recyclerOrderDetail.layoutManager = LinearLayoutManager(this)

            // Error Fix: Akses 'items' sekarang aman karena OrderModel sudah diperbaiki
            // Menggunakan CartAdapter untuk menampilkan detail item
            val adapter = CartAdapter(this, order.items)
            binding.recyclerOrderDetail.adapter = adapter

            binding.txtTotal.text = "Total: Rp ${order.totalPrice}"
        }
    }
}