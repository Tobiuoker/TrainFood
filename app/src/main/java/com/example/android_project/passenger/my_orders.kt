package com.example.android_project.passenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.CallbackInterface
import com.example.android_project.adapters.My_orders_adapter
import com.example.android_project.classes.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_my_orders.*

class my_orders : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)
        var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Deliverers/orders")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var menu: MutableList<DBOrder> = ArrayList()

                for( i in snapshot.children) {
                    val temp = i.getValue<DBOrder>(DBOrder::class.java)!!
                    if (temp.passengerId == auth.uid.toString()) {
                        var dishRef = database.getReference("Deliverers/orders/" + i.key.toString() + "/Dishes")
                        dishRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var dishes: ArrayList<Basket> = ArrayList()
                                snapshot.children.mapNotNullTo(dishes) { it.getValue<Basket>(Basket::class.java) }
                                if (temp != null) {
                                    temp.nameOfOrder = i.key.toString()
                                    temp.dishes = dishes
                                    menu.add(temp)
                                }

                                recycleView(menu)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.my_orders
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

    }

    private fun recycleView(basket: MutableList<DBOrder>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_orders_recycleview.layoutManager = layoutManager

        val adapter = My_orders_adapter(this, basket)
        my_orders_recycleview.adapter = adapter
    }

    fun go_to_rest(){
        startActivity(Intent(this, restaurants_main::class.java))
        overridePendingTransition(0,0)
    }
    fun go_to_myorders(){
//        startActivity(Intent(this, my_orders::class.java))

    }
    fun go_to_profile(){
        startActivity(Intent(this, passenger_profile::class.java))
        overridePendingTransition(0,0)
    }

}
