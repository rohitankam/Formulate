package com.ankam.rohit.formulate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ankam.rohit.formulate.RecylcleView.Adapter;
import com.ankam.rohit.formulate.RecylcleView.Cards;
import com.ankam.rohit.formulate.RecylcleView.LoadMore;

import java.util.ArrayList;
import java.util.UUID;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;
    RecyclerView r1;
    ArrayList<Cards> arrayList=new ArrayList<Cards>();
    Adapter adapter;
    String[] name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mTextMessage = (TextView) findViewById(R.id.message);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);  name=getResources().getStringArray(R.array.formulas);
        RecyclerView recycle=(RecyclerView)findViewById(R.id.recycle);
        // randomdata();

        int i=0;
        for (String n:name) {
            Cards cards=new Cards(n);
            arrayList.add(cards);
            i++;
        }
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter =new Adapter(recycle,this,arrayList);
        recycle.setAdapter(adapter);

        adapter.setLoadmore(new LoadMore() {
            @Override
            public void LoadMore() {
                if(arrayList.size()<=10){
                    arrayList.add(null);
                    adapter.notifyItemInserted(arrayList.size()-1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            arrayList.remove(arrayList.size()-1);
                            adapter.notifyItemRemoved(arrayList.size());

                            int index=arrayList.size();
                            int end=index+10;
                            for(int i=index;i<end;i++){
                                randomdata();
                            }
                            adapter.notifyDataSetChanged();
                            adapter.setLoaded();
                        }
                    },5000);
                }
                else {
                    Toast.makeText(MainActivity.this, "Load data completed. ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void randomdata() {
        for(int i=0;i<=10;i++){
            String name= UUID.randomUUID().toString();
            Cards item=new Cards(name);
            arrayList.add(item);
        }
    }

    public void input(View view) {
        startActivity(new Intent(this,Input.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main2_drawer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
      return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.form) {
            // Handle the camera action
//            startActivity(new Intent(this,MainActivity.class));
            NavUtils.getParentActivityIntent(MainActivity.this);
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this,Matrix_Calc.class));

        } else if (id == R.id.nav_slideshow) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.tic_tac) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
