package com.carlitosdroid.demo_move_items_recyclerview.helper

interface ItemTouchHelperInterface {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

