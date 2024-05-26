package com.example.login_verification

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MyAdapter(private val userlist:ArrayList<User>): RecyclerView.Adapter <MyAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)


    }

    //supportActionBar?.hide()


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=userlist[position]
        holder.name.text=currentitem.name


        holder.itemView.setOnClickListener {
            it.context?.let { context ->
                currentitem?.let { item ->
                    val intent = Intent(context, ChatActivity::class.java).apply {
                        putExtra("name", currentitem.name)
                        putExtra("id", currentitem.id)
                    }
                    context.startActivity(intent)
                }
            }
        }




    }
    override fun getItemCount(): Int {
        return userlist.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){


        val name:TextView=itemView.findViewById(R.id.name1)


    }



}