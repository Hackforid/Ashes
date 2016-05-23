package com.smilehacker.ashes.account

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * Created by kleist on 16/5/23.
 */
class AccountProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.smilehacker.ashes.provider"
        const val CONTENT_URI_BASE = "content://$AUTHORITY"
        const val TABLE_NAME = "data"
        val CONTENT_URI = Uri.parse("$CONTENT_URI_BASE/$TABLE_NAME")
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri?): String? {
        return String()
    }
}