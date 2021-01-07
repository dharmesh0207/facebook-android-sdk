package com.social.fblibrary.listeners

/**
 * On login/logout actions listener
 *
 * @author dharmesh
 */

interface OnErrorListener {
    fun onException(throwable: Throwable?)
    fun onFail(reason: String?)
}