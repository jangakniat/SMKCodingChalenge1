package com.example.smkcodingchalenge1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setDataSpinnerGender()
        getProfileData()

        btnSaveEdit.setOnClickListener { saveData() }
    }

    private fun setDataSpinnerGender(){
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.gender, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editSpinnerGender.adapter = adapter
    }

    private fun getProfileData(){
        val intentData = intent.extras

        val name = intentData?.getString("name")
        val gender = intentData?.getString("gender")
        val email = intentData?.getString("email")
        val telp = intentData?.getString("telp")
        val address = intentData?.getString("address")
        editName.setText(name)
        editEmail.setText(email)
        setEditSpinner(gender.toString())
        editTelephone.setText(telp)
        editAddress.setText(address)
    }

    private fun setEditSpinner(string: String){
        var x = 0
        while (x < editSpinnerGender.adapter.count) {
           if(editSpinnerGender.getItemAtPosition(x).toString()== string){
               editSpinnerGender.setSelection(x)
           }
            x++

        }

    }

    private fun saveData(){
        val inputName = editName.text.toString()
        val inputEmail = editEmail.text.toString()
        val inputTelp = editTelephone.text.toString()
        val inputAddress = editAddress.text.toString()
        val inputGender = editSpinnerGender.selectedItem.toString()
        when{
            inputName.isEmpty() -> editName.error = "Nama tidak boleh kosong"
            inputGender.equals("Pilih Jenis Kelamin", ignoreCase = true) ->
                showToast("Jenis Kelamin harus dipilih")
            inputEmail.isEmpty() -> editEmail.error = "Email tidak boleh kosong"
            inputTelp.isEmpty() -> editTelephone.error = "Telp tidak boleh kosong"
            inputAddress.isEmpty() -> editAddress.error = "Alamat tidak boleh kosong"
            else -> {
                showToast("Sukses Mengedit Profile")
                val result = Intent()
                result.putExtra("name", inputName)
                result.putExtra("email", inputEmail)
                result.putExtra("telp", inputTelp)
                result.putExtra("address", inputAddress)
                result.putExtra("gender", inputGender)
                setResult(Activity.RESULT_OK, result)
            }
        }

        finish()
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
