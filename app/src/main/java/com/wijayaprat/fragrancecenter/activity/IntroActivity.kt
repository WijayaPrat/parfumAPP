package com.wijayaprat.fragrancecenter.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wijayaprat.fragrancecenter.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // langsung pindah ke MainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
