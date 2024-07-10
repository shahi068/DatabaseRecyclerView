package com.adamco.databaserecyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adamco.databaserecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myUser: MyUser
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var databaseAdapter: DatabaseAdapter
    private val userList = mutableListOf<MyUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myUser = MyUser(null, "", 100)
        databaseHelper = DatabaseHelper(this)

        initRecyclerView()
        initViews()
    }

    private fun initRecyclerView() {
        databaseAdapter = DatabaseAdapter(userList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = databaseAdapter
        }
    }

    private fun initViews() {
        with(binding) {
            saveDetails.setOnClickListener {
                val userName = inputName.text.toString()
                val userAge = inputAge.text.toString().toInt()
                val record = MyUser(id = null, name = userName, age = userAge)

                databaseHelper.insertData(record)
                inputName.text?.clear()
                inputAge.text?.clear()

                if (inputID.text!!.isNotEmpty()) {
                    val toast = Toast.makeText(
                        this@MainActivity,
                        "You cannot save to an ID, try again by using the update button.",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                    inputID.text?.clear()
                }
            }

            btnShowAll.setOnClickListener {
                fetchData()
                databaseAdapter.notifyDataSetChanged()
            }

            updateDetails.setOnClickListener {
                val userName = inputName.text.toString()
                val userAge = inputAge.text.toString().toInt()
                val userID = inputID.text.toString().toLong()
                val record = MyUser(id = userID, name = userName, age = userAge)

                databaseHelper.updateUser(record)

                // Refresh the user list
                fetchData()
                databaseAdapter.notifyDataSetChanged()

                inputName.text?.clear()
                inputAge.text?.clear()
                inputID.text?.clear()
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val user = userList[position]
                    user.id?.let {
                        val deleted = databaseHelper.deleteUser(it)

                        if (deleted > 0) {
                            userList.removeAt(position)
                            databaseAdapter.notifyItemRemoved(position)
                        }
                    }
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
            }).attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun fetchData() {
        //this clears the userList so its blank and ready to be overwritten with
        // whatever is in the database
        userList.clear()
        //this updates the userList to have everything that the database has
        userList.addAll(databaseHelper.readData())
    }
}
