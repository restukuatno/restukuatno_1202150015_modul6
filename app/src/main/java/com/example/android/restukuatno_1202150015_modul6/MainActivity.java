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

public class MainActivity extends AppCompatActivity {
    //deklarasi variable yang akan digunakan
    EditText daftarUser, daftarPass;
    //firebase authentifiaction id
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    //progress dialog
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mengakses edit text pada layout
        daftarUser = (EditText) findViewById(R.id.editDaftarEmail);
        daftarPass = (EditText) findViewById(R.id.editDaftarPass);

        //progress dialog instance
        pd = new ProgressDialog(this);

        //firebase instance
        mAuth = FirebaseAuth.getInstance();
        //membuar auth state listener yang akan menghandel sign up user
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //check user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //apabila user ada maka akan diarahkan menuju login class
                if ( user != null){
                    Intent login = new Intent(MainActivity.this, login.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //memulai intent
                    startActivity(login);
                }
            }
        };
        //memanggul state listener untuk menghandel login
        mAuth.addAuthStateListener(mAuthListener);
    }

    //method yang dijalankan ketika button pada halaman daftar diklik
    public void btnDaftar2(View view) {
        //akan menampilkan progress dialog dengan judul Create Account
        pd.setTitle("Create Account: ");
        //dan pesan yang meminta untuk menunggu sementara akun dibuat
        pd.setMessage("Wait until the account is being created");
        //menampilkan progress dialog
        pd.show();
        //menjalankan method createUserAccount
        createUserAccount();
    }

    //attach listener pada firebase auth instance
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    //remove listener pada firebase auth instance
    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }

    //membuat akun
    private void createUserAccount() {
        String emailUser, passUser;

        //mendapatkan nilai dari masukkan edit text
        emailUser = daftarUser.getText().toString().trim();
        passUser = daftarPass.getText().toString().trim();
        //apabila edit text email dan password tidak kosong maka
        if ( !TextUtils.isEmpty(emailUser) && !TextUtils.isEmpty(passUser)){
            mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //apabila berhasil melakukan regist/daftar akan memunculkan pesan bahwa akun berhasil dibuat
                    if (task.isSuccessful()){
                        //membuat toast
                        Toast.makeText(MainActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                        //intent dari class daftar ke login
                        Intent login = new Intent(MainActivity.this, login.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //memulai intent
                        startActivity(login);
                        //apabila kondisi lain selain di atas terjadi
                    }else {
                        //menampilkan toast bahwa akun gagal di buat
                        Toast.makeText(MainActivity.this, "Failed to created account", Toast.LENGTH_SHORT).show();
                        //menutup progress dialog
                        pd.dismiss();
                    }
                }
            });
        }
    }
}

