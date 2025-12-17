package com.wijayaprat.fragrancecenter.activity

import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.wijayaprat.fragrancecenter.model.OrderModel
import com.wijayaprat.fragrancecenter.helper.ProductRepository
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wijayaprat.fragrancecenter.databinding.ActivityCartBinding
import com.wijayaprat.fragrancecenter.helper.ManagementCart
import com.wijayaprat.fragrancecenter.adapter.CartAdapter

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartManager: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartManager = ManagementCart(this)

        setupRecycler()
        updateTotal()

        binding.btnCheckout.setOnClickListener {

            startActivity(Intent(this, CheckoutActivity::class.java))
            checkout()
        }
    }

    private fun setupRecycler() {
        binding.recyclerCart.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerCart.adapter =
            CartAdapter(
                cartManager.getCart(),
                onUpdate = {
                    updateTotal()
                }
            )
    }

    private fun updateTotal() {
        binding.txtTotal.text =
            "Total: Rp ${cartManager.getTotalPrice()}"
    }

    private fun checkout() {
        val items = cartManager.getCart()
        if (items.isEmpty()) {
            Toast.makeText(this, "Cart kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val order = OrderModel(
            userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
            items = items,
            totalPrice = cartManager.getTotalPrice()
        )

        ProductRepository().saveOrder(
            order,
            onSuccess = {
                Toast.makeText(this, "Order berhasil", Toast.LENGTH_SHORT).show()
                cartManager.clearCart()
                finish()
            },
            onFailure = {
                Toast.makeText(this, "Checkout gagal", Toast.LENGTH_SHORT).show()
            }
        )
    }
}