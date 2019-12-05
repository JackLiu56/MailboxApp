package com.njit.android.emailmobileterminal.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.njit.android.emailmobileterminal.FetchEmail;
import com.njit.android.emailmobileterminal.MyApplication;
import com.njit.android.emailmobileterminal.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final static String HOST_STRING = "imap.gmail.com";
    private static String DEFAULT_USERNAME;
    private static String DEFAULT_PASSWORD;

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    public void login(final FetchEmail fetchEmail,final  String username, final String password) {
        // can be launched in a separate asynchronous job

        new Thread(new Runnable() {
            @Override
            public void run() {

                String defaultUserName = null;
                String defaultPassword = null;
                if(username == null || username.length()==0) {
                    defaultUserName = DEFAULT_USERNAME;
                } else{
                    defaultUserName = username;
                }
                if(password == null || password.length()==0) {
                    defaultPassword = DEFAULT_PASSWORD;
                } else{
                    defaultPassword = password;
                }
                try{
                    Log.e("defaultUserName:",defaultUserName);
                    Log.e("defaultPassword:",defaultPassword);
                    fetchEmail.login(HOST_STRING,defaultUserName,defaultPassword);

                    if(fetchEmail.isLoggedIn()){
                        loginResult.postValue(new LoginResult(true,""));
                    } else{
                        loginResult.postValue(new LoginResult(false,"Log in failed"));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Log.e("e:"," "+e.toString());
                    loginResult.postValue(new LoginResult(false,e.toString()));
                }

            }
        }).start();
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
