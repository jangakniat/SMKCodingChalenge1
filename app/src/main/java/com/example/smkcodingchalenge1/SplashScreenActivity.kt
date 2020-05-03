package com.example.smkcodingchalenge1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

            //hiding title bar of this activity

            setContentView(R.layout.activity_splash_screen)

            //4second splash time
            Handler().postDelayed({
                //start main activity
                startActivity(Intent(this, MainActivity::class.java))
                //finish this activity
                finish()
            },4000)


    }
}
