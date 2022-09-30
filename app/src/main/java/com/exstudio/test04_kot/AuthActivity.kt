package com.exstudio.test04_kot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlin.system.measureTimeMillis

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //Splash
        //Thread.sleep(measureTimeMillis {  }:2000)//HACK:
        //setTheme(R.Style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //Analytics Event
        val analytics :FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)
        //setup
        setup()

    }

    private fun setup() {

      //  title = "Autenticaction"

        signButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passEdit.text.isNotEmpty()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.text.toString(), passEdit.text.toString()).addOnCompleteListener{

                    if (it.isSuccessful) {

                        showHome(it.result?.user?.email?: "", ProviderType.BASIC)

                    } else {
                        showAlert()

                    }
                }
            }

        }

        loginButton.setOnClickListener{
            if (emailEditText.text.isNotEmpty() && passEdit.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString(), passEdit.text.toString()).addOnCompleteListener{

                    if (it.isSuccessful) {

                        showHome(it.result?.user?.email?: "", ProviderType.BASIC)

                    } else {
                        showAlert()

                    }
                }
            }

        }

        terminosButton.setOnClickListener{
            showTerms()

        }

    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pelele")
        builder.setMessage("Pelele no se registro tu usuario")
        builder.setPositiveButton("Proceda REY", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showTerms() {

            startActivity(Intent(this, terminosAct::class.java))

    }

}