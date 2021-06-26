package com.example.baruintro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

class UsersRecyclerAdapter(private val listusers: List<User>):
    RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_recycler, parent, false)
        return UserViewHolder(itemView)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: AppCompatTextView
        var textViewEmail: AppCompatTextView
        var textViewPassword: AppCompatTextView

        init {
            textViewName = view.findViewById(R.id.textViewName) as AppCompatTextView
            textViewEmail = view.findViewById(R.id.textViewEmail) as AppCompatTextView
            textViewPassword = view.findViewById(R.id.textViewPassword) as AppCompatTextView
        }

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listusers[position].name
        holder.textViewEmail.text = listusers[position].email
        holder.textViewPassword.text = listusers[position].password

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}
