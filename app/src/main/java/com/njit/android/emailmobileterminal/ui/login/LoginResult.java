package com.njit.android.emailmobileterminal.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private boolean success;
    @Nullable
    private String error;

    LoginResult(@Nullable boolean success,@Nullable String error) {
        this.success = success;
        this.error = error;
    }

    @Nullable
    boolean getSuccess() {
        return success;
    }

    @Nullable
    String getError() {
        return error;
    }
}
