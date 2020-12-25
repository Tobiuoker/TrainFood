package com.example.android_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.classes.Dish
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rest_menu_cell.view.*
import java.util.EnumSet.range

class list_of_menu_of_restaurant_passenger(var context: Context, var menu: MutableList<Dish>): RecyclerView.Adapter<list_of_menu_of_restaurant_passenger.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rest_menu_cell, parent, false)
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
                //how to remove
//                menu.removeAt(adapterPosition)
//                notifyDataSetChanged()
            }

            itemView.addDishToBusket.setOnClickListener {
                var database = FirebaseDatabase.getInstance()
                var ref = database.getReference("Basket/")

                for (i in 0 until menu.size) {
                    if (menu[i].name == itemView.rest_menu_title.text) {
                        var ref1 = ref.child(menu[i].name)
                        var ref2 = ref1.child("price")
                        ref2.setValue(menu[i].price)
                        var ref3 = ref1.child("image")
                        ref3.setValue(menu[i].image)
                        var ref4 = ref1.child("name")
                        ref4.setValue(menu[i].name)
                        var ref5 = ref1.child("quantity")
                        ref5.setValue(itemView.rest_menu_counter.text.toString())
                    }
                }
            }

            itemView.rest_menu_minus.setOnClickListener {
                var temp = itemView.rest_menu_counter.text.toString().toInt()
                temp -= 1
                if (temp < 0) {
                    temp = 0
                }
                itemView.rest_menu_counter.setText(temp.toString())
            }
            itemView.rest_menu_plus.setOnClickListener {
                var temp = itemView.rest_menu_counter.text.toString().toInt()
                temp += 1
                itemView.rest_menu_counter.setText(temp.toString())
            }
        }

        fun setData(menu: Dish, pos: Int) {
            itemView.rest_menu_title.text = menu!!.name
            itemView.rest_menu_price.text = menu!!.price + "$"
            Picasso.get().load(menu!!.image).into(itemView.rest_menu_image)
        }
    }
}