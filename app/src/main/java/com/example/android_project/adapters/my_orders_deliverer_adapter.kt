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
import com.example.android_project.deliverer.deliverer_certain_myOrder
import com.example.android_project.deliverer.deliverer_certain_unassignedOrder
import kotlinx.android.synthetic.main.all_orders_deliverer.view.*
import kotlinx.android.synthetic.main.my_orders_deliverer.view.*

class my_order_deliverer_adapter(var context: Context, var my_orders: MutableList<DBOrder>): RecyclerView.Adapter<my_order_deliverer_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_orders_deliverer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orders = my_orders[position]
        holder.setData(orders, position)
    }

    override fun getItemCount(): Int {
        return my_orders.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.getContext(), deliverer_certain_myOrder::class.java)
                intent.putExtra("delivererOrderName", itemView.my_orders_deliverer_title.text)
                itemView.getContext().startActivity(intent)
            }
        }

        @SuppressLint("ResourceAsColor")
        fun setData(my_orders: DBOrder, pos: Int) {
            itemView.my_orders_deliverer_title.text = my_orders!!.nameOfOrder
        }
    }
}