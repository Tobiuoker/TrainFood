package com.example.android_project.passenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.list_of_menu_of_restaurant_passenger
import com.example.android_project.adapters.list_of_rest_adapter
import com.example.android_project.classes.Restaurant
import com.example.android_project.deliverer.deliverer_certain_myOrder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_rest_menu.*
import kotlinx.android.synthetic.main.activity_restaurants_main.*
import kotlinx.android.synthetic.main.my_orders_deliverer.view.*


class restaurants_main : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_main)

        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Restaurants/")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var menu: MutableList<String> = ArrayList()
                for (i in snapshot.children){
                    menu.add(i.key.toString())
                }

                var listView = findViewById(R.id.list_of_rest_listView) as ListView
                var restaurants: ArrayList<Restaurant> = ArrayList()
                listView.adapter = list_of_rest_adapter(applicationContext, menu)

                listView.setOnItemClickListener { parent, view, position, id ->
                    go_to_rest_menu(menu[position])
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView

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
                    return false
                }
            })

        basket_button.setOnClickListener {

            startActivity(Intent(this, basket::class.java))
            overridePendingTransition(0,0)
        }

    }

    fun go_to_rest_menu(name: String) {
//        startActivity(Intent(this, rest_menu::class.java))

        val intent = Intent(this, rest_menu::class.java)
        intent.putExtra("restName", name)
        startActivity(intent)
        overridePendingTransition(0, 0)

    }

    fun go_to_rest(){
        startActivity(Intent(this, restaurants_main::class.java))
        overridePendingTransition(0, 0)

    }
    fun go_to_myorders(){
        startActivity(Intent(this, my_orders::class.java))
        overridePendingTransition(0, 0)

    }
    fun go_to_profile(){
        startActivity(Intent(this, passenger_profile::class.java))
        overridePendingTransition(0, 0)
    }
    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}