package com.example.app_curso

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val signupEmail = findViewById<EditText>(R.id.EmailN)
        val signupPassword = findViewById<EditText>(R.id.PasswordN)
        val register = findViewById<Button>(R.id.Registrar)
        val auth = FirebaseAuth.getInstance()

        register.setOnClickListener {
            val email = signupEmail.text.toString()
            val password = signupPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Creado con exito")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Error, verifique y vuelva a intentar",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

            private fun updateUI(user: FirebaseUser?) {
                if (user != null) {
                    // El usuario se autenticó correctamente, realiza las acciones necesarias (por ejemplo, inicie una nueva actividad)
                    startActivity(Intent(this, login_activity::class.java))
                    finish()
                } else {
                    // La autenticación falló, puedes mostrar un mensaje de error o realizar otras acciones si es necesario.
                    // En este ejemplo, simplemente se muestra un Toast para indicar que la autenticación falló.
                    Toast.makeText(this, "Error al autenticar.", Toast.LENGTH_SHORT).show()
                }
            }





}