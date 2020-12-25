package com.example.android_project.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.OrderDish
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.basket_cell.view.*
import kotlinx.android.synthetic.main.rest_menu_cell.view.*

class basket_adapter(var context: Context, var basket: MutableList<OrderDish>): RecyclerView.Adapter<basket_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.basket_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baskett = basket[position]
        holder.setData(baskett, position)
    }

    override fun getItemCount(): Int {
        return basket.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {

            }
            itemView.basket_remove.setOnClickListener {
                basket.removeAt(adapterPosition)
                var databse = FirebaseDatabase.getInstance()
                var ref = databse.getReference("Basket/" + itemView.basket_title.text.toString())
                ref.removeValue()
                context.startActivity(Intent(context, com.example.android_project.passenger.basket::class.java))
                notifyDataSetChanged()
            }
        }

        fun setData(basket: OrderDish, pos: Int) {
            println(itemView.basket_price.text)
            itemView.basket_price.text = basket!!.price
            itemView.basket_quantity.text = "x" + basket!!.quantity
            itemView.basket_title.text = basket!!.name
            Picasso.get().load(basket!!.image).into(itemView.basket_image)
        }
    }
}