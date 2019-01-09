package com.carlitosdroid.demo_move_items_recyclerview.helper

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class SimpleItemTouchHelperCallback(private val itemTouchHelperInterface: ItemTouchHelperInterface) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        if (p1.itemViewType != p2.itemViewType) {
            return false
        }
        itemTouchHelperInterface.onItemMove(p1.adapterPosition, p2.adapterPosition)
        return true
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        itemTouchHelperInterface.onItemDismiss(p0.adapterPosition)
    }

}