package com.example.android_project.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.Basket
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.specific_order_cell.view.*


class my_specific_order_deliverer(var context: Context, var order: MutableList<Basket>): RecyclerView.Adapter<my_specific_order_deliverer.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.specific_order_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = order[position]
        holder.setData(order, position)
    }

    override fun getItemCount(): Int {
        return order.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
            }
        }

        @SuppressLint("ResourceAsColor")
        fun setData(order: Basket, pos: Int) {
            itemView.specific_order_title.text = order!!.name
            itemView.specific_order_quantity.text = "x" + order!!.quantity
            Picasso.get().load(order!!.image).into(itemView.specific_order_image)
        }
    }
}