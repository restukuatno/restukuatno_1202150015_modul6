package com.example.android.restukuatno_1202150015_modul6;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class home2 extends Fragment {

    //deklarasi variable
    adapter2 adapterPost;
    DatabaseReference nDatabaseRef;
    ArrayList<databasepost> list;
    RecyclerView newRecView;


    public home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home2, container, false);
        //firebase instance
        nDatabaseRef = FirebaseDatabase.getInstance().getReference().child("post");
        //mebuat arraylist baru
        list = new ArrayList<>();
        //membuat adapter baru
        adapterPost = new adapter2(list, this.getContext());
        //mengenali recycler view pada layout
        newRecView = view.findViewById(R.id.inirecyclerview);

        //menampilkan recycler view nya
        newRecView.setHasFixedSize(true);
        newRecView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newRecView.setAdapter(adapterPost);

        //listener yang menghandle ketika nilai pada firebase berubah
        nDatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //mendapatkan nilai dari post saat ini
                databasepost crnt = dataSnapshot.getValue(databasepost.class);
                if(crnt.getUsr().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    crnt.key = dataSnapshot.getKey();
                    list.add(crnt);
                    adapterPost.notifyDataSetChanged();
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
        return view;
    }
}


