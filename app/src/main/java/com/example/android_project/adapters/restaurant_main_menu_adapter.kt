package com.example.android_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.Dish
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rest_menu_cell.view.*
import kotlinx.android.synthetic.main.resturant_main_menu.view.*

class restaurant_main_menu_adapter(var context: Context, var menu: MutableList<Dish>): RecyclerView.Adapter<restaurant_main_menu_adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.resturant_main_menu, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val menu = menu[position]
            holder.setData(menu, position)
        }

        override fun getItemCount(): Int {
            return menu.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            init {
                itemView.setOnClickListener {
                }
            }

            fun setData(menu: Dish, pos: Int) {
                itemView.restaurant_menu_price.text = menu!!.price + "$"
                itemView.restaurant_menu_title.text = menu!!.name
                Picasso.get().load(menu!!.image).into(itemView.restaurant_menu_image)
            }
        }
    }