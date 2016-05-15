package edu.rose_hulman.srproject.humanitarianapp.controllers;


import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

import edu.rose_hulman.srproject.humanitarianapp.R;
import edu.rose_hulman.srproject.humanitarianapp.models.Person;
import edu.rose_hulman.srproject.humanitarianapp.models.Roles;
import edu.rose_hulman.srproject.humanitarianapp.nonlocaldata.NonLocalDataService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedInput;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, NewUserDialogFragment.AddPersonListener {

        private static final String TAG = "SignInActivity";
        private static final int RC_SIGN_IN = 9001;
    NonLocalDataService service=new NonLocalDataService();
    String personID;
    String googleID;
    String emailAddress;

        private GoogleApiClient mGoogleApiClient;
        private TextView mStatusTextView;
        private ProgressDialog mProgressDialog;
        private boolean logMeOut=false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            switchToMain("3000");
            if (this.getIntent()!=null && this.getIntent().getExtras()!=null) {
                logMeOut = this.getIntent().getExtras().getBoolean("logMeOut", false);
            }
            setContentView(R.layout.activity_login);

            // Views
            mStatusTextView = (TextView) findViewById(R.id.status);

            // Button listeners
            findViewById(R.id.go_button).setOnClickListener(this);
            findViewById(R.id.sign_in_button).setOnClickListener(this);
            findViewById(R.id.sign_out_button).setOnClickListener(this);
            findViewById(R.id.disconnect_button).setOnClickListener(this);

            // [START configure_signin]
            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken("380073753318-jo66uj3ghqmqmjg32p1s1662mnukbohj.apps.googleusercontent.com")
                    .build();
            // [END configure_signin]

            // [START build_client]
            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            // [END build_client]

            // [START customize_button]
            // Customize sign-in button. The sign-in button can be displayed in
            // multiple sizes and color schemes. It can also be contextually
            // rendered based on the requested scopes. For example. a red button may
            // be displayed when Google+ scopes are requested, but a white button
            // may be displayed when only basic profile is requested. Try adding the
            // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
            // difference.
            SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            signInButton.setScopes(gso.getScopeArray());
            // [END customize_button]
        }

    @Override
        public void onStart() {
            super.onStart();

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                //showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        //hideProgressDialog();
                        handleSignInResult(googleSignInResult);
                    }
                });
            }
        }

        // [START onActivityResult]
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        }
        // [END onActivityResult]

        // [START handleSignInResult]
        private void handleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()) + acct.getDisplayName());

                //acct.
                updateUI(true);
                googleID=acct.getId();
                emailAddress=acct.getEmail();
//                if (!logMeOut) {
//                    Log.wtf("s40-login", "!logMeOut");
//                    checkValidity(acct.getId(), acct.getEmail());
                                    //switchToMain("3000");
                                    //switchToMain(personID);
//                                }
//                switchToMain("3000");
//                Callback<Response> callback=new Callback<Response>() {
//                    @Override
//                    public void success(Response response, Response response2) {
//                        Log.wtf("s40", "found it");
//                        ObjectMapper mapper=new ObjectMapper();
//                        TypeReference<HashMap<String, Object>> typeReference=
//                                new TypeReference<HashMap<String, Object>>() {
//                                };
//                        try {
//                            HashMap<String, Object> map = mapper.readValue(response.getBody().in(), typeReference);
//                            boolean found = (boolean) map.get("found");
//                            if (found) {
//                               personID=(String) map.get("personId");
////                                if (!logMeOut) {
////                                    switchToMain("3000");
////                                    //switchToMain(personID);
////                                }
//                            }
//                            else{
//
//                            }
//                        }catch(Exception e){
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//    //                    mEmailView.setError(error.getMessage());
//    //                    mEmailView.requestFocus();
//                    }
//                };
//                LoginService loginService=new LoginService();
//                loginService.doLogin(acct.getEmail(), callback);




            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
        }

        // [END handleSignInResult]
        private void switchToMain(String id){
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("userID", id);
            startActivity(intent);
        }

        // [START signIn]
        private void signIn() {
            logMeOut=false;
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        // [END signIn]

        // [START signOut]
        private void signOut() {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END signOut]

        // [START revokeAccess]
        private void revokeAccess() {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            // [START_EXCLUDE]
                            updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END revokeAccess]

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            // An unresolvable error has occurred and Google APIs (including Sign-In) will not
            // be available.
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
        }

        private void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
            }

            mProgressDialog.show();
        }

        private void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }

        private void updateUI(boolean signedIn) {
            if (signedIn) {
                findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            } else {
                mStatusTextView.setText(R.string.signed_out);

                findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
                case R.id.sign_out_button:
                    signOut();
                    break;
                case R.id.disconnect_button:
                    revokeAccess();
                    break;
                case R.id.go_button:
                    go();
                    break;
            }
        }
    public void go(){

       checkValidity(googleID, emailAddress);
    }


    private void checkValidity(final String token, final String email){
        Log.wtf("s40-login", "In Verify!");
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.wtf("s40-login", "Successful Verify!");
                ObjectMapper mapper=new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference=
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                    String personId= (String) o.get("personId");
                    //switchToMain("3000");
                    switchToMain(personId);
                }
                catch(Exception e){
                    Log.wtf("login", e.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse()!=null){
                    Log.wtf("login", error.getResponse().getStatus()+"");
                    Log.wtf("login", token);
                    if (error.getResponse().getStatus()==420){
                        Log.wtf("login", "Signup");
                        //
                        signUp(token, email);
                    }
                }
                else{
                    if (error.getMessage().equals("420")){
                        signUp(token, email);
                    }
                    else {
                        Log.wtf("s40-login", "Failed Failure!");
                        Log.wtf("s40-login", error.getMessage());
                        Log.wtf("s40-login", error.getUrl());
                    }
                }

            }
        };
        service.verify(token, callback);
    }
    private void signUp(final String token, final String email){

        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.wtf("s40-login", "Successful Sign Up!");
                ObjectMapper mapper=new ObjectMapper();
                TypeReference<HashMap<String, Object>> typeReference=
                        new TypeReference<HashMap<String, Object>>() {
                        };
                try {
                    HashMap<String, Object> o = mapper.readValue(response.getBody().in(), typeReference);
                    personID= (String) o.get("personId");
                    Log.wtf("s40-login", personID);
                    emailAddress=email;
                    addPerson();
                    //switchToMain(personID);
                }
                catch(Exception e){
                    Log.wtf("login", e.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    ObjectMapper mapper=new ObjectMapper();
                    TypeReference<HashMap<String, Object>> typeReference=
                            new TypeReference<HashMap<String, Object>>() {
                            };
                    HashMap<String, Object> errorMap=mapper.readValue(error.getResponse().getBody().in(), typeReference);
                    Log.wtf("loginError", (String)errorMap.get("error"));
                }catch(Exception e){
                    Log.wtf("loginError Error", e.getMessage());
                }
//                if (error.getResponse()!=null){
//                    Log.wtf("login", error.getResponse().getStatus()+"");
//                    if (error.getResponse().getStatus()==420){
//                        signUp(token);
//                    }
//                }
            }
        };
        service.signUp(token, callback);
    }
    public void addPerson() {
        DialogFragment newFragment = new NewUserDialogFragment();
        newFragment.show(getFragmentManager(), "addPerson");
    }
    public String getEmail(){
        return emailAddress;
    }

    @Override
    public void addNewPerson(final String name, String phone, String email, Roles.PersonRoles role) {


            Person p=new Person(name, phone, email, Long.parseLong(personID));

            edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation location=new edu.rose_hulman.srproject.humanitarianapp.models.Person.PersonLocation();
            location.setName("Omega 4 Relay");
            location.setTime("2185-04-05 14:45");
            location.setLat(34.56f);
            location.setLng(-5.45f);

            //location.setID(10000);

            Callback<Response> responseCallback = new Callback<Response>() {
                @Override
                public void success(Response response, Response response2) {
                    Log.wtf("s40-login", "Successful Add!");
                    Toast.makeText(getApplicationContext(), "Successful adding of new person: " + name, Toast.LENGTH_LONG).show();
                    switchToMain(personID);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("RetrofitError", error.getMessage());
                }
            };
            //NonLocalDataService service=new NonLocalDataService();
            service.addNewPerson(p, personID,responseCallback);

    }
}

