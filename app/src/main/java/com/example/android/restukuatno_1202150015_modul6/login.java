package com.example.android.restukuatno_1202150015_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    //deklarasi variable yang akan digunakan
    EditText email, pass;
    //firabase authentification fields;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mengenali email dan password pada layout
        email = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPass);

        //firebase authentification instance
        mAuth = FirebaseAuth.getInstance();

        //progress dialog context
        pd = new ProgressDialog(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //checking user presence
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //apabila user tidak sama dengan null
                if ( user != null) {
                    //berpindah dari ke home class
                    Intent home = new Intent(login.this, home.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                    //menutup current activity
                    finish();
                }
            }
        };
        //auth listener
        mAuth.addAuthStateListener(mAuthListener);
    }

    //ketika button masuk di klik
    public void btnMasuk(View view) {
        //progress dialog dengan judul loging in user
        pd.setTitle("Loging in the user");
        //berisi pesan untuk menunggu hingga proses selesai
        pd.setMessage("Please wait...");
        pd.show();
        //memanggil method login user
        loginUser();

    }

    //attach firebase auth listener
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    //remove firebase auth state listener
    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    //method loginUser
    private void loginUser() {
        final String userEmail, userPassword;
        //mendapatkan nilai dari email dan password kemudian mengkonversi ke string
        userEmail = email.getText().toString().trim();
        userPassword = pass.getText().toString().trim();

        if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)){
            mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //apabila berhasil sign in dengan email dan password yg benar
                    if (task.isSuccessful()){
                        //menutup progres dialog
                        pd.dismiss();
                        //intent ke class home
                        Intent home = new Intent(login.this, home.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //memulai intent
                        startActivity(home);
                    }else {
                        //memunculkan toast berisi pesan gagal login
                        Toast.makeText(login.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                    }
                }
            });
        }else {
            //set error pada field email dan password karena field tersebut harus di isi
            email.setError("Required field");
            pass.setError("Required field");
            //muncul toast berisi pesan untuk memasukkan email dan password
            Toast.makeText(login.this, "Please enter the valid user email and password", Toast.LENGTH_SHORT).show();
            //menutup progress dialog
            pd.dismiss();
        }
    }

    //method ketika button daftar pada halaman login diklik
    public void btnDaftar(View view) {
        //berpindak ke halaman daftar
        startActivity(new Intent(this, MainActivity.class));
    }
}

