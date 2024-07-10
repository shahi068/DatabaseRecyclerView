package com.adamco.databaserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.adamco.databaserecyclerview.databinding.UserItemBinding

class DatabaseAdapter(private val userDetails: MutableList<MyUser>) : RecyclerView.Adapter<DatabaseAdapter.UserItemViewHolder>() {
    private lateinit var binding: UserItemBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        databaseHelper = DatabaseHelper(parent.context)
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(userDetails[position])
    }

    override fun getItemCount() = userDetails.size

    inner class UserItemViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: MyUser) {
            with(binding) {
                txtName.setText(user.name)
                txtAge.setText(user.age.toString())

                ivMore.setOnClickListener {
                    val popup = PopupMenu(itemView.context, ivMore)

                    popup.menu.add("Edit")
                    popup.menu.add("Delete")

                    popup.show()

                    popup.setOnMenuItemClickListener {
                        when (it.title) {
                            "Edit" -> {
                                Toast.makeText(itemView.context, "Edit clicked", Toast.LENGTH_SHORT).show()
                            }
                            "Delete" -> {
                                Toast.makeText(itemView.context, "Delete clicked", Toast.LENGTH_SHORT).show()
                                removeItem(adapterPosition)
                            }
                        }
                        true
                    }
                }
            }
        }
    }

    private fun removeItem(position: Int) {
        val user = userDetails[position]

        //THIS IS TO REMOVE THE USER FROM DB
        user.id?.let { databaseHelper.deleteUser(it.toLong()) }

        // THIS IS TO REMOVE USER FROM THE LIST
        userDetails.removeAt(position)

        // THEN NOTIFY THE ADAPTER THAT WE REMOVED THE ITEM
        notifyItemRemoved(position)
    }
}
