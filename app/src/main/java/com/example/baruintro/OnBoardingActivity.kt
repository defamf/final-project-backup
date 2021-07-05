package com.example.baruintro

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.baruintro.adapter.OnBoardingViewPagerAdapter
import com.example.baruintro.model.OnBoardingData
import com.google.android.material.tabs.TabLayout

class OnBoardingActivity : AppCompatActivity() {

    var OnBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var next: TextView? = null
    var position = 0
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(restorePrefData()){
            val i=Intent(applicationContext, MainCRUD::class.java)
            startActivity(i)
            finish()
        }
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tab_indicator)
        next = findViewById(R.id.next)


        val onBoardingData:MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Smart Wallet","Memberikan keamanan dan kemudahan untuk menyimpan uang digital kamu",R.drawable.kedua))
        onBoardingData.add(OnBoardingData("Smart Invest","Temukan berbagai keuntungan investasi dengan manajemen resiko yang bermutu",R.drawable.ketiga))
        onBoardingData.add(OnBoardingData("Smart Calculator","Menganalisis pengeluaran anda untuk mendapatkan penawaran terbaik dari kami",R.drawable.keempat))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        next?.setOnClickListener{
            if(position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position

            }

            if(position == onBoardingData.size){
                savePrefData()
                val i=Intent(applicationContext, MainCRUD::class.java)
                startActivity(i)
                finishAffinity()
            }

        }

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if(tab.position == onBoardingData.size - 1){
                    next!!.text = "Get Started"

                }else{
                    next!!.text = "Next"
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager);
        OnBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager!!.adapter = OnBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }

    private fun savePrefData(){
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor =  sharedPreferences!!.edit()
        editor.putBoolean("IsFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean{
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("IsFirstTimeRun", false)
    }
}