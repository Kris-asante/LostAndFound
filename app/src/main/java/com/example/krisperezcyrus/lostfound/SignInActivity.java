package com.example.krisperezcyrus.lostfound;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "ACTION";
    private SignInButton googleBtn;
    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN = 2;
    //GoogleSignInApi mGoogleSignInClient;
    GoogleApiClient mGoogleApiClient;

    EditText username;
    EditText password;
    Button login;

    String name = "coasante" ;
    String pass = "1ljsxn2" ;

    String name1 = "kjkusi" ;
    String pass1 = "1ljsxn2" ;




    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        setGoogleBtnText(googleBtn, "Sign In with Google");
    }

    protected void setGoogleBtnText(SignInButton googleBtn, String buttonText){
        // setting text for google sign in button
        for (int i = 0; i < googleBtn.getChildCount(); i++){
            View v = googleBtn.getChildAt(i);
            if(v instanceof TextView){
                TextView textView = (TextView) v;
                textView.setText(buttonText);
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);

//
//        View backgroundImage = findViewById(R.id.background);
//        Drawable background = backgroundImage.getBackground();
//        background.setAlpha(80);


         username = findViewById(R.id.username);


        password = findViewById(R.id.password);

        login = findViewById(R.id.loginbutton);


        googleBtn = findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                Snackbar.make(v, "Internet Connection Required", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //use this
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //check this if doesn't work
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    Intent profile = new Intent(SignInActivity.this, Ankasa.class);
                    startActivity(profile);
                }
            }
        };

        // mGoogleSignInClient = (GoogleSignInApi) GoogleSignIn.getClient(this, gso);
    }


    private void signIn() {
        //GoogleSignInApi mGoogleSignInClient;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

        /*Intent signInIntent = mGoogleSignInClient.getSignInIntent((GoogleApiClient) mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent = new Intent(SignInActivity.this,Ankasa.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.googleBtn), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void login(View view) {


        boolean cancel = false;
        View focusView = null;

        final String yourname = username.getText().toString().trim();
        final String yourname1 = username.getText().toString().trim();
        final String yourpass = password.getText().toString().trim();
        final String yourpass1 = password.getText().toString().trim();


        if (TextUtils.isEmpty(yourname)) {
            username.setError("This field is required");
            focusView = username;
            cancel = true;
        }

        if (TextUtils.isEmpty(yourname1)) {
            username.setError("This field is required");
            focusView = username;
            cancel = true;
        }

        if (TextUtils.isEmpty(yourpass)) {
            password.setError("This field is required");
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(yourpass1)) {
            password.setError("This field is required");
            focusView = password;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else {


            if (username.getText().toString().equals(name) && password.getText().toString().equals(pass)) {
                Intent intent = new Intent(SignInActivity.this,Ankasa.class);
                startActivity(intent);
                Toast.makeText(this, "Authentication Verified", Toast.LENGTH_LONG).show();


            }

            if (username.getText().toString().equals(name1) && password.getText().toString().equals(pass1)) {
                Intent intent = new Intent(SignInActivity.this,Ankasa.class);
                startActivity(intent);
                Toast.makeText(this, "Authentication Verified", Toast.LENGTH_LONG).show();
                username.setText("");
                password.setText("");

            }else {

                Snackbar.make(findViewById(R.id.loginbutton), "Authentication Failed, Wrong Details", Snackbar.LENGTH_LONG).show();

            }

        }

    }
}