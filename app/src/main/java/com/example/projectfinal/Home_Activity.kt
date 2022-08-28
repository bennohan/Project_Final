package com.example.projectfinal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.RoomDatabase
import com.example.projectfinal.Adapter_Intent.FriendsAdapter
import com.example.projectfinal.databinding.ActivityHomeBinding
import com.example.projectfinal.room.FriendsRoom
import kotlinx.android.synthetic.main.activity_all_friends.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.edSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home_Activity : AppCompatActivity() {

    private val database by lazy {FriendsRoom(this) }
    lateinit var friendsAdapter: FriendsAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButton()
        setupRecyclerView()
    }
    private fun setupButton() {
        binding.edSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
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
                    val getData = database.friendsDao().getsearchDatabase(searchQuery)
                    withContext(Dispatchers.Main) {
                        friendsAdapter.setData(getData)
                    }
                }
            }
        })

        flAdd.setOnClickListener {
            val moveAdd = Intent(this, Add_Activity::class.java)
            startActivity(moveAdd)
        }
        tvViewAll.setOnClickListener {
        }
    }

    private fun setupRecyclerView() {
        friendsAdapter = FriendsAdapter(this, arrayListOf())

        binding.rvNewFriend.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNewFriend.adapter = friendsAdapter
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val getDataFriend = database.friendsDao().getFriend()
            Log.d("AddActivity", "dataFriendResponse: $getDataFriend")
            withContext(Dispatchers.Main) {
                friendsAdapter.setData(getDataFriend)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbarhome, menu)
        val search = menu?.findItem(R.id.icSearch)
        val searchView = search?.actionView as? SearchView


        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
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
                    val getData = database.friendsDao().getsearchDatabase(searchQuery)
                    withContext(Dispatchers.Main) {
                        friendsAdapter.setData(getData)
                    }
                }
            }
        })
        return true
    }

}
