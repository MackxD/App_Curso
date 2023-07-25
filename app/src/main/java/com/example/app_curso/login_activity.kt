package com.example.app_curso

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class login_activity : AppCompatActivity() {
    lateinit var ingresar: Button
    lateinit var usuario: EditText
    lateinit var password: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ingresar = findViewById(R.id.ingresar)
        usuario = findViewById(R.id.usuario)
        password = findViewById(R.id.password)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        ingresar.setOnClickListener() {

            if(usuario.text.toString() == "" || password.text.toString() == ""){
                Toast.makeText(this, "Credenciales invalidas", Toast.LENGTH_LONG).show()
            }
            else{
                login()
            }

        }
    }

    fun login() {
        val email = usuario.text.toString()
        val pass = password.text.toString()

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->

                if(!isInternetAvailable()){
                    Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show()
                }
                else{
                    if (task.isSuccessful) {
                        // Inicio de sesión exitoso
                        val user = auth.currentUser
                        val intent = Intent(this, inicio_activity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_LONG).show()
                    }
                    else {
                        Toast.makeText(this, "Contraseña y/o usuario incorrectos", Toast.LENGTH_LONG).show()
                    }
                }
            }

    }
   private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun onTextViewClicked(view: View) {

        if (view.id == R.id.signup_text) {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

            Toast.makeText(this,"Presionado",Toast.LENGTH_LONG).show()
        }
    }
}
