package com.example.android_project.deliverer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.android_project.MainActivity
import com.example.android_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_deliverer_profile.*

class deliverer_profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliverer_profile)
        val bottomNavigationView = findViewById(R.id.bottom_navigation_deliverer) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.profile_deliverer
        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {

                    when (item.getItemId()) {
                        R.id.all_orders -> {
                            go_to_all_order()
                            return true
                        }
                        R.id.my_orders_deliverer -> {
                            go_to_myorders_deliverer()
                            return true
                        }
                        R.id.profile_deliverer -> {
                            go_to_profile_deliverer()
                            return true
                        }
                    }
                    return true
                }
            })

        delivererLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    fun go_to_all_order() {
        startActivity(Intent(this, deliverer_allOrders::class.java))
        overridePendingTransition(0, 0)
    }

    fun go_to_myorders_deliverer() {
        startActivity(Intent(this, deliverer_myOrders::class.java))
        overridePendingTransition(0, 0)

    }

    fun go_to_profile_deliverer() {
        startActivity(Intent(this, deliverer_profile::class.java))
        overridePendingTransition(0, 0)
    }
}
