package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.wijayaprat.fragrancecenter.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadQr()

        binding.btnConfirm.setOnClickListener {
            Toast.makeText(this, "Menunggu verifikasi admin", Toast.LENGTH_SHORT).show()
            finish()
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
}
