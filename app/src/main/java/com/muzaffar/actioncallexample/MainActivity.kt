package com.muzaffar.actioncallexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import com.muzaffar.actioncallexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val CALL_PHONE_PERMISSION_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val callButton: Button = findViewById(R.id.call_button) // Assume you have a button in your layout

        callButton.setOnClickListener {
            makePhoneCall("1234567890") // Replace with the phone number you want to call
        }
    }

    private fun makePhoneCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(callIntent)
        } else {
            // Request the CALL_PHONE permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST
            )
        }
    }

    // Handle the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall("1234567890") // Retry the call after permission is granted
            } else {
                Toast.makeText(this, "Permission DENIED to make phone calls", Toast.LENGTH_SHORT).show()
            }
        }
    }
}