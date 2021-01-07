package com.social.fblibrary

import android.app.Activity
import android.content.Intent
import com.facebook.AccessToken
import com.social.fblibrary.listeners.OnLoginListener
import com.social.fblibrary.listeners.OnLogoutListener

/**
 * Simple Facebook SDK which wraps original Facebook SDK
 *
 * @author dharmesh
 */
class SimpleFacebook private constructor() {
    /**
     * Login to Facebook
     *
     * @param onLoginListener
     */
    fun login(onLoginListener: OnLoginListener?) {
        mSessionManager!!.login(onLoginListener)
    }

    /**
     * Logout from Facebook
     */
    fun logout(onLogoutListener: OnLogoutListener?) {
        mSessionManager!!.logout(onLogoutListener)
    }

    /**
     * Are we logged in to facebook
     *
     * @return `True` if we have active and open session to facebook
     */
    val isLogin: Boolean
        get() = mSessionManager!!.isLogin


    /**
     * Get the current access token
     */
    val accessToken: AccessToken
        get() = mSessionManager!!.accessToken

    /**
     * Get string access token
     */
    val token: String
        get() = mSessionManager!!.accessToken.token

    /**
     * Call this inside your activity in [Activity.onActivityResult]
     * method
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mSessionManager!!.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Clean all references like Activity to prevent memory leaks
     */
    fun clean() {
        mSessionManager!!.clean()
    }

    companion object {
        /**
         * <br></br>
         * **Important:** Use this method only after you initialized this library
         * or by: [.initialize] or by [.getInstance]
         *
         * @return The [com.com.social.fblibrary.SimpleFacebook] instance
         */
        var instance: SimpleFacebook? = null
            private set
        private var mConfiguration =
            SimpleFacebookConfiguration.Builder().build()
        private var mSessionManager: SessionManager? = null

        /**
         * Initialize the library and pass an [Activity]. This kind of
         * initialization is good in case you have a one base activity and many
         * fragments. In this case you just initialize this library and then just
         * get an instance of this library by [com.com.social.fblibrary.SimpleFacebook.getInstance]
         * in any other place.
         *
         * @param activity
         * Activity
         */
        fun initialize(activity: Activity) {
            if (instance == null) {
                instance = SimpleFacebook()
                mSessionManager =
                    SessionManager(mConfiguration)
            }
            mSessionManager!!.setActivity(activity)
        }

        /**
         * Get the instance of [com.com.social.fblibrary.SimpleFacebook]. This method, not only returns
         * a singleton instance of [com.com.social.fblibrary.SimpleFacebook] but also updates the
         * current activity with the passed activity. <br></br>
         * If you have more than one `Activity` in your application. And
         * more than one activity do something with facebook. Then, call this method
         * in [Activity.onResume] method
         *
         * <pre>
         * &#064;Override
         * protected void onResume() {
         * super.onResume();
         * mSimpleFacebook = SimpleFacebook.getInstance(this);
         * }
        </pre> *
         *
         * @param activity
         * @return [com.com.social.fblibrary.SimpleFacebook] instance
         */
        fun getInstance(activity: Activity): SimpleFacebook? {
            if (instance == null) {
                instance = SimpleFacebook()
                mSessionManager =
                    SessionManager(mConfiguration)
            }
            mSessionManager!!.setActivity(activity)
            return instance
        }

        /**
         * Get configuration
         *
         * @return
         */
        /**
         * Set facebook configuration. **Make sure** to set a configuration
         * before first actual use of this library like (login, getProfile, etc..).
         *
         * @param configuration
         * The configuration of this library
         */
        var configuration: SimpleFacebookConfiguration
            get() = mConfiguration
            set(configuration) {
                mConfiguration = configuration
                SessionManager.configuration = configuration
            }
    }
}