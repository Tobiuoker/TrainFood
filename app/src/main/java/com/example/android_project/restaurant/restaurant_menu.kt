package com.example.android_project.restaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.restaurant_main_menu_adapter
import com.example.android_project.classes.Dish
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_restaurant_menu.*

class restaurant_menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)
        var database = FirebaseDatabase.getInstance()

        var auth: FirebaseAuth
        auth = Firebase.auth
        var ref = database.getReference("RestUsers")
        var userId: String = auth.uid.toString()
        val newRef = ref.child(userId)
        newRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.value.toString()
                restaurantName.setText(name)


                getMenu(name)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val bottomNavigationView = findViewById(R.id.bottom_navigation_restaurant) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.menu
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


        restaurant_main_menu_add_button.setOnClickListener {
            startActivity(Intent(this, restaurant_addMenu::class.java))
            overridePendingTransition(0, 0)
        }

    }

    private fun getMenu(child: String) {
        var database2 = FirebaseDatabase.getInstance()
        var menuRef = database2.getReference("Restaurants")
        val newMenuRef = menuRef.child(child)
        println(newMenuRef)
        println("tuta")
        newMenuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var menu: MutableList<Dish> = ArrayList()
                println(menu)
                snapshot.children.mapNotNullTo(menu) { it.getValue<Dish>(Dish::class.java) }
                recycleView(menu)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun recycleView(menu: MutableList<Dish>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        restaurant_main_menu_recycleview.layoutManager = layoutManager

        val adapter = restaurant_main_menu_adapter(this, menu)
        restaurant_main_menu_recycleview.adapter = adapter
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
