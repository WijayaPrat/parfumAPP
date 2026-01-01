package com.wijayaprat.fragrancecenter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: T

    abstract fun inflateBinding(): T

    // ✅ WAJIB return MaterialToolbar?
    abstract fun getToolbar(): MaterialToolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflateBinding()
        setContentView(binding.root)

        // ✅ Pasang Toolbar jika ada
        getToolbar()?.let { toolbar ->
            setSupportActionBar(toolbar)
        }
    }
}
