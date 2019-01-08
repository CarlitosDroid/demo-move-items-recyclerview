package com.carlitosdroid.demo_move_items_recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.carlitosdroid.demo_move_items_recyclerview.helper.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), PersonAdapter.OnDragStartListener{

    private var actionMode: ActionMode? = null
    private var actionModeCallback: ActionModeCallback? = null

    private var mItemTouchHelper : ItemTouchHelper? = null
    val people : MutableList<Person> = mutableListOf()
    var adapterPerson : PersonAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            if (actionMode == null) {
                actionMode = startSupportActionMode(actionModeCallback!!)
                adapterPerson!!.enabledItems(true)
            }
        }
        people.add(Person(1, "Bill", "72041666"))
        people.add(Person(2, "cris", "72041789"))
        people.add(Person(3, "jamil", "72041616"))
        people.add(Person(1, "Carlos", "72041666"))
        people.add(Person(2, "Gerardo", "72041789"))
        people.add(Person(3, "pilar", "72041616"))
        people.add(Person(1, "Jhona", "72041666"))
        people.add(Person(2, "alex", "72041789"))
        people.add(Person(3, "sonaly", "72041616"))
        people.add(Person(3, "Jan", "72041616"))
        people.add(Person(3, "Pablo", "72041616"))

        adapterPerson = PersonAdapter(people,this)
        rvPerson.adapter = adapterPerson
        rvPerson.layoutManager = LinearLayoutManager(this)

        val callback = SimpleItemTouchHelperCallback(adapterPerson!!)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(rvPerson)
        actionModeCallback = ActionModeCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class ActionModeCallback : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_native_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_delete -> {
                    adapterPerson!!.enabledItems(false)
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }

    override fun onDragStarted(viewHolder: RecyclerView.ViewHolder) {
        mItemTouchHelper!!.startDrag(viewHolder)
    }

}
