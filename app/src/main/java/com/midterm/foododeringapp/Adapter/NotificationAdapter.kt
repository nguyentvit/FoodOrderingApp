package com.midterm.foododeringapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midterm.foododeringapp.databinding.NotificationItemBinding

class NotificationAdapter(private var notification: ArrayList<String>, private var notificationImage: ArrayList<Int>) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    class NotificationViewHolder(private var binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root) {
        val imgNotifi = binding.imgNotifi
        val tvNotifi = binding.tvNotifi
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return  NotificationViewHolder(NotificationItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return notification.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.imgNotifi.setImageResource(notificationImage[position])
        holder.tvNotifi.text = notification[position]
    }
}