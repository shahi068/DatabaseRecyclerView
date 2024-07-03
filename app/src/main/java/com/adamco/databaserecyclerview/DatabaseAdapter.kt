package com.adamco.databaserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adamco.databaserecyclerview.databinding.ActivityMainBinding
import com.adamco.databaserecyclerview.databinding.UserItemBinding

class DatabaseAdapter(val userDetails : List<MyUser>) : RecyclerView.Adapter<DatabaseAdapter.UserItemViewHolder>(){
    private lateinit var binding: UserItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.bind(userDetails[position])
    }

    override fun getItemCount() = userDetails.size

    inner class UserItemViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: MyUser) {
            with(binding) {
                txtName.setText(user.name)
                txtAge.setText(user.age.toString())
            }
        }
    }


}



