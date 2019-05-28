package com.gamalinda.android.poc.testing.collapsingtoolbar.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
