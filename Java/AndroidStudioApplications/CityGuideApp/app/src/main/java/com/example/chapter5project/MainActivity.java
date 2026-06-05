package com.example.chapter5project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView simplelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.buildingsic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        simplelist = (ListView) findViewById(R.id.simpleListView);
        String[] buildings = {"Eiffel Tower", "Kyoto Tower", "Statue of Liberty", "World Trade Center", "Stonehenge"};
        ArrayAdapter adapter = (new ArrayAdapter<String>(this, R.layout.activity_main, R.id.travel, buildings));
        simplelist.setAdapter(adapter);
        simplelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(position)
                {
                    case 0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.toureiffel.paris/en")));
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kyoto-tower.jp/en/")));
                        break;
                    case 2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nps.gov/stli/index.htm")));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, OneWorld.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, Stonehenge.class));
                        break;
            }
        }
        });
    }
}