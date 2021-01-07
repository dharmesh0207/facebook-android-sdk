package com.social.fblibrary;

import android.app.Activity;
import android.content.Intent;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.social.fblibrary.listeners.OnLoginListener;
import com.social.fblibrary.listeners.OnLogoutListener;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dharmesh
 */
public class SessionManager {

    public static Class<?> TAG = SessionManager.class;
    static SimpleFacebookConfiguration configuration;

    private WeakReference<Activity> mActivity;
    private final LoginManager mLoginManager;
    private final LoginCallback mLoginCallback = new LoginCallback();
    private final CallbackManager mCallbackManager = CallbackManager.Factory.create();

    public class LoginCallback implements FacebookCallback<LoginResult> {

        public OnLoginListener loginListener;
        boolean doOnLogin = false;
        boolean askPublishPermissions = false;
        List<String> publishPermissions;

        @Override
        public void onSuccess(LoginResult loginResult) {
            if (loginListener != null) {

                if (doOnLogin) {
                    doOnLogin = false;
                    askPublishPermissions = false;
                    publishPermissions = null;
                    loginListener.onLogin(loginResult);
                    return;
                }

                if (askPublishPermissions && publishPermissions != null) {
                    doOnLogin = true;
                    askPublishPermissions = false;
                } else {
                    loginListener.onLogin(loginResult);
                }

            }
        }

        @Override
        public void onCancel() {
            loginListener.onFail("User canceled the permissions dialog");
        }

        @Override
        public void onError(FacebookException e) {
            loginListener.onException(e);
        }
    }

    public SessionManager(SimpleFacebookConfiguration configuration) {
        SessionManager.configuration = configuration;
        mLoginManager = LoginManager.getInstance();
        mLoginManager.registerCallback(mCallbackManager, mLoginCallback);
        mLoginManager.setLoginBehavior(configuration.getLoginBehavior());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Login to Facebook
     *
     * @param onLoginListener
     */
    public void login(OnLoginListener onLoginListener) {
        if (onLoginListener == null) {
            return;
        }

        if (isLogin()) {
            LoginResult loginResult = createLastLoginResult();
            onLoginListener.onLogin(loginResult);
            return;
        }

        if (hasPendingRequest()) {
            onLoginListener.onFail("Already has pending login request");
            return;
        }

        // just do the login
        loginImpl(onLoginListener);
    }

    private void loginImpl(OnLoginListener onLoginListener) {

        // user hasn't the access token with all read acceptedPermissions we need, thus we ask him to login
        mLoginCallback.loginListener = onLoginListener;

    }


    private LoginResult createLastLoginResult() {
        return new LoginResult(getAccessToken(), getAccessToken().getPermissions(), getAccessToken().getDeclinedPermissions());
    }

    /**
     * Logout from Facebook
     */
    public void logout(OnLogoutListener onLogoutListener) {
        if (onLogoutListener == null) {
            return;
        }

        mLoginManager.logOut();
        onLogoutListener.onLogout();
    }

    /**
     * Indicate if you are logged in or not.
     *
     * @return <code>True</code> if you is logged in, otherwise return
     *         <code>False</code>
     */
    public boolean isLogin() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return false;
        }
        return !accessToken.isExpired();
    }

    /**
     * Get access token of open session
     *
     */
    public AccessToken getAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }

    public Set<String> getAcceptedPermissions() {
        AccessToken accessToken = getAccessToken();
        if (accessToken == null) {
            return new HashSet<String>();
        }
        return accessToken.getPermissions();
    }

    void setActivity(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
    }

    public Activity getActivity() {
        return mActivity.get();
    }

    public LoginCallback getLoginCallback() {
        return mLoginCallback;
    }

    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }


    public boolean hasPendingRequest() {
        // try {
        // 	Field f = mLoginManager.getClass().getDeclaredField("pendingLoginRequest");
        // 	f.setAccessible(true);
        //     Object request = f.get(mLoginManager);
        //     if (request != null) {
        // 		return true;
        // 	}
        // } catch (Exception e) {
        // 	// do nothing
        // }
        return false;
    }


    public void clean() {
        mActivity.clear();
    }

}
