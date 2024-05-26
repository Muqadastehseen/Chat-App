package com.example.login_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView:RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagelist: ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference

    //Using sender room and receiver room we create a private room for both sender and receiver so other user canot read thir chat

    var receiverRoom: String?=null
    var senderRoom: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiverid=intent.getStringExtra("id")

        val senderid=FirebaseAuth.getInstance().currentUser?.uid
        mDbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=receiverid+senderid
        receiverRoom=senderid+receiverid

        supportActionBar?.title=name



        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sendButton=findViewById(R.id.sentButton)
        messagelist= ArrayList()
        messageAdapter= MessageAdapter(this,messagelist)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        //Logic for adding data in recycler view

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messagelist.clear()


                    for(postSnapshot in  snapshot.children){

                        val message=postSnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        //ading mess to database

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderid)

            // Send the message to the sender's room

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    // After successfully sending to the sender, also send to the receiver's room

                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

            messageBox.setText("")
        }

    }
}