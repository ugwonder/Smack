package com.mgbachi_ugo.smack

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvater = "profileDefault"
    var avaterColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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

    }
}
