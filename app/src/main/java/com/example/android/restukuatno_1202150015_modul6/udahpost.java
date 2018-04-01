package com.example.android.restukuatno_1202150015_modul6;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class udahpost extends AppCompatActivity {
    //deklarasi variable yang digunakan
    TextView usr, judul, deskripsi;
    ImageView gmbr;
    EditText comments;
    RecyclerView nRecView;
    adapter1 adapterC;
    ArrayList<database1> list;
    DatabaseReference nDatabaseRef;
    ProgressDialog pd;
    String nUsr, imageid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udahpost);
        //inisialisasi objek yang digunakan
        usr = findViewById(R.id.posts_uploader);
        gmbr = findViewById(R.id.posts_image);
        comments = findViewById(R.id.posts_komen);
        pd = new ProgressDialog(this);
        judul = findViewById(R.id.posts_judul);
        deskripsi = findViewById(R.id.posts_deskripsi);
        nDatabaseRef = FirebaseDatabase.getInstance().getReference().child("comments");
        nRecView = findViewById(R.id.posts_recview);
        list = new ArrayList<>();
        adapterC = new adapter1(this, list);
        //menampilkan recycler
        nRecView.setHasFixedSize(true);
        nRecView.setLayoutManager(new LinearLayoutManager(this));
        nRecView.setAdapter(adapterC);

        //memberi nilai view pada class
        String[] crntusr = FirebaseAuth.getInstance().getInstance().getCurrentUser().getEmail().split("@");
        nUsr = crntusr[0];
        imageid = getIntent().getStringExtra("key");
        usr.setText(getIntent().getStringExtra("usr"));
        judul.setText(getIntent().getStringExtra("judul"));
        deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        Glide.with(this).load(getIntent().getStringExtra("gmbr")).override(250, 250).into(gmbr);

        nDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                database1 crnt = dataSnapshot.getValue(database1.class);
                if (crnt.getCommentedImage().equals(imageid)) {
                    list.add(crnt);
                    adapterC.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //ketika button post pada halaman komen di klik
    public void btnKomen(View view) {
        //memunculkan dialog
        pd.setTitle("Adding Comment");
        pd.setMessage("Please wait, the comment is being added ...");
        pd.show();
        //inisialisasi objek
        database1 cmnt = new database1(nUsr, comments.getText().toString(), imageid);
        //menginputkan data ke firebase
        nDatabaseRef.push().setValue(cmnt).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //apabila berhasil menambahkan komentar
                if (task.isSuccessful()) {
                    //memunculkan toast
                    Toast.makeText(udahpost.this, "You commented on this post", Toast.LENGTH_SHORT).show();
                    //set field menjadi kosong kemali
                    comments.setText(null);
                } else {
                    //apabila tidak berhasil maka muncul toas berisi gagal menambah komen
                    Toast.makeText(udahpost.this, "Failed to add comment", Toast.LENGTH_SHORT).show();
                }
                //menutup dialog
                pd.dismiss();
            }
        });
    }

}
