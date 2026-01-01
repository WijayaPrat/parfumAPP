package com.wijayaprat.fragrancecenter.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.wijayaprat.fragrancecenter.R
import com.wijayaprat.fragrancecenter.databinding.ActivityMainBinding
import com.wijayaprat.fragrancecenter.helper.ManagementCart

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var menuRef: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuRef = menu

        val cartItem = menu.findItem(R.id.action_cart)
        val actionView = cartItem.actionView

        actionView?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        updateCartBadge()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }

            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        updateCartBadge()
    }

    private fun updateCartBadge() {
        val menu = menuRef ?: return
        val cartItem = menu.findItem(R.id.action_cart)
        val actionView = cartItem.actionView ?: return

        val badge = actionView.findViewById<TextView>(R.id.cart_badge)
        val totalQty = ManagementCart(this)
            .getCart()
            .sumOf { it.quantity }

        badge.text = totalQty.toString()
        badge.visibility =
            if (totalQty > 0) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}