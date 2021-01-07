package com.social.android.utilities


open class Constants {
    companion object {

        const val LOGIN_TYPE_FACEBOOK = "facebook"

        const val EMAIL = "email"
        const val PUBLIC_PROFILE = "public_profile"
        /*
        this request field permission change if policy change by facebook in new sdk...
        according to requirement you can choose field
         */
        const val REQUEST_FIELD = "id,name,email"
    }
}