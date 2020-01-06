package com.pstagram.pstagram.pstagram.view

import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pstagram.pstagram.pstagram.R
import com.pstagram.pstagram.pstagram.UserIdManager
import com.pstagram.pstagram.pstagram.adapter.ViewPagerAdapter
import com.pstagram.pstagram.pstagram.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if(UserIdManager.getInstance(this).userId == 0){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding.viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.tablayout.setupWithViewPager(binding.viewpager)
    }
}
