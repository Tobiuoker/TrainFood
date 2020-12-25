package com.example.android_project.passenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.android_project.MainActivity
import com.example.android_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_passenger_profile.*

class passenger_profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_profile)

        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.profile
        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.getItemId()) {
                        R.id.restaurants -> {
                            go_to_rest()
                            return true
                        }
                        R.id.my_orders -> {
                            go_to_myorders()
                            return true
                        }
                        R.id.profile -> {
                            go_to_profile()
                            return true
                        }
                    }
                    return true
                }
            })

        passangerLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun go_to_rest(){
        startActivity(Intent(this, restaurants_main::class.java))
        overridePendingTransition(0,0)
    }
    fun go_to_myorders(){
        startActivity(Intent(this, my_orders::class.java))
        overridePendingTransition(0,0)

    }
    fun go_to_profile(){
        startActivity(Intent(this, passenger_profile::class.java))
        overridePendingTransition(0,0)
    }
}
