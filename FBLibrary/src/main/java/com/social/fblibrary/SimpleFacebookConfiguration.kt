package com.social.fblibrary

import com.facebook.login.LoginBehavior

/**
 * @author dharmesh
 */
class SimpleFacebookConfiguration private constructor(builder: Builder) {
    /**
     * Get facebook application id
     */
    val appId: String?

    /**
     * Get application namespace
     */
    val namespace: String?

    /**
     * Get session login behavior
     *
     * @return
     */
    var loginBehavior: LoginBehavior? = null



    class Builder {
        var mAppId: String? = null
        var mNamespace: String? = null
        var mLoginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK

        /**
         * Set facebook App Id. <br></br>
         * The application id is located in the dashboard of the app in admin
         * panel of facebook
         *
         * @param appId
         */
        fun setAppId(appId: String?): Builder {
            mAppId = appId
            return this
        }

        /**
         * Set application namespace
         *
         * @param namespace
         * @return
         */
        fun setNamespace(namespace: String?): Builder {
            mNamespace = namespace
            return this
        }




        /**
         * @param loginBehavior
         * The loginBehavior to set.
         * @see LoginBehavior
         */
        fun setLoginBehavior(loginBehavior: LoginBehavior): Builder {
            mLoginBehavior = loginBehavior
            return this
        }





        /**
         * Build the configuration for storage tool.
         *
         * @return
         */
        fun build(): SimpleFacebookConfiguration {
            return SimpleFacebookConfiguration(this)
        }
    }


    init {
        appId = builder.mAppId
        namespace = builder.mNamespace
        loginBehavior = builder.mLoginBehavior
    }
}