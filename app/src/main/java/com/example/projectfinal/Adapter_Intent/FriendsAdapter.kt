package com.example.projectfinal.Adapter_Intent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfinal.Bitmap_Converter.BitmapConverter
import com.example.projectfinal.room.FriendData
import com.example.projectfinal.Detail_Activity
import com.example.projectfinal.databinding.ItemRvNewfriendsBinding
import kotlinx.android.synthetic.main.item_rv_newfriends.view.*

 class FriendsAdapter(
    private val context: Context,
    private val item: ArrayList<FriendData>
        ):RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    class FriendsViewHolder(binding: ItemRvNewfriendsBinding): RecyclerView.ViewHolder(binding.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
            val binding = ItemRvNewfriendsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            return FriendsViewHolder(binding)
        }
        override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
            val friendData = item[position]
            var photo =""
            photo = friendData.photoProfile
            val bitmap = BitmapConverter().stringToBitmap(context, photo)

            holder.itemView.tvName.text = friendData.name
            holder.itemView.tvSchool.text = friendData.school
            holder.itemView.tvGithub.text = friendData.github
            holder.itemView.imgPhoto.setImageBitmap(bitmap)

            holder.itemView.setOnClickListener {
                val moveRead = Intent(holder.itemView.context, Detail_Activity::class.java)
                    .putExtra("KEY_ID", friendData.id)
                    .putExtra("KEY_NAME", friendData.name)
                    .putExtra("KEY_SCHOOL", friendData.school)
                    .putExtra("KEY_GITHUB", friendData.github)
                    .putExtra("KEY_IMG", photo)
                holder.itemView.context.startActivity(moveRead)
            }
        }

    override fun getItemCount(): Int {
        //return item.size
        return Math.min(item.size, 5)
         }
    fun setData(data: List<FriendData>) {
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }
        }