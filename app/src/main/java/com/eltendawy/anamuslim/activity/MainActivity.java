package com.eltendawy.anamuslim.activity;

import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.eltendawy.anamuslim.Base.BaseActivity;
import com.eltendawy.anamuslim.Fragment.Ahadith;
import com.eltendawy.anamuslim.Fragment.Quran;
import com.eltendawy.anamuslim.Fragment.Radio;
import com.eltendawy.anamuslim.R;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    int current_selected_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        quran=new Quran();
        ahadith=new Ahadith();
        radio=new Radio();
        try {
            setfragmentContainer(R.id.fragment_container);
            showFragment(quran);
            navigationView.setCheckedItem(R.id.nav_quran);
        } catch (Exception e) {
            Log.e("Error",e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_quran) {
            try
            {
                showFragment(quran);
            }
            catch (Exception e) {
                Log.e("Error",e.getMessage());
            }
        } else if (id == R.id.nav_hadith) {
            try
            {
                showFragment(ahadith);
            }
            catch (Exception e) {
                Log.e("Error",e.getMessage());
            }
        } else if (id == R.id.nav_radio) {
            try
            {
                showFragment(radio);
            }
            catch (Exception e) {
                Log.e("Error",e.getMessage());
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
