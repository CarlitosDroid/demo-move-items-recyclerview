package com.carlitosdroid.demo_move_items_recyclerview

import android.support.v4.view.MotionEventCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.carlitosdroid.demo_move_items_recyclerview.helper.ItemTouchHelperInterface
import kotlinx.android.synthetic.main.person_item.view.*

class PersonAdapter(val personArrayList: MutableList<Person>,
                    var onDragStartListener: OnDragStartListener) : RecyclerView.Adapter<PersonAdapter.MyViewHolder>(), ItemTouchHelperInterface {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(p0.context)
                .inflate(R.layout.person_item, p0, false))
    }

    override fun getItemCount() = personArrayList.size

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        myViewHolder.bind(personArrayList[position])
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val person = personArrayList[fromPosition]
        personArrayList.removeAt(fromPosition)
        personArrayList.add(toPosition, person)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        personArrayList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun enabledItems(enable: Boolean) {
        for (person in personArrayList) {
            person.drag = enable
        }
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.ivDrag.setOnTouchListener { _, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    onDragStartListener!!.onDragStarted(this)
                }
                false
            }
        }

        fun bind(person: Person) {
            itemView.tvName.text = person.name
            if (person.drag) {
                itemView.ivDrag.visibility = View.VISIBLE
            } else {
                itemView.ivDrag.visibility = View.GONE
            }
        }
    }

    interface OnDragStartListener {
        fun onDragStarted(viewHolder: RecyclerView.ViewHolder)
    }

}