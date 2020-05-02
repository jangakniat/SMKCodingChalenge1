package com.example.smkcodingchalenge1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getProfileData()
    }
    private fun getProfileData(){
        val bundle = intent.extras

        val name = bundle?.getString("name")
        val gender = bundle?.getString("gender")
        val email = bundle?.getString("email")
        val telp = bundle?.getString("telp")
        val address = bundle?.getString("address")
        txtName.text = name
        txtGender.text = gender
        txtEmail.text = email
        txtTelp.text = telp
        txtAddress.text = address
    }
}
