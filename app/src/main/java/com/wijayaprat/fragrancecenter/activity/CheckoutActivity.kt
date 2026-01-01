package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wijayaprat.fragrancecenter.databinding.ActivityCheckoutBinding
import com.wijayaprat.fragrancecenter.model.OrderModel
import com.wijayaprat.fragrancecenter.model.ParfumModel

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadQr()

        val cartItems =
            intent.getSerializableExtra("CART_ITEMS") as? ArrayList<ParfumModel>

        val totalPrice =
            intent.getIntExtra("TOTAL_PRICE", 0)

        binding.btnConfirm.setOnClickListener {
            if (cartItems.isNullOrEmpty()) {
                Toast.makeText(this, "Cart kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveOrder(cartItems, totalPrice)
        }
    }

    private fun loadQr() {
        db.collection("payment_qr")
            .document("default")
            .get()
            .addOnSuccessListener {
                val url = it.getString("imageUrl")
                Glide.with(this)
                    .load(url)
                    .into(binding.imgQr)
            }
    }

    private fun saveOrder(items: List<ParfumModel>, totalPrice: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val order = OrderModel(
            userId = userId,
            items = items,
            totalPrice = totalPrice,
            timestamp = Timestamp.now(),
            status = "Pending"
        )

        db.collection("orders")
            .add(order)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Order dikirim, menunggu verifikasi admin",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal checkout", Toast.LENGTH_SHORT).show()
            }
    }
}
