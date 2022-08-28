package com.example.projectfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectfinal.Adapter_Intent.FriendsAdapter
import com.example.projectfinal.Bitmap_Converter.BitmapConverter
import com.example.projectfinal.room.FriendData
import com.example.projectfinal.room.FriendsRoom
import com.example.projectfinal.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Detail_Activity : AppCompatActivity() {
    private val database by lazy { FriendsRoom
        (this) }
    lateinit var friendsAdapter: FriendsAdapter
    private lateinit var binding: ActivityDetailBinding
    var idFriend = 0
    var nama = ""
    var sekolah = ""
    var github = ""
    var fotoProfil = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        idFriend = intent.getIntExtra("KEY_ID", 0)
        nama = intent.getStringExtra("KEY_NAME") ?: ""
        sekolah = intent.getStringExtra("KEY_SCHOOL") ?: ""
        github = intent.getStringExtra("KEY_GITHUB") ?: ""
        fotoProfil = intent.getStringExtra("KEY_IMG") ?: ""

        val bitmap = BitmapConverter().stringToBitmap(this, fotoProfil)
        binding.imgDetailPhoto.setImageBitmap(bitmap)

        setupButton()
        viewFriend()
    }

    private fun setupButton() {
        val intentId = intent.getIntExtra("KEY_ID", 0)
        val name = intent.getStringExtra("KEY_NAME")
        val school = intent.getStringExtra("KEY_SCHOOL")
        val github = intent.getStringExtra("KEY_GITHUB")

        binding.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                database.friendsDao()
                    .deleteFriend(FriendData("$name", "$school", "$github", "$fotoProfil"))
            }
            finish()
        }
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this@Detail_Activity, Edit_Activity::class.java)
            intent.putExtra("KEY_ID", intentId)
            intent.putExtra("KEY_NAME", name)
            intent.putExtra("KEY_SCHOOL", school)
            intent.putExtra("KEY_GITHUB", github)
            intent.putExtra("KEY_IMG", fotoProfil)
            startActivity(intent)
        }
    }
    private fun viewFriend() {
        val name = intent.getStringExtra("KEY_NAME")
        val school = intent.getStringExtra("KEY_SCHOOL")
        val github = intent.getStringExtra("KEY_GITHUB")

        binding.tvName.setText(name)
        binding.tvSchool.setText(school)
        binding.tvGithub.setText(github)
    }

}




