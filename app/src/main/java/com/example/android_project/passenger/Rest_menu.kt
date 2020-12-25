package com.example.android_project.passenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.list_of_menu_of_restaurant_passenger
import com.example.android_project.classes.Dish
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_rest_menu.*


class rest_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_menu)

        val restName =  getIntent().getStringExtra("restName")

        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Restaurants/" + restName)
        restNameInMenu.setText(restName)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var menu: MutableList<Dish> = ArrayList()

                snapshot.children.mapNotNullTo(menu) { it.getValue<Dish>(Dish::class.java) }
                recycleView(menu)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.selectedItemId = R.id.restaurants

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


        rest_menu_return.setOnClickListener {
            go_to_rest()
        }

    }

    private fun recycleView(menu: MutableList<Dish>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rest_menu_recyclerview.layoutManager = layoutManager

        val adapter = list_of_menu_of_restaurant_passenger(this, menu)
        rest_menu_recyclerview.adapter = adapter
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
