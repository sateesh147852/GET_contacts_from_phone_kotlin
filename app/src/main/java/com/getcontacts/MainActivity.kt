package com.getcontacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.getcontacts.adapter.ContactAdapter
import com.getcontacts.databinding.ActivityMainBinding
import com.getcontacts.utility.Constants.RC_CONTACTS
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var viewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btGetContacts.setOnClickListener {
      checkPermissions()
    }

    initializeViewModel()
  }

  private fun initializeViewModel() {
    viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    viewModel.getContactList().observe(this, {
      val linearLayoutManager = LinearLayoutManager(this)
      val contactAdapter = ContactAdapter(it)
      binding.rvPhoneNumber.layoutManager = linearLayoutManager
      binding.rvPhoneNumber.adapter = contactAdapter
    })
  }

  private fun checkPermissions() {
    if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS))
      getContacts()
    else
      EasyPermissions.requestPermissions(
        this,
        "This permission is required to access your contacts please enable it",
        RC_CONTACTS,
        Manifest.permission.READ_CONTACTS
      )

  }

  private fun getContacts() {
    viewModel.fetchContactList(contentResolver)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
      RC_CONTACTS -> {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          getContacts()
        }
      }
    }
  }
}