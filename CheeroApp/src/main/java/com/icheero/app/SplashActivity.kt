package com.icheero.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.icheero.app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initData()
    }

    private fun initData() {
        binding.name.text = "视图绑定"
    }

}