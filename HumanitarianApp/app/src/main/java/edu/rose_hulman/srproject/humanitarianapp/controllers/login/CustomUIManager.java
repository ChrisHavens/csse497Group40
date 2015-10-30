package edu.rose_hulman.srproject.humanitarianapp.controllers.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.controllers.login.SignInDialog;

import com.google.identitytoolkit.GitkitUser;
import com.google.identitytoolkit.IdProvider;
import com.google.identitytoolkit.UiManager;

/**
 * Created by daveyle on 10/29/2015.
 */
public class CustomUIManager implements UiManager {
    RequestHandler requestHandler;
    private Activity activity;

    public CustomUIManager(Activity activity){
        this.activity=activity;
    }

    @Override
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler=requestHandler;
    }

    @Override
    public void showStartSignIn(GitkitUser.UserProfile userProfile) {

    }

    @Override
    public void showPasswordSignIn(String s) {
        DialogFragment newFragment = new SignInDialog();
        newFragment.show(activity.getFragmentManager(), "signIn");

    }

    @Override
    public void showPasswordSignUp(String s) {
        DialogFragment newFragment = new SignInDialog();
        newFragment.show(activity.getFragmentManager(), "signUp");
    }

    @Override
    public void showPasswordAccountLinking(String s, IdProvider idProvider) {

    }

    @Override
    public void showIdpAccountLinking(String s, IdProvider idProvider, IdProvider idProvider1) {

    }

    @Override
    public void handleError(ErrorCode errorCode, Object... objects) {

    }

    @Override
    public void dismiss() {

    }

    public void signIn(String email, String password){
        UiManager.SignInWithPasswordRequest request=new UiManager.SignInWithPasswordRequest();
        request.setEmail(email);
        request.setPassword(password);
        requestHandler.handle(request);
    }
    public void signUp(String email, String name, String password){
        UiManager.SignUpWithPasswordRequest request=new UiManager.SignUpWithPasswordRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setDisplayName(name);
        requestHandler.handle(request);
    }
//    public static class SignInDialog extends DialogFragment{
//        // UI references.
//        private AutoCompleteTextView mEmailView;
//        private EditText mPasswordView;
//        private View mProgressView;
//        private View mLoginFormView;
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            // Get the layout inflater
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            View v= onCreateView(inflater);
//            builder.setView(v)
//                    // Add action buttons
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            attemptLogin();
//                            SignInDialog.this.getDialog().dismiss();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            SignInDialog.this.getDialog().dismiss();
//                        }
//                    });
//            return builder.create();
//
//        }
//        public View onCreateView(LayoutInflater inflater){
//            View view=inflater.inflate(R.layout.activity_login, null);
//            // Set up the login form.
//            mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
//            //populateAutoComplete();
//
//            mPasswordView = (EditText) view.findViewById(R.id.password);
//            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                        attemptLogin();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            mPasswordView.setVisibility(View.GONE);
//
//            mLoginFormView = view.findViewById(R.id.login_form);
//            mProgressView = view.findViewById(R.id.login_progress);
//            return view;
//        }
//        public void setPasswordViewVisibility(int visibility){
//            mPasswordView.setVisibility(visibility);
//        }
//
//
//
//        /**
//         * Attempts to sign in or register the account specified by the login form.
//         * If there are form errors (invalid email, missing fields, etc.), the
//         * errors are presented and no actual login attempt is made.
//         */
//        public void attemptLogin() {
//
//            // Reset errors.
//            mEmailView.setError(null);
//            mPasswordView.setError(null);
//
//            // Store values at the time of the login attempt.
//            String email = mEmailView.getText().toString();
//            String password = mPasswordView.getText().toString();
//
//            boolean cancel = false;
//            View focusView = null;
//
//            // Check for a valid password, if the user entered one.
//            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//                mPasswordView.setError(getString(R.string.error_invalid_password));
//                focusView = mPasswordView;
//                cancel = true;
//            }
//
//            // Check for a valid email address.
//            if (TextUtils.isEmpty(email)) {
//                mEmailView.setError(getString(R.string.error_field_required));
//                focusView = mEmailView;
//                cancel = true;
//            } else if (!isEmailValid(email)) {
//                mEmailView.setError(getString(R.string.error_invalid_email));
//                focusView = mEmailView;
//                cancel = true;
//            }
//
//            if (cancel) {
//                // There was an error; don't attempt login and focus the first
//                // form field with an error.
//                focusView.requestFocus();
//            } else {
//                // Show a progress spinner, and kick off a background task to
//                // perform the user login attempt.
//                showProgress(true);
//
////            mAuthTask = new UserLoginTask(email, password);
////            mAuthTask.execute((Void) null);
//            }
//        }
//
//        private boolean isEmailValid(String email) {
//            //TODO: Replace this with your own logic
//            return email.contains("@");
//        }
//
//        private boolean isPasswordValid(String password) {
//            //TODO: Replace this with your own logic
//            return password.length() > 5;
//        }
//
//        /**
//         * Shows the progress UI and hides the login form.
//         */
//        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//        public void showProgress(final boolean show) {
//            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//            // for very easy animations. If available, use these APIs to fade-in
//            // the progress spinner.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//                int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                    }
//                });
//
//                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                mProgressView.animate().setDuration(shortAnimTime).alpha(
//                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                    }
//                });
//            } else {
//                // The ViewPropertyAnimator APIs are not available, so simply show
//                // and hide the relevant UI components.
//                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            }
//        }
//    }
//    public static class SignUpDialog extends DialogFragment{
//
//
//        // UI references.
//        private AutoCompleteTextView mEmailView;
//        private EditText mPasswordView;
//        private EditText mPasswordConfirmView;
//        private EditText mNameView;
//        private View mProgressView;
//        private View mLoginFormView;
//
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            // Get the layout inflater
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            View v= onCreateView(inflater);
//            builder.setView(v)
//                    // Add action buttons
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            attemptLogin();
//                            SignUpDialog.this.getDialog().dismiss();
//                        }
//                    })
//                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            SignUpDialog.this.getDialog().dismiss();
//                        }
//                    });
//            return builder.create();
//
//        }
//        public View onCreateView(LayoutInflater inflater){
//            View view=inflater.inflate(R.layout.activity_login, null);
//
//            // Set up the login form.
//            mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
//            //populateAutoComplete();
//            mNameView= (EditText)view.findViewById(R.id.nameField);
//
//            mPasswordView = (EditText) view.findViewById(R.id.password);
//            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                        attemptLogin();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            //mPasswordView.setVisibility(View.GONE);
//            mPasswordConfirmView = (EditText) view.findViewById(R.id.passwordConfirm);
//            mPasswordConfirmView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//                @Override
//                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                        attemptLogin();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            //mPasswordConfirmView.setVisibility(View.GONE);
//
//            mLoginFormView = view.findViewById(R.id.login_form);
//            mProgressView = view.findViewById(R.id.login_progress);
//            return view;
//        }
//        public void setPasswordViewVisibility(int visibility){
//            mPasswordView.setVisibility(visibility);
//            mPasswordConfirmView.setVisibility(visibility);
//        }
//
//
//
//        /**
//         * Attempts to sign in or register the account specified by the login form.
//         * If there are form errors (invalid email, missing fields, etc.), the
//         * errors are presented and no actual login attempt is made.
//         */
//        public void attemptLogin() {
//
//            // Reset errors.
//            mEmailView.setError(null);
//            mPasswordView.setError(null);
//            mPasswordConfirmView.setError(null);
//
//            // Store values at the time of the login attempt.
//            String email = mEmailView.getText().toString();
//            String password = mPasswordView.getText().toString();
//            String passwordConfirm= mPasswordConfirmView.getText().toString();
//            boolean cancel = false;
//            View focusView = null;
//
//            // Check for a valid password, if the user entered one.
//            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//                mPasswordView.setError(getString(R.string.error_invalid_password));
//                focusView = mPasswordView;
//                cancel = true;
//            }
//            if (!TextUtils.isEmpty(passwordConfirm) && !isPasswordValid(passwordConfirm)) {
//                mPasswordConfirmView.setError(getString(R.string.error_invalid_password));
//                focusView = mPasswordConfirmView;
//                cancel = true;
//            }
//            if (!password.equals(passwordConfirm)) {
//                mPasswordConfirmView.setError(getString(R.string.error_no_matching_password));
//                mPasswordView.setError(getString(R.string.error_no_matching_password));
//                focusView = mPasswordView;
//                cancel = true;
//            }
//
//            // Check for a valid email address.
//            if (TextUtils.isEmpty(email)) {
//                mEmailView.setError(getString(R.string.error_field_required));
//                focusView = mEmailView;
//                cancel = true;
//            } else if (!isEmailValid(email)) {
//                mEmailView.setError(getString(R.string.error_invalid_email));
//                focusView = mEmailView;
//                cancel = true;
//            }
//
//            if (cancel) {
//                // There was an error; don't attempt login and focus the first
//                // form field with an error.
//                focusView.requestFocus();
//            } else {
//                // Show a progress spinner, and kick off a background task to
//                // perform the user login attempt.
//                //showProgress(true);
//
////            mAuthTask = new UserLoginTask(email, password);
////            mAuthTask.execute((Void) null);
//            }
//        }
//
//        private boolean isEmailValid(String email) {
//            //TODO: Replace this with your own logic
//            return email.contains("@");
//        }
//
//        private boolean isPasswordValid(String password) {
//            //TODO: Replace this with your own logic
//            return password.length() > 5;
//        }
//
//    }



}
