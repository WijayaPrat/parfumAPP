package com.wijayaprat.fragrancecenter.activity

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.wijayaprat.fragrancecenter.databinding.ActivityAdminAddProductBinding
import com.wijayaprat.fragrancecenter.helper.ImageUploadHelper
import com.wijayaprat.fragrancecenter.helper.ProductRepository
import com.wijayaprat.fragrancecenter.model.ParfumModel

class AdminAddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminAddProductBinding
    private val repo = ProductRepository()
    private val uploader = ImageUploadHelper()
    private var imageUri: Uri? = null

    private val picker =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            binding.imgPreview.setImageURI(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickImage.setOnClickListener {
            picker.launch("image/*")
        }

        binding.btnSaveProduct.setOnClickListener {
            save()
        }
    }

    private fun save() {
        val title = binding.edtTitle.text.toString()
        val price = binding.edtPrice.text.toString().toIntOrNull()
        val stockA = binding.edtStockA.text.toString().toIntOrNull()
        val stockB = binding.edtStockB.text.toString().toIntOrNull()

        if (title.isEmpty() || price == null || stockA == null || stockB == null || imageUri == null) {
            Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show()
            return
        }

        uploader.uploadImage(imageUri!!,
            onSuccess = { url ->
                val product = ParfumModel(
                    title = title,
                    price = price,
                    imageUrl = url
                )

                repo.addProduct(
                    product,
                    onSuccess = {
                        Toast.makeText(this, "Produk ditambahkan", Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this, "Gagal simpan", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            onFailure = {
                Toast.makeText(this, "Upload gagal", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
