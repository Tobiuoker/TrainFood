package com.example.android_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.android_project.R
import com.example.android_project.classes.Restaurant


class list_of_rest_adapter(var context: Context, var restaurants: MutableList<String>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var title: TextView
        var image: ImageView

        init {
            this.title = row?.findViewById(R.id.list_of_rest_title) as TextView
            this.image = row?.findViewById(R.id.list_of_rest_image) as ImageView
        }
    }

    override fun getCount(): Int {
        return restaurants.count()
    }

    override fun getItem(position: Int): Any {
        return restaurants.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder?
        if( convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.list_of_rest, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var rest = restaurants[position]
        viewHolder.title.text = rest
        viewHolder.image.setImageResource(R.drawable.restlogo)
        return view as View
    }


}