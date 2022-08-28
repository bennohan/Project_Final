package com.example.projectfinal

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.projectfinal.Adapter_Intent.FriendsAdapter
import com.example.projectfinal.Bitmap_Converter.BitmapConverter
import com.example.projectfinal.room.FriendData
import com.example.projectfinal.room.FriendsRoom
import com.example.projectfinal.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_Activity : AppCompatActivity() {
    private val database by lazy{FriendsRoom(this)}
    lateinit var friendsAdapter: FriendsAdapter
    private val displaySearch = ArrayList<FriendData>()
    private val list = ArrayList<FriendData>()
    lateinit var binding: ActivityAddBinding
    var idFriend = 0
    var nama = ""
    var sekolah = ""
    var github = ""
    var fotoProfil = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)

        saveFriends()
    }

    private var activityLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        it.data?.data?.let { uri ->

            val bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }

            binding.imgAddPhoto.setImageBitmap(bitmapImage)
            fotoProfil = BitmapConverter().bitmapToString(bitmapImage)


        }

    }

    private fun checkPermissionGallery(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionGallery() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            110
        )
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityLauncherGallery.launch(galleryIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 110) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                openGallery()
            } else {
                Toast.makeText(this, "Izin Di Tolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveFriends() {
        //progress dialog
        binding.imgAddPhoto.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Harus menggunaan foto di bawah 5mb")
            builder.setPositiveButton("Ya", { dialogInterface: DialogInterface, i: Int ->
                if (checkPermissionGallery()) {
                    openGallery()
                } else {
                    requestPermissionGallery()
                }
            })
            builder.setNegativeButton("Batal", { dialogInterface: DialogInterface, i: Int ->
                finish()
            })
            builder.show()
        }

        binding.btnSaveAdd.setOnClickListener {
            if (binding.edNameAdd.text?.trim().toString().isEmpty() || binding.edNameAdd.text?.trim().toString()
                    .isEmpty()
            ) {
                binding.edNameAdd.error = "Field tidak boleh kosong!"
                binding.edSchoolAdd.error = "Field tidak boleh kosong"
                binding.edGithubAdd.error = "Field tidak boleh kosong"
            } else {

                val editName = binding.edNameAdd.text?.trim().toString()
                val editSchool = binding.edSchoolAdd.text?.trim().toString()
                val editGithub = binding.edGithubAdd.text?.trim().toString()
                val id = intent.getIntExtra("KEY_ID", 0)

                CoroutineScope(Dispatchers.IO).launch {
                    database.friendsDao()
                        .addFriend(FriendData( editName, editSchool, editGithub, fotoProfil))
                }
                finish()
            }
        }
    }

}

