package com.example.android_project.restaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.example.android_project.MainActivity
import com.example.android_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_restaurant_add_menu.*
import kotlinx.android.synthetic.main.activity_restaurant_menu.*
import kotlinx.android.synthetic.main.activity_restaurant_profile.*

class restaurant_profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var database = FirebaseDatabase.getInstance()

        var auth: FirebaseAuth
        auth = Firebase.auth
        var ref = database.getReference("RestUsers")
        var userId: String = auth.uid.toString()
        val newRef = ref.child(userId)
        newRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restName = snapshot.value.toString()
                restProfileName.setText(restName)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        setContentView(R.layout.activity_restaurant_profile)
        val bottomNavigationView = findViewById(R.id.bottom_navigation_restaurant) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.information
        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {

                    when (item.getItemId()) {
                        R.id.menu -> {
                            go_to_menu()
                            return true
                        }
                        R.id.information -> {
                            go_to_resturant_profile()
                            return true
                        }
                    }
                    return false
                }
            })

        restLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun go_to_menu() {
        startActivity(Intent(this, restaurant_menu::class.java))
        overridePendingTransition(0, 0)
    }

    fun go_to_resturant_profile() {
        startActivity(Intent(this, restaurant_profile::class.java))
        overridePendingTransition(0, 0)

    }
}
