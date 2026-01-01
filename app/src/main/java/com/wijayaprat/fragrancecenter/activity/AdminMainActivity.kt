package com.wijayaprat.fragrancecenter.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.adapter.ParfumAdapter
import com.wijayaprat.fragrancecenter.databinding.ActivityAdminMainBinding
import com.wijayaprat.fragrancecenter.helper.ProductRepository
import com.wijayaprat.fragrancecenter.helper.UserSession

class AdminMainActivity : BaseActivity<ActivityAdminMainBinding>() {

    private val repository = ProductRepository()
    private lateinit var adapter: ParfumAdapter

    // 🔹 WAJIB untuk BaseActivity
    override fun inflateBinding(): ActivityAdminMainBinding {
        return ActivityAdminMainBinding.inflate(layoutInflater)
    }

    override fun getToolbar(): com.google.android.material.appbar.MaterialToolbar? {
        return binding.toolbarMain.topAppBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Admin"

        adapter = ParfumAdapter(mutableListOf()) {
            // admin klik item (opsional)
        }

        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter

        binding.fabAddProduct.setOnClickListener {
            startActivity(Intent(this, AdminAddProductActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_admin_orders -> {
                startActivity(Intent(this, AdminOrderActivity::class.java))
                true
            }

            R.id.menu_logout -> {
                FirebaseAuth.getInstance().signOut()
                UserSession.role = ""

                val intent = Intent(this, LoginActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                true
            }

            else -> super.onOptionsItemSelected(item)
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
                // optional
            }
        )
    }
}
