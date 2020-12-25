package com.example.android_project.deliverer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.my_specific_order_deliverer
import com.example.android_project.classes.Basket
import com.example.android_project.classes.DBOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_deliverer_certain_my_order.*
import kotlinx.android.synthetic.main.activity_deliverer_certain_unassigned_order.*

class deliverer_certain_myOrder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliverer_certain_my_order)

        val orderName =  getIntent().getStringExtra("delivererOrderName")

        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Deliverers/orders/" + orderName + "/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var order: DBOrder = DBOrder()

                val temp = snapshot.getValue<DBOrder>(DBOrder::class.java)!!
                delivererOrderName.setText(orderName)
                if (temp.status == "received") {
                    submitOrder.setText("Received")
                    submitOrder.setEnabled(false)
                }
                var dishRef = database.getReference("Deliverers/orders/" + orderName + "/Dishes")
                dishRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var dishes: ArrayList<Basket> = ArrayList()
                        snapshot.children.mapNotNullTo(dishes) { it.getValue<Basket>(Basket::class.java) }
                        if (temp != null) {
                            if (orderName != null) {
                                temp.nameOfOrder = orderName
                            }
                            temp.dishes = dishes
                            order = temp
                        }

                        recycleView(order.dishes)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        submitOrder.setOnClickListener {
            var dishRef = database.getReference("Deliverers/orders/" + orderName + "/status")
            dishRef.setValue("received")
            startActivity(Intent(this, deliverer_myOrders::class.java))
            overridePendingTransition(0, 0)
        }

        my_certain_order_deliverer_return.setOnClickListener {
            finish()
        }
    }
    private fun recycleView(order: MutableList<Basket>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        my_certain_order_deliverer_recycleview.layoutManager = layoutManager

        val adapter = my_specific_order_deliverer(this, order)
        my_certain_order_deliverer_recycleview.adapter = adapter
    }
}
