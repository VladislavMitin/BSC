package ru.vladislavmitin.bsc.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract

class ContactService(private val contentResolver: ContentResolver): IContactService {
    override suspend fun getPhoto(phone: String): String? {
        val uri =
            Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone))
        val projection = arrayOf(ContactsContract.PhoneLookup.PHOTO_URI)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        var photoUri: String? = null

        cursor?.let {
            while (it.moveToNext()) {
                photoUri = it.getString(it.getColumnIndex(ContactsContract.PhoneLookup.PHOTO_URI))
            }
        }

        cursor?.close()

        return photoUri
    }
}