package com.wijayaprat.fragrancecenter.activity

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ActivityDetailedBinding
import com.wijayaprat.fragrancecenter.helper.ImageUploadHelper
import com.wijayaprat.fragrancecenter.helper.ManagementCart
import com.wijayaprat.fragrancecenter.helper.ProductRepository
import com.wijayaprat.fragrancecenter.helper.UserLocationManager
import com.wijayaprat.fragrancecenter.model.OrderModel
import com.wijayaprat.fragrancecenter.model.ParfumModel

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var managementCart: ManagementCart
    private lateinit var item: ParfumModel

    private val productRepository = ProductRepository()
    private val imageUploadHelper = ImageUploadHelper()

    private var selectedImageUri: Uri? = null
    private var currentBranch: String = ""

    // ================= IMAGE PICKER =================
    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                binding.imgParfum.setImageURI(uri)

                Toast.makeText(
                    this,
                    getString(R.string.image_selected),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.sharedElementEnterTransition =
            TransitionInflater.from(this)
                .inflateTransition(android.R.transition.move)

        window.sharedElementReturnTransition =
            TransitionInflater.from(this)
                .inflateTransition(android.R.transition.move)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        // ================= GET DATA FROM INTENT =================
        item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("object", ParfumModel::class.java)
                ?: throw IllegalStateException("ParfumModel is null")
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("object") as? ParfumModel
                ?: throw IllegalStateException("ParfumModel is null")
        }

        // ================= SET UI =================
        with(binding) {
            txtTitle.text = item.title
            txtExtra.text = "-" // ✅ FIX FINAL (AMAN)
            txtDescription.text = item.description
            txtPrice.text = getString(R.string.price_format, item.price)

            Glide.with(this@DetailedActivity)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgParfum)
        }

        // ================= PICK IMAGE =================
        binding.btnUploadImage.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // ================= BUY / ADD TO CART =================
        binding.btnAddToCart.setOnClickListener {

            binding.btnAddToCart.isEnabled = false
            currentBranch = UserLocationManager.currentBranch

            productRepository.reduceStock(
                productId = item.id,
                onSuccess = {

                    val uri = selectedImageUri

                    if (uri != null) {
                        imageUploadHelper.uploadImage(
                            uri,
                            onSuccess = { imageUrl ->
                                item.imageUrl = imageUrl
                                saveOrderAndAddToCart()
                            },
                            onFailure = {
                                saveOrderAndAddToCart()
                            }
                        )
                    } else {
                        saveOrderAndAddToCart()
                    }
                },
                onFailure = {
                    Toast.makeText(
                        this,
                        "Stok cabang $currentBranch habis",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.btnAddToCart.isEnabled = true
                }
            )
        }
    }

    // ================= SAVE ORDER =================
    private fun saveOrderAndAddToCart() {

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val order = OrderModel(
            userId = userId,
            items = listOf(item),
            totalPrice = item.price * item.quantity,
            status = "Pending"
        )

        productRepository.saveOrder(
            order = order,
            onSuccess = {

                managementCart.addItem(item)

                Toast.makeText(
                    this,
                    "Order berhasil disimpan ($currentBranch)",
                    Toast.LENGTH_SHORT
                ).show()

                binding.btnAddToCart.isEnabled = true
            },
            onFailure = {
                Toast.makeText(
                    this,
                    "Gagal menyimpan order",
                    Toast.LENGTH_SHORT
                ).show()

                binding.btnAddToCart.isEnabled = true
            }
        )
    }
}
