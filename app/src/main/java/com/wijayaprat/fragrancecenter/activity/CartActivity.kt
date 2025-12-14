package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.adapter.CartAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityCartBinding
import com.wijayaprat.fragrancecenter.helper.ManagementCart

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        binding.recyclerCart.layoutManager = LinearLayoutManager(this)

        val adapter = CartAdapter(
            managementCart.getCart()
        ) {
            updateTotal()
        }

        binding.recyclerCart.adapter = adapter

        updateTotal()

        binding.btnCheckout.setOnClickListener {
            managementCart.clearCart()

            Toast.makeText(
                this,
                getString(R.string.order_success),
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }

    }


    private fun updateTotal() {
        val total = managementCart.getTotalPrice()
        binding.txtTotalPrice.text =
            getString(R.string.price_format, total)
    }
}
