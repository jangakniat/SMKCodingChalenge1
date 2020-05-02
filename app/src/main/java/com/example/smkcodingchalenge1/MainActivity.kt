package com.example.smkcodingchalenge1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var inputName : String = ""
    private var inputEmail : String = ""
    private var inputTelp : String = ""
    private var inputAddress : String = ""
    private var inputGender : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDataSpinnerGender()
        btnSave.setOnClickListener { inputValidation() }
    }
    private fun setDataSpinnerGender(){
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.gender, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }


    private fun inputValidation(){
        inputName = editName.text.toString()
        inputEmail = editEmail.text.toString()
        inputTelp = editTelephone.text.toString()
        inputAddress = editAddress.text.toString()
        inputGender = spinnerGender.selectedItem.toString()
        when{
            inputName.isEmpty() -> editName.error = "Nama tidak boleh kosong"
            inputGender.equals("Pilih Jenis Kelamin", ignoreCase = true) ->
                showToast("Jenis Kelamin harus dipilih")
            inputEmail.isEmpty() -> editEmail.error = "Email tidak boleh kosong"
            inputTelp.isEmpty() -> editTelephone.error = "Telp tidak boleh kosong"
            inputAddress.isEmpty() -> editAddress.error = "Alamat tidak boleh kosong"
            else -> {
                showToast("Navigasi ke halaman profil")
                goToProfilActivity()
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToProfilActivity(){
        val intent = Intent(this, ProfileActivity::class.java)
        val bundle = Bundle()
        bundle.putString("name", inputName)
        bundle.putString("gender", inputGender)
        bundle.putString("email", inputEmail)
        bundle.putString("telp", inputTelp)
        bundle.putString("address", inputAddress)
        intent.putExtras(bundle)
        startActivity(intent)

    }

}
