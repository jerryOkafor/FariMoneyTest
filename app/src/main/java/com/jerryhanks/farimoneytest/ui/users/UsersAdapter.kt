package com.jerryhanks.farimoneytest.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jerryhanks.farimoneytest.data.models.UserMinimal
import com.jerryhanks.farimoneytest.databinding.ItemUserBinding

/**
 * Author: Jerry
 * Project: FairmoneyTest
 */
class UsersAdapter(
    private val context: Context,
    private val clickCallback: (UserMinimal, View, View, View) -> Unit
) :
    ListAdapter<UserMinimal, UsersAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding.root, clickCallback, context)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bindUser(user)

    }

    class UserViewHolder(
        itemView: View,
        private val clickCallback: (UserMinimal, View, View, View) -> Unit,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(itemView) {
        fun bindUser(user: UserMinimal) {
            val binding = ItemUserBinding.bind(itemView)
            binding.tvEmail.text = user.email
            binding.tvName.text = "${user.title} ${user.firstName} ${user.lastName}"

            binding.root.setOnClickListener {
                clickCallback(user, binding.ivProfilePic, binding.tvName, binding.tvEmail)
            }

            Glide.with(context).load(user.picture).into(binding.ivProfilePic)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserMinimal>() {
            override fun areItemsTheSame(oldItem: UserMinimal, newItem: UserMinimal): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: UserMinimal, newItem: UserMinimal): Boolean {
                return newItem.firstName == oldItem.firstName && newItem.lastName == oldItem.lastName
                        && newItem.email == oldItem.email
            }

        }
    }


}