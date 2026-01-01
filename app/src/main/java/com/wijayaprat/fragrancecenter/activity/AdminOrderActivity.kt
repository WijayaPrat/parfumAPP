package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.adapter.AdminOrderAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityAdminOrdersBinding
import com.wijayaprat.fragrancecenter.helper.ProductRepository
import com.wijayaprat.fragrancecenter.model.OrderModel

class AdminOrderActivity : BaseActivity<ActivityAdminOrdersBinding>() {

    private lateinit var adapter: AdminOrderAdapter
    private val repository = ProductRepository()
    private val orderList = mutableListOf<OrderModel>()

    // 🔹 WAJIB untuk BaseActivity
    override fun inflateBinding(): ActivityAdminOrdersBinding {
        return ActivityAdminOrdersBinding.inflate(layoutInflater)
    }

    override fun getToolbar() = binding.topAppBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Order Admin"

        adapter = AdminOrderAdapter(orderList) { order ->
            updateStatus(order)
        }

        binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
        binding.recyclerOrders.adapter = adapter

        loadOrders()
    }

    // 🔹 STEP 8: Load order dari Firestore
    private fun loadOrders() {
        repository.getOrders(
            onSuccess = { orders ->
                orderList.clear()
                orderList.addAll(orders)
                adapter.notifyDataSetChanged()
            },
            onFailure = {
                // optional: Toast error
            }
        )
    }

    // 🔹 STEP 9: Update status order ke Firestore
    private fun updateStatus(order: OrderModel) {
        repository.updateOrderStatus(
            orderId = order.id,
            newStatus = "Completed",
            onSuccess = {
                order.status = "Completed"
                adapter.notifyDataSetChanged()
            },
            onFailure = {
                // optional: Toast gagal update
            }
        )
    }
}
