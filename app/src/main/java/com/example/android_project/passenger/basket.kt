package com.example.android_project.passenger

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_project.R
import com.example.android_project.adapters.basket_adapter
import com.example.android_project.classes.OrderDish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_basket.*

class basket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)


        var database = FirebaseDatabase.getInstance()
        var ref = database.getReference("Basket/")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var basket: MutableList<OrderDish> = ArrayList()

                snapshot.children.mapNotNullTo(basket) { it.getValue<OrderDish>(OrderDish::class.java) }

                var price = 0

                for (i in basket) {
                    price += (i.price.toInt() * i.quantity.toInt())
                }
                basket_total_price.setText("Total: " + price.toString() + "$")
                recycleView(basket)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        basket_return.setOnClickListener {
            go_to_rest_main()
        }
        basketOrder.setOnClickListener {

            var database = FirebaseDatabase.getInstance()
            var ref = database.getReference("Basket/")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var basket: MutableList<OrderDish> = ArrayList()

                    snapshot.children.mapNotNullTo(basket) { it.getValue<OrderDish>(OrderDish::class.java) }
                    var orderRef = database.getReference("Deliverers/orders")
                    var orderNumberRef = database.getReference("Order")
                    orderNumberRef.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var orderNumber = snapshot.value.toString()



                            var newOrderRef = orderRef.child("Order №" + orderNumber)

                            var auth: FirebaseAuth
                            auth = Firebase.auth
                            var userId: String = auth.uid.toString()

                            var newOrderPassengerIdRef = newOrderRef.child("passengerId")
                            newOrderPassengerIdRef.setValue(userId)

                            var newOrderDelivererIdRef = newOrderRef.child("delivererId")
                            newOrderDelivererIdRef.setValue("")

                            var newOrderPassengerStatusRef = newOrderRef.child("status")
                            newOrderPassengerStatusRef.setValue("unassigned")

                            var newOrderPassengerFoodRef = newOrderRef.child("Dishes")

                            for (i in basket) {
                                var ref1 = newOrderPassengerFoodRef.child(i.name)
                                var ref2 = ref1.child("price")
                                ref2.setValue(i.price)
                                var ref3 = ref1.child("image")
                                ref3.setValue(i.image)
                                var ref4 = ref1.child("name")
                                ref4.setValue(i.name)
                                var ref5 = ref1.child("quantity")
                                ref5.setValue(i.quantity)
                            }
                            ref.removeValue()
                            orderNumberRef.setValue((orderNumber.toInt() + 1).toString())
                            applicationContext.toast("Order has successfully placed")
                            go_to_rest_main()
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
        }

    }

    private fun recycleView(basket: MutableList<OrderDish>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        basket_recyclerview.layoutManager = layoutManager

        val adapter = basket_adapter(this, basket)
        basket_recyclerview.adapter = adapter
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun go_to_rest_main() {
        startActivity(Intent(this, restaurants_main::class.java))
        overridePendingTransition(0, 0)
    }
}
