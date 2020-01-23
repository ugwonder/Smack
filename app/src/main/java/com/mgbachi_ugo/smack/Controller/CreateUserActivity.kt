package com.mgbachi_ugo.smack.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.mgbachi_ugo.smack.R
import com.mgbachi_ugo.smack.Services.AuthService
import com.mgbachi_ugo.smack.Services.UserDataService
import com.mgbachi_ugo.smack.Utilities.BROADCAST_USER_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvater = "profileDefault"
    var avaterColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvater(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avater = random.nextInt(28)
        if (color == 0) {
            userAvater = "light$avater"
        } else{
            userAvater = "dark$avater"
        }
        val resourceId = resources.getIdentifier(userAvater,"drawable", packageName)
        createAvaterImageView.setImageResource(resourceId)


    }

    fun generateColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvaterImageView.setBackgroundColor(Color.rgb(r,g,b))
        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255
        avaterColor = "[$savedB, $savedG, $savedR]"



    }

    fun createUserBtnClicked(view: View) {
        enableSpinner(true)
        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createpasswordText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess){
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, userName, email, userAvater, avaterColor) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChange = Intent(BROADCAST_USER_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }

        } else {
            Toast.makeText(this, "Make sure Username, Email and Password are correctly filled", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }

    }
    fun errorToast() {
        Toast.makeText(this, "Something went wrong please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        createrUserBtn.isEnabled = !enable
        createAvaterImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
