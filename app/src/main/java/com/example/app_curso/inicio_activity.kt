package com.example.app_curso

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.IOException


class inicio_activity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)



        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        toolbar = findViewById(R.id.toolbar)

        //Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        //Obtener referencia de base de datos
        databaseReference = FirebaseDatabase.getInstance().reference

        getSeason()

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
                    true
                }

                R.id.menu_item2 -> {
                    // Acción para el menú item 2
                    val intent = Intent(this, reglamento::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.menu_item3 -> {
                    val url = "https://ieca.conectatalentomx.com/app/home/_" // URL que deseas abrir
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                }
                R.id.menu_item4 -> {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Obtener por id los datos
    fun getSeason() {

        databaseReference.child("seasons").limitToFirst(8)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (seasonSnapshot in dataSnapshot.children) {
                        val season = seasonSnapshot.getValue(Season::class.java)
                        val name = season?.name
                        val descripcion = season?.descripcion
                        val estatus = season?.estatus

                        when (seasonSnapshot.key) {
                            "0" -> {
                                findViewById<TextView>(R.id.Sesion1).text = name
                                findViewById<TextView>(R.id.Tema1).text = descripcion
                            }

                            "1" -> {
                                findViewById<TextView>(R.id.Sesion2).text = name
                                findViewById<TextView>(R.id.Tema2).text = descripcion
                            }

                            "2" -> {
                                findViewById<TextView>(R.id.Sesion3).text = name
                                findViewById<TextView>(R.id.Tema3).text = descripcion
                            }

                            "3" -> {
                                findViewById<TextView>(R.id.Sesion4).text = name
                                findViewById<TextView>(R.id.Tema4).text = descripcion
                            }

                            "4" -> {
                                findViewById<TextView>(R.id.Sesion5).text = name
                                findViewById<TextView>(R.id.Tema5).text = descripcion
                            }

                            "5" -> {
                                findViewById<TextView>(R.id.Sesion6).text = name
                                findViewById<TextView>(R.id.Tema6).text = descripcion
                            }

                            "6" -> {
                                findViewById<TextView>(R.id.Sesion7).text = name
                                findViewById<TextView>(R.id.Tema7).text = descripcion
                            }

                            "7" -> {
                                findViewById<TextView>(R.id.Sesion8).text = name
                                findViewById<TextView>(R.id.Tema8).text = descripcion
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejar error de lectura de Firebase
                }
            })
    }

    fun onTextViewClicked(view: View) {
        if (view.id == R.id.Sesion1) {
            openPdfFile("Sesion1.pdf")

        }
        if (view.id == R.id.Sesion2) {
            openPdfFile("Sesion2.pdf")

        }
        if (view.id == R.id.Sesion3) {
            openPdfFile("Sesion3.pdf")

        }
        if (view.id == R.id.Sesion4) {
            openPdfFile("Sesion4.pdf")

        }
        if (view.id == R.id.Sesion5) {
            openPdfFile("Sesion5.pdf")

        }
        if (view.id == R.id.Sesion6) {
            openPdfFile("Sesion6.pdf")

        }
        if (view.id == R.id.Sesion7) {
            openPdfFile("Sesion7.pdf")

        }
        if (view.id == R.id.Sesion8) {
            openPdfFile("Sesion8.pdf")

        }
    }

    fun openPdfFile(pdfFileName: String) {
        try {
            val inputStream = assets.open(pdfFileName)
            val file = File(filesDir, pdfFileName)

            // Copia el archivo PDF desde los assets a la memoria interna
            inputStream.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            val uri = FileProvider.getUriForFile(this, packageName + ".fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Manejar el caso en el que no se encuentre una aplicación para abrir el PDF
            // Puedes mostrar un mensaje o sugerir al usuario que instale una aplicación de visualización de PDF
        } catch (e: IOException) {
            // Manejar otros errores de lectura de archivos
        }
    }




}

