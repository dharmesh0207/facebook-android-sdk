package com.social.android.view.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.social.android.R
import com.social.android.utilities.Constants
import com.social.fblibrary.SimpleFacebook
import com.social.fblibrary.listeners.OnLoginListener
import com.social.fblibrary.listeners.OnLogoutListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mSimpleFacebook: SimpleFacebook? = null
    var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setup()
        onClickListener()
    }



    //For initialize...
    private fun init() {
        // Initialize Facebook Login button
        mSimpleFacebook = SimpleFacebook.getInstance(this)
    }

    //For call and set values.
    private fun setup() {
        login_button.setPermissions(Arrays.asList(Constants.EMAIL,Constants.PUBLIC_PROFILE));

        setLogin()
        setLogout()

    }

    //For click...
    private fun onClickListener() {

    }

    /**
     * Login example.
     */
    private fun setLogin() {
        // Login listener
        val onLoginListener: OnLoginListener = object : OnLoginListener {
            override fun onLogin(
                loginResult: LoginResult?
            ) {
                val request = GraphRequest.newMeRequest(
                    loginResult!!.accessToken
                ) { `object`, response ->
                    Log.e(TAG, response.toString())


                }
                val parameters = Bundle()
                //this fields for now limitatoin so please before add this library refer facebook
                      // developer document..........
                parameters.putString("fields", Constants.REQUEST_FIELD)
                request.parameters = parameters
                request.executeAsync()
            }

            override fun onCancel() {
            }

            override fun onException(throwable: Throwable?) {
                Toast.makeText(this@MainActivity,throwable!!.localizedMessage,Toast.LENGTH_SHORT).show()
            }

            override fun onFail(reason: String?) {
                Toast.makeText(this@MainActivity,reason,Toast.LENGTH_SHORT).show()
            }

        }
        login_button.setOnClickListener{
            mSimpleFacebook!!.login(
                onLoginListener
            )
        }
    }

    /**
     * Logout example if custom button apply
     */
    private fun setLogout() {
        val onLogoutListener: OnLogoutListener = OnLogoutListener {
            // change the state of the button or do whatever you want
        }
       /* logout_button.setOnClickListener(View.OnClickListener {
            mSimpleFacebook!!.logout(
                onLogoutListener
            )
        })*/
    }


    override fun onResume() {
        super.onResume()
        mSimpleFacebook = SimpleFacebook.getInstance(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSimpleFacebook!!.onActivityResult(requestCode, resultCode, data)
    }
}