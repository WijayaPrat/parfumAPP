package com.wijayaprat.fragrancecenter.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.wijayaprat.fragrancecenter.databinding.ActivityAdminPaymentQrBinding
import com.wijayaprat.fragrancecenter.helper.ImageUploadHelper

class AdminPaymentQrActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPaymentQrBinding
    private val db = FirebaseFirestore.getInstance()
    private val imageHelper = ImageUploadHelper()
    private var qrUri: Uri? = null

    private val picker =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            qrUri = it
            binding.imgQr.setImageURI(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminPaymentQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickQr.setOnClickListener {
            picker.launch("image/*")
        }

        binding.btnSaveQr.setOnClickListener {
            saveQr()
        }
    }

    private fun saveQr() {
        if (qrUri == null) {
            Toast.makeText(this, "Pilih QR dulu", Toast.LENGTH_SHORT).show()
            return
        }

        imageHelper.uploadImage(
            qrUri!!,
            onSuccess = { imageUrl ->
                val data = mapOf(
                    "imageUrl" to imageUrl,
                    "provider" to "QRIS"
                )

                db.collection("payment_qr")
                    .document("default")
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "QR Payment disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }
            },
            onFailure = {
                Toast.makeText(this, "Upload gagal", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
