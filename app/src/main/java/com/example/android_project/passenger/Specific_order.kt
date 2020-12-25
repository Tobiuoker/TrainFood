package com.example.android_project.passenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.Specific_order_adapter
import com.example.android_project.classes.Basket
import com.example.android_project.classes.DBOrder
import com.example.android_project.classes.Specific_order
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_specific_order.*
import kotlinx.android.synthetic.main.my_orders_cell.view.*

class specific_order : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_order)

        val orderName =  getIntent().getStringExtra("orderName")

        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Deliverers/orders/" + orderName + "/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var order: DBOrder = DBOrder()

                val temp = snapshot.getValue<DBOrder>(DBOrder::class.java)
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


                        if (order.status == "unassigned") {
                            specific_order_button.setText("Ordered")
                            specific_order_button.setBackgroundColor(
                                applicationContext.getResources().getColor(
                                    R.color.ordered
                                )
                            )
                        }
                        else if (order.status == "onTheWay") {
                            specific_order_button.setText("On The Way")
                            specific_order_button.setBackgroundColor(
                                applicationContext.getResources().getColor(
                                    R.color.onTheWay
                                )
                            )
                        }
                        else if (order.status == "received") {
                            specific_order_button.setText("Received")
                            specific_order_button.setBackgroundColor(
                                applicationContext.getResources().getColor(
                                    R.color.received
                                )
                            )
                        }
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

        specific_order_return.setOnClickListener {
            finish()
        }
    }

    private fun recycleView(order: MutableList<Basket>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        specific_order_recycleview.layoutManager = layoutManager

        val adapter = Specific_order_adapter(this, order)
        specific_order_recycleview.adapter = adapter
    }

}
