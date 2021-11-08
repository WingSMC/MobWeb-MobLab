package hu.bme.aut.android.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.shoppinglist.adapter.ShoppingAdapter
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.data.ShoppingListDatabase
import hu.bme.aut.android.shoppinglist.databinding.ActivityMainBinding
import hu.bme.aut.android.shoppinglist.fragments.NewShoppingItemDialogFragment
import kotlin.concurrent.thread

class MainActivity :
    AppCompatActivity(),
    ShoppingAdapter.ShoppingItemClickListener,
    NewShoppingItemDialogFragment.NewShoppingItemDialogListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: ShoppingListDatabase
    private lateinit var adapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = ShoppingListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewShoppingItemDialogFragment().show(
                supportFragmentManager,
                NewShoppingItemDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        menu?.findItem(R.id.menu_delete_all)?.setOnMenuItemClickListener {
            adapter.removeAll()
            onAllItemsDeleted()
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onShoppingItemCreated(newItem: ShoppingItem) {
        thread {
            val insertId = database.shoppingItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    private fun initRecyclerView() {
        adapter = ShoppingAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.shoppingItemDao().getAll()
            runOnUiThread {
                adapter.loadItems(items)
            }
        }
    }

    override fun onItemChanged(item: ShoppingItem) {
        thread {
            database.shoppingItemDao().update(item)
            Log.d("MainActivity", "ShoppingItem update was successful")
        }
    }
    override fun onItemDeleted(item: ShoppingItem) {
        thread {
            database.shoppingItemDao().deleteItem(item)
            Log.d("MainActivity", "ShoppingItem deletion was successful")
        }
    }
    fun onAllItemsDeleted() {
        thread {
            database.shoppingItemDao().deleteAll()
        }
    }
}
