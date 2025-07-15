package com.example.myapplication222

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.buttonCalculator).setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }
        findViewById<Button>(R.id.buttonList).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
        findViewById<Button>(R.id.buttonWeather).setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }
    }
}