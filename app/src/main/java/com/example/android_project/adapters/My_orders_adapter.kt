package com.example.android_project.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.DBOrder
import com.example.android_project.passenger.specific_order
import kotlinx.android.synthetic.main.my_orders_cell.view.*

interface CallbackInterface {
    fun passDishesDescription(dishes: MutableList<DBOrder>)
}

class My_orders_adapter(var context: Context, var my_orders: MutableList<DBOrder>): RecyclerView.Adapter<My_orders_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_orders_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = my_orders[position]
        holder.setData(order, position)
    }

    override fun getItemCount(): Int {
        return my_orders.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.getContext(), specific_order::class.java)
                intent.putExtra("orderName", itemView.my_orders_title.text)
                itemView.getContext().startActivity(intent)
            }
        }

        @SuppressLint("ResourceAsColor")
        fun setData(my_orders: DBOrder, pos: Int) {
            itemView.my_orders_title.text = my_orders!!.nameOfOrder
            itemView.my_orders_status.text = my_orders!!.status
            if (my_orders.status == "unassigned") {
                itemView.my_orders_status.setText("Ordered")
                itemView.my_orders_status.setBackgroundColor(
                    itemView.my_orders_status.getContext().getResources().getColor(
                        R.color.ordered
                    )
                )
            }
            else if (my_orders.status == "onTheWay") {
                itemView.my_orders_status.setText("On The Way")
                itemView.my_orders_status.setBackgroundColor(
                    itemView.my_orders_status.getContext().getResources().getColor(
                        R.color.onTheWay
                    )
                )
            }
            else if (my_orders.status == "received") {
                itemView.my_orders_status.setText("Received")
                itemView.my_orders_status.setBackgroundColor(
                    itemView.my_orders_status.getContext().getResources().getColor(
                        R.color.received
                    )
                )
            }
        }
    }
}