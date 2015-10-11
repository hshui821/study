package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;

/**
 * Created by shhwang on 15. 10. 11.
 */
public class AndroidAuthenticator implements Authenticator {

    private final AccountManager mAccountManager;
    private final Account mAccount;
    private final String mAuthTokenType;
    private final boolean mNotifyAuthFailure;

    public AndroidAuthenticator(Context context, Account account, String authTokenType) {
        this(context, account, authTokenType, false);
    }

    public AndroidAuthenticator(Context context, Account account, String authTokenType, boolean notifyAuthFailure) {
        this(AccountManager.get(context), account, authTokenType, notifyAuthFailure);
    }

    // Visible for testing. Allows injection of a mock AccountManager
    AndroidAuthenticator(AccountManager accountManager, Account account,
                         String authTokenType, boolean notifyAuthFailure) {
        mAccountManager = accountManager;
        mAccount = account;
        mAuthTokenType = authTokenType;
        mNotifyAuthFailure = notifyAuthFailure;
    }

    public Account getAccount() {
        return mAccount;
    }

    @Override
    public String getAuthToken() throws AuthFailureError {
        AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(mAccount, mAuthTokenType, mNotifyAuthFailure, null, null);
        Bundle result;
        try {
            result = future.getResult();
        } catch (Exception e) {
            throw new AuthFailureError("Error while retrieving auth token", e);
        }
        String authToken = null;
        if (future.isDone() && !future.isCancelled()) {
            if (result.containsKey(AccountManager.KEY_INTENT)) {
                Intent intent = result.getParcelable(AccountManager.KEY_INTENT);
                throw new AuthFailureError(intent);
            }
            authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
        }
        if (authToken == null) {
            throw new AuthFailureError("Got null auth token for type: " + mAuthTokenType);
        }
        return authToken;
    }

    @Override
    public void invalidateAuthToken(String authToken) {
        mAccountManager.invalidateAuthToken(mAccount.type, authToken);
    }
}

