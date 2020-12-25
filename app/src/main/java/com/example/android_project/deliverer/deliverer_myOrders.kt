package com.example.android_project.deliverer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.my_order_deliverer_adapter
import com.example.android_project.classes.Basket
import com.example.android_project.classes.DBOrder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_deliverer_my_orders.*

class deliverer_myOrders : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliverer_my_orders)


        var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()
        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Deliverers/orders")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var menu: MutableList<DBOrder> = ArrayList()

                for( i in snapshot.children) {
                    val temp = i.getValue<DBOrder>(DBOrder::class.java)!!
                    if (temp.delivererId == auth.uid.toString()) {
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


        val bottomNavigationView = findViewById(R.id.bottom_navigation_deliverer) as BottomNavigationView
        bottomNavigationView.selectedItemId = R.id.my_orders_deliverer
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

    }

    private fun recycleView(orders: MutableList<DBOrder>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_orders_deliverer_recycleview.layoutManager = layoutManager

        val adapter = my_order_deliverer_adapter(this, orders)
        my_orders_deliverer_recycleview.adapter = adapter
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