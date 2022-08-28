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

class FriendsAdapterViewAll (
    private val context:  Context,
    private val item: ArrayList<FriendData>

) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {

    class FriendViewHolder(binding: ItemRvNewfriendsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FriendsAdapter.FriendsViewHolder {
        val binding = ItemRvNewfriendsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FriendsAdapter.FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsAdapter.FriendsViewHolder, position: Int) {
        val friendModel = item[position]
        var photo = ""
        photo = friendModel.photoProfile
        val bitmap = BitmapConverter().stringToBitmap(context, photo)

        holder.itemView.tvName.text = friendModel.name
        holder.itemView.tvSchool.text = friendModel.school
        holder.itemView.tvGithub.text = friendModel.github
        holder.itemView.imgPhoto.setImageBitmap(bitmap)

        holder.itemView.setOnClickListener {
            val moveRead = Intent(holder.itemView.context, Detail_Activity::class.java)
                .putExtra("KEY_ID", friendModel.id)
                .putExtra("KEY_NAME", friendModel.name)
                .putExtra("KEY_SCHOOL", friendModel.school)
                .putExtra("KEY_GITHUB", friendModel.github)
                .putExtra("KEY_IMG", photo)
            holder.itemView.context.startActivity(moveRead)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun setData(data: List<FriendData>) {
        item.clear()
        item.addAll(data)
        notifyDataSetChanged()
    }

}