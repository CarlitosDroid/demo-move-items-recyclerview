package com.carlitosdroid.demo_move_items_recyclerview.main

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.carlitosdroid.demo_move_items_recyclerview.model.Person
import com.carlitosdroid.demo_move_items_recyclerview.R
import com.carlitosdroid.demo_move_items_recyclerview.helper.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var actionMode: ActionMode? = null
    private var actionModeCallback: ActionModeCallback? = null

    private var mItemTouchHelper: ItemTouchHelper? = null

    private val personList: MutableList<Person> = mutableListOf(
            Person(1, "Tipo de operación", "72041666"),
            Person(2, "Tipo de Inmueble", "72041789"),
            Person(3, "Ubicación", "72041789"),
            Person(4, "Precio", "72041789"),
            Person(5, "Área Total", "72041789"),
            Person(6, "Dormitorios", "72041789"),
            Person(7, "Baños", "72041789"),
            Person(8, "Cocheras", "72041789"),
            Person(9, "Antiguedad", "72041789"),
            Person(10, "Tipo de Publicación", "72041789"),
            Person(10, "Estado de Inmueble", "72041789")
    )

    private val personAdapter = PersonAdapter(personList) { myViewHolder ->
        mItemTouchHelper!!.startDrag(myViewHolder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpToolbar()

        rvPerson.adapter = personAdapter
        rvPerson.layoutManager = LinearLayoutManager(this)

        val callback = SimpleItemTouchHelperCallback(personAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(rvPerson)

        actionModeCallback = ActionModeCallback(personAdapter)

    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
            R.id.action_move -> {
                if (actionMode == null) {
                    actionMode = startSupportActionMode(actionModeCallback!!)
                    actionMode!!.title = "Mover Items"
                    personAdapter.enabledItems(true)
                }
                true
            }

            R.id.action_clear1 -> {
                true
            }


            R.id.action_clear2 -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class ActionModeCallback(val personAdapter: PersonAdapter) : ActionMode.Callback {

        private var statusBarColor: Int = 0

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.menu_native_mode, menu)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = window.statusBarColor
                window.statusBarColor = Color.GRAY
            }
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_delete -> {
                    personAdapter.enabledItems(false)
                    mode.finish()
                    true
                }

                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            personAdapter.enabledItems(false)
            actionMode = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) window.statusBarColor = statusBarColor
        }
    }
}
