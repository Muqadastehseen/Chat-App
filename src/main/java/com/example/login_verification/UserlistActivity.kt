package com.example.login_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login_verification.R.menu
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserlistActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbref:DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerView=findViewById(R.id.userlist)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf<User>()
        getUserData()
        //supportActionBar?.hide()

    }

    private fun getUserData() {
        dbref=FirebaseDatabase.getInstance().getReference("users")

        dbref.addValueEventListener(object:ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    for (userSnapshot in snapshot.children) {

                        val user = userSnapshot.getValue(User::class.java)
                        //if (mAuth.currentUser?.uid != user?.id) {


                        userArrayList.add(user!!)


                        //}
                    }

                    userRecyclerView.adapter=MyAdapter(userArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){

            mAuth.signOut()
            val intent= Intent(applicationContext,Login::class.java)
            finish()
            return true
            startActivity(intent)
        }
        return true
    }
}



