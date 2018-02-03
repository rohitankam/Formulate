package com.ankam.rohit.formulate;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ankam.rohit.formulate.RecylcleView.Adapter;
import com.ankam.rohit.formulate.RecylcleView.Cards;
import com.ankam.rohit.formulate.RecylcleView.LoadMore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cards> arrayList=new ArrayList<Cards>();
    Adapter adapter;
    String[] name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=getResources().getStringArray(R.array.formulas);
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
}
