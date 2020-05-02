package com.example.smkcodingchalenge1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    companion object {
        val REQUEST_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        getProfileData()
        btnEditProfile.setOnClickListener{goToEditProfile()}
        btnCall.setOnClickListener { dialPhoneNumber(txtTelephone.text.toString()) }
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
        txtTelephone.text = telp
        txtAddress.text = address
    }

    private fun goToEditProfile(){
        val intent = Intent(this, EditProfileActivity::class.java)
        val inputName = txtName.text.toString()
        val inputEmail = txtEmail.text.toString()
        val inputTelp = txtTelephone.text.toString()
        val inputAddress = txtAddress.text.toString()
        val inputGender = txtGender.text.toString()
        intent.putExtra("name", inputName)
        intent.putExtra("email", inputEmail)
        intent.putExtra("telp", inputTelp)
        intent.putExtra("address", inputAddress)
        intent.putExtra("gender", inputGender)
        startActivityForResult(intent, REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra("name")
                val gender = data?.getStringExtra("gender")
                val email = data?.getStringExtra("email")
                val telp = data?.getStringExtra("telp")
                val address = data?.getStringExtra("address")
                txtName.text = name
                txtGender.text = gender
                txtEmail.text = email
                txtTelephone.text = telp
                txtAddress.text = address
            }else{
                Toast.makeText(this, "Edit failed",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }


    }
}
