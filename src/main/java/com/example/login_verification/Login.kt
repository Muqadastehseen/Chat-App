package com.example.login_verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_verification.databinding.ActivityLoginActivtyBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginActivtyBinding
    private lateinit var firebaseDataBase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Initlize firebase database
        firebaseDataBase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDataBase.reference.child("users")

        binding.loginButton.setOnClickListener {
            val login2Username=binding.login2Username.text.toString()
            val login2Password=binding.login2Password.text.toString()

            if(login2Username.isNotEmpty() && login2Password.isNotEmpty()){
                LoginUser(login2Username,login2Password)
            }
            else{
                Toast.makeText(this@Login,"All fields are mandatory",Toast.LENGTH_SHORT).show()

            }
        }
        binding.signupRedirect.setOnClickListener{
            startActivity(Intent(this@Login,Signup::class.java))
            finish()

        }

    }

    private fun LoginUser(username: String, password: String) {
        databaseReference.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val userData = userSnapshot.getValue(UserData::class.java)
                            if (userData != null && userData.password == password)
                            {
                                Toast.makeText(this@Login,"Congratultion U are Successfully Login",Toast.LENGTH_SHORT).show()
                                val i = Intent(applicationContext,UserlistActivity::class.java)
                                //finish()
                                startActivity(i)
                                // Toast.makeText(this@Login,"Congratullly Login",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(this@Login, "Login Failed", Toast.LENGTH_SHORT).show()

                            }




                        }
                    }


                }

//

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@Login,"Database Error:${databaseError.message}",Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }
}