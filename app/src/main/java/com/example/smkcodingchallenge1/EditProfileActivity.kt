package com.example.smkcodingchallenge1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfileActivity : AppCompatActivity() {

    private var image: Uri? =null

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setDataSpinnerGender()
        getProfileData()

        btnSaveEdit.setOnClickListener { saveData() }
        editImage.setOnClickListener { checkStoragePermissions() }
    }

    private fun checkStoragePermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, EditProfileActivity.PERMISSION_CODE)
            } else{
                //permission already granted
                pickImageFromGallery()
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,EditProfileActivity.IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            EditProfileActivity.PERMISSION_CODE -> {
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
        if (resultCode == Activity.RESULT_OK && requestCode == EditProfileActivity.IMAGE_PICK_CODE){
            editImage.setImageURI(data?.data)
            image=data?.data
        }
    }

    private fun setDataSpinnerGender(){
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.gender, android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        editSpinnerGender.adapter = adapter
    }

    private fun getProfileData(){
        val intentData = intent.extras

        val image =Uri.parse( intentData?.getString("image"))
        val name = intentData?.getString("name")
        val gender = intentData?.getString("gender")
        val age = intentData?.getString("age")
        val email = intentData?.getString("email")
        val telp = intentData?.getString("telp")
        val address = intentData?.getString("address")
        editImage.setImageURI(image)
        editName.setText(name)
        editAge.setText(age)
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
        val inputImage = image.toString()
        val inputName = editName.text.toString()
        val inputGender = editSpinnerGender.selectedItem.toString()
        val inputAge = editAge.text.toString()
        val inputEmail = editEmail.text.toString()
        val inputTelp = editTelephone.text.toString()
        val inputAddress = editAddress.text.toString()
        when{
            inputName.isEmpty() -> editName.error = "Nama tidak boleh kosong"
            inputAge.isEmpty() -> editAge.error = "Umur tidak boleh kosong"
            inputGender.equals("Pilih Jenis Kelamin", ignoreCase = true) ->
                showToast("Jenis Kelamin harus dipilih")
            inputEmail.isEmpty() -> editEmail.error = "Email tidak boleh kosong"
            inputTelp.isEmpty() -> editTelephone.error = "Telp tidak boleh kosong"
            inputAddress.isEmpty() -> editAddress.error = "Alamat tidak boleh kosong"
            else -> {
                showToast("Sukses Mengedit Profile")
                val result = Intent()
                result.putExtra("image", inputImage)
                result.putExtra("name", inputName)
                result.putExtra("age", inputAge)
                result.putExtra("gender", inputGender)
                result.putExtra("email", inputEmail)
                result.putExtra("telp", inputTelp)
                result.putExtra("address", inputAddress)
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }


    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
