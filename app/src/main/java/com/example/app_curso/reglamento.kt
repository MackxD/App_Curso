package com.example.app_curso

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class reglamento : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reglamento)

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Agregar el botón de navegación tipo "sandwich" (hamburguesa)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu) // Reemplaza "ic_hamburguesa" con el nombre de tu ícono personalizado
            setDisplayHomeAsUpEnabled(true)
        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Lógica para manejar los clics en los elementos del menú
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    // Acción para el menú item 1
                    val intent = Intent(this, inicio_activity::class.java)
                    startActivity(intent)

                    true
                }

                R.id.menu_item2 -> {
                    // Acción para el menú item 2
                    val intent = Intent(this, reglamento::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_item3 -> {
                    val url = "https://ieca.conectatalentomx.com/app/home/_" // URL que deseas abrir
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                }

                R.id.menu_item4 -> {
                    // Acción para el menú item 3
                    val intent = Intent(this, Conctacto::class.java)
                    startActivity(intent)

                    true
                }
                R.id.menu_item5 -> {
                    // Acción para el menú item 3
                    val intent = Intent(this, login_activity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }


                else -> false
            }
        }

        val headerView = navigationView.getHeaderView(0)

        val currentUser = FirebaseAuth.getInstance().currentUser
        // Verificar si el usuario está autenticado
        if (currentUser != null) {
            // Obtener el email del usuario actual
            val userEmail = currentUser.email

            // Actualizar la interfaz de usuario con el email
            val emailTextView: TextView = headerView.findViewById(R.id.navemail)
            emailTextView.text = userEmail
        } else {
            // El usuario no está autenticado, manejar el caso si es necesario
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
