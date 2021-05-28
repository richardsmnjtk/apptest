package com.example.loginapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener{
            onBackPressed()
        }

        btn_register.setOnClickListener {
            when {
                TextUtils.isEmpty(et_register_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Please enter email",
                            Toast.LENGTH_SHORT
                    )
                }

                TextUtils.isEmpty(et_register_password.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(
                            this@RegisterActivity,
                            "Please enter password",
                            Toast.LENGTH_SHORT,
                    ).show()
                }
                else -> {

                    val email: String = et_register_email.text.toString().trim{ it <= ' '}
                    val password: String = et_register_password.text.toString().trim{ it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                if (task.isSuccessful){
                                    val firebaseUser: FirebaseUser = task.result!!.user!!

                                    Toast.makeText(
                                            this@RegisterActivity,
                                            "You are registered Successfully",
                                            Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@RegisterActivity,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else (
                                        Toast.makeText(
                                                this@RegisterActivity,
                                                task.exception!!.message.toString(),
                                                Toast.LENGTH_SHORT
                                        ).show()
                                )
                            }
                    )
                }
            }
        }
    }
}