package hu.bme.aut.android.shoppinglist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.shoppinglist.R
import hu.bme.aut.android.shoppinglist.data.ShoppingItem
import hu.bme.aut.android.shoppinglist.databinding.ItemShoppingListBinding

class ShoppingAdapter(private val listener: ShoppingItemClickListener) :
    RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>() {

    private val items = mutableListOf<ShoppingItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShoppingViewHolder(
        ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val shoppingItem = items[position]

        with(holder.binding) {
            ivIcon.setImageResource(getImageResource(shoppingItem.category))
            cbIsBought.isChecked = shoppingItem.isBought
            tvDescription.text = shoppingItem.description
            tvName.text = shoppingItem.name
            tvCategory.text = shoppingItem.category.name
            tvPrice.text = "${shoppingItem.estimatedPrice} Ft"
            cbIsBought.setOnCheckedChangeListener { _, isChecked ->
                shoppingItem.isBought = isChecked
                listener.onItemChanged(shoppingItem)
            }
            ibRemove.setOnClickListener {
                deleteItem(shoppingItem)
                listener.onItemDeleted(shoppingItem)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface ShoppingItemClickListener {
        fun onItemChanged(item: ShoppingItem)
        fun onItemDeleted(item: ShoppingItem)
    }

    inner class ShoppingViewHolder(val binding: ItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root)

    @DrawableRes()
    private fun getImageResource(category: ShoppingItem.Category): Int {
        return when (category) {
            ShoppingItem.Category.FOOD -> R.drawable.groceries
            ShoppingItem.Category.ELECTRONIC -> R.drawable.lightning
            ShoppingItem.Category.BOOK -> R.drawable.open_book
        }
    }

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun deleteItem(item: ShoppingItem) {
        val index = items.indexOf(item)
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAll() {
        items.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadItems(shoppingItems: List<ShoppingItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }
}
