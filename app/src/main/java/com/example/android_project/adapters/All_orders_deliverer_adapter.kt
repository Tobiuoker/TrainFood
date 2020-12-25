package com.example.android_project.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.DBOrder
import com.example.android_project.deliverer.deliverer_certain_unassignedOrder
import com.example.android_project.passenger.specific_order
import kotlinx.android.synthetic.main.all_orders_deliverer.view.*
import kotlinx.android.synthetic.main.my_orders_cell.view.*

class All_orders_deliverer_adapter(var context: Context, var orders: MutableList<DBOrder>): RecyclerView.Adapter<All_orders_deliverer_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.all_orders_deliverer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orders = orders[position]
        holder.setData(orders, position)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
//                val intent = Intent(itemView.getContext(), deliverer_certain_unassignedOrder::class.java)
//                itemView.getContext().startActivity(intent)

                val intent = Intent(itemView.getContext(), deliverer_certain_unassignedOrder::class.java)
                intent.putExtra("unassignedOrderName", itemView.all_orders_deliverer_title.text)
                itemView.getContext().startActivity(intent)
            }

        }

        fun setData(orders: DBOrder, pos: Int) {
            itemView.all_orders_deliverer_title.text = orders!!.nameOfOrder
            itemView.all_orders_deliverer_status.text = orders!!.status
        }
    }
}