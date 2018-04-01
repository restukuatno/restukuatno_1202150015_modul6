package com.example.android.restukuatno_1202150015_modul6;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    //deklarasi variable
    AppBarLayout nBarLayout;
    Toolbar nToolbar;
    TabLayout nTabLayout;
    ViewPager nViewPager;
    FirebaseAuth nAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //mengakses appbarlayout, toolbar, tablayout, viewpager pada layout
        nBarLayout = (AppBarLayout) findViewById(R.id.inibarlayout);
        nToolbar = (Toolbar) findViewById(R.id.initoolbar);
        nTabLayout = (TabLayout) findViewById(R.id.initablayout);
        nViewPager = (ViewPager) findViewById(R.id.iniviewpager);

        //menentukan toolbar
        setSupportActionBar(nToolbar);
        setupVP(nViewPager);
        //autorisasi firebase
        nAuth = FirebaseAuth.getInstance();

        //mengikat tablayout dengan viewpager
        nTabLayout.setupWithViewPager(nViewPager);

    }

    //method yang dijalankan untuk membuat menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //method yang dijalankan ketika option 'keluar' dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.btnKeluar){
            //firebase instance sign out
            FirebaseAuth.getInstance().signOut();
            //intent ke login class
            startActivity(new Intent(this, login.class));
            //menutup current activity
            finish();
        }
        return true;
    }

    //menentukan adapter untuk viewpager
    public void setupVP(ViewPager v){
        VPAdapter adapter  = new VPAdapter(getSupportFragmentManager());
        //set title fragment dengan nama be2rikut
        adapter.addFragment(new home1(), "TERBARU");
        adapter.addFragment(new home2(), "SAYA");
        //set adapter untuk view
        v.setAdapter(adapter);
    }

    //ketika floatting button diklik
    public void addpost(View view) {
        //berpindah ke postphoto class
        startActivity(new Intent(home.this, posfoto.class));

    }

    //subclass sebagai adapter untuk viewpager dan fragment
    class VPAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> listfragment;
        ArrayList<String>    listtitle;

        public VPAdapter(FragmentManager fm) {
            super(fm);
            listfragment  = new ArrayList<>();
            listtitle = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return listfragment.get(position);
        }

        public void addFragment(Fragment f, String t){
            listfragment.add(f);
            listtitle.add(t);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listtitle.get(position);
        }

        @Override
        public int getCount() {
            return listfragment.size();
        }
    }
}
