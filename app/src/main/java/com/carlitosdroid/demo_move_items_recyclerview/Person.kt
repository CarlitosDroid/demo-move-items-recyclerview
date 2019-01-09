package com.carlitosdroid.demo_move_items_recyclerview

data class Person(val id: Int,
                  val name: String,
                  val dni: String,
                  var drag:Boolean=false)