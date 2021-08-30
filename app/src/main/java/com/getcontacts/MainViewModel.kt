package com.getcontacts

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.getcontacts.model.PhoneNumber

class MainViewModel : ViewModel() {

  private val phoneNumber = ArrayList<PhoneNumber>()
  private val TAG = "MainViewModel"
  private val mutableLiveData = MutableLiveData<ArrayList<PhoneNumber>>()

  fun fetchContactList(cr: ContentResolver) {

    val cur: Cursor? = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

    if ((cur?.count ?: 0) > 0) {
      while (cur != null && cur.moveToNext()) {
        val id: String = cur.getString(
          cur.getColumnIndex(ContactsContract.Contacts._ID)
        )

        val name: String = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

        if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
          val pCur: Cursor? = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
          )
          while (pCur!!.moveToNext()) {
            val phoneNo: String =
              pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            Log.i(TAG, "Name: $name")
            Log.i(TAG, "Phone Number: $phoneNo")
            phoneNumber.add(PhoneNumber(name, phoneNo))
          }
          pCur.close()
        }
      }
    }
    cur?.close()
    mutableLiveData.value = phoneNumber
  }

  public fun getContactList() : LiveData<ArrayList<PhoneNumber>>{
    return mutableLiveData
  }

}