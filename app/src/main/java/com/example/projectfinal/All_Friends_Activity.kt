package com.example.projectfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.projectfinal.Adapter_Intent.FriendsAdapterViewAll
import com.example.projectfinal.room.FriendsRoom
import com.example.projectfinal.databinding.ActivityAllFriendsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class All_Friends_Activity : AppCompatActivity() {

    private val database by lazy { FriendsRoom(this) }
    lateinit var friendsAdapterViewAll: FriendsAdapterViewAll
    private lateinit var binding: ActivityAllFriendsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_friends)
        binding = ActivityAllFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupButton()
    }

    private fun setupButton() {
        binding.edSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchDatabase(newText)
                }
                return true
            }

            private fun searchDatabase(query: String) {
                val searchQuery = "%$query%"

                CoroutineScope(Dispatchers.IO).launch {
                    val getData = database.friendsDAO().getsearchDatabase(searchQuery)
                    withContext(Dispatchers.Main) {
                        friendsAdapterViewAll.setData(getData)
                    }
                }
            }
        })
    }
    private fun setupRecyclerView() {
        friendsAdapterViewAll = FriendsAdapterViewAll(this, arrayListOf())


        binding.rvViewAll.adapter = friendsAdapterViewAll
    }


    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val getDataFriend = database.friendDao().getFriend()
            withContext(Dispatchers.Main) {
                friendsAdapterViewAll.setData(getDataFriend)
            }
        }
    }
}