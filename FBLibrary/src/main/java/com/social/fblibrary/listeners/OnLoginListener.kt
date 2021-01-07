package com.social.fblibrary.listeners

import com.facebook.login.LoginResult


/**
 * On login/logout actions listener
 *
 * @author dharmesh
 */
interface OnLoginListener : OnErrorListener {
    fun onLogin(
        loginResult: LoginResult?
    )

    fun onCancel()
}