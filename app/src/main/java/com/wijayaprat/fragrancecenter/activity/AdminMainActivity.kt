package com.wijayaprat.fragrancecenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.adapter.ParfumAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityAdminMainBinding
import com.wijayaprat.fragrancecenter.helper.ProductRepository


class AdminMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminMainBinding
    private val repository = ProductRepository()
    private lateinit var adapter: ParfumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ParfumAdapter(mutableListOf()) {
            // admin klik item (opsional)
        }

        binding.recyclerProducts.layoutManager =
            LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this, AdminAddProductActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadProducts()
    }

    private fun loadProducts() {
        repository.getProducts(
            onSuccess = {
                adapter.updateData(it)
            },
            onFailure = {
                // bisa tambahin Toast nanti
            }
        )
    }
}
