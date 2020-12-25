package com.example.android_project.classes

data class  DBOrder (
    var dishes: ArrayList<Basket> = ArrayList(),
    var delivererId: String = "",
    var passengerId: String = "",
    var status: String = "",
    var nameOfOrder: String = ""
    )