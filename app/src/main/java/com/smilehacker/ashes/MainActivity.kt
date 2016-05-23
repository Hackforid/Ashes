package com.smilehacker.ashes

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.onClick
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            button("Add account") {
                onClick {
                    val accountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
                    val account = Account("xiaomi", getString(R.string.account_type))
                    accountManager.addAccountExplicitly(account, "aaa", null)
                }
            }
        }
    }

    private fun getAccount() {
        val accountManager = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
        val accounts = accountManager.getAccountsByType("com.smilehacker")

    }
}
