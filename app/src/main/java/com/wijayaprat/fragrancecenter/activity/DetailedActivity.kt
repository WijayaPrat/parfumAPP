package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ActivityDetailedBinding
import com.wijayaprat.fragrancecenter.helper.ManagementCart
import com.wijayaprat.fragrancecenter.model.ParfumModel

class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        val item = ParfumModel(
            title = intent.getStringExtra("title") ?: "",
            extra = intent.getStringExtra("extra") ?: "",
            description = intent.getStringExtra("description") ?: "",
            price = intent.getIntExtra("price", 0),
            picUrl = intent.getStringExtra("picUrl") ?: "",
            quantity = 1
        )

        binding.txtTitle.text = item.title
        binding.txtExtra.text = item.extra
        binding.txtDescription.text = item.description
        binding.txtPrice.text = getString(R.string.price_format, item.price)

        Glide.with(this)
            .load(item.picUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.imgParfum)

        binding.btnAddToCart.setOnClickListener {
            managementCart.addItem(item)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}
