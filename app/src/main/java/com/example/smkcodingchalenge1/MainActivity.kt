package com.example.smkcodingchalenge1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.net.toFile


import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private var inputImage : Uri? = null
    private var inputName : String = ""
    private var inputAge : String = ""
    private var inputGender : String = ""
    private var inputEmail : String = ""
    private var inputTelp : String = ""
    private var inputAddress : String = ""

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDataSpinnerGender()
        btnSave.setOnClickListener { inputValidation() }

        iv_image.setOnClickListener{checkStoragePermissions()}
    }

    private fun checkStoragePermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else{
                //permission already granted
                pickImageFromGallery();
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            iv_image.setImageURI(data?.data)
            inputImage=data?.data
        }
    }

    private fun setDataSpinnerGender(){
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.gender, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }


    private fun inputValidation(){
        inputName = createName.text.toString()
        inputAge = createAge.text.toString()
        inputGender = spinnerGender.selectedItem.toString()
        inputEmail = createEmail.text.toString()
        inputTelp = createTelephone.text.toString()
        inputAddress = createAddress.text.toString()
        when{
            inputName.isEmpty() -> createName.error = "Nama tidak boleh kosong"
            inputAge.isEmpty() -> createAge.error = "usia tidak boleh kosong"
            inputGender.equals("Pilih Jenis Kelamin", ignoreCase = true) ->
                showToast("Jenis Kelamin harus dipilih")
            inputEmail.isEmpty() -> createEmail.error = "Email tidak boleh kosong"
            inputTelp.isEmpty() -> createTelephone.error = "Telp tidak boleh kosong"
            inputAddress.isEmpty() -> createAddress.error = "Alamat tidak boleh kosong"
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
        bundle.putString("image",inputImage.toString())
        bundle.putString("name", inputName)
        bundle.putString("gender", inputGender)
        bundle.putString("age", inputAge)
        bundle.putString("email", inputEmail)
        bundle.putString("telp", inputTelp)
        bundle.putString("address", inputAddress)
        intent.putExtras(bundle)
        startActivity(intent)

    }

}
