package com.example.team_project_work_late.ui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.team_project_work_late.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView=findViewById(R.id.bottom_navigation);

        //첫 화면 띄우기
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,new Frag1()).commit();

        //case 함수를 통해 클릭 받을 때마다 화면 변경하기
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_1 :
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Frag1()).commit();
                        break;
                    case R.id.action_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Frag2()).commit();
                        break;
                    case R.id.action_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new Frag3()).commit();
                        break;

                }
                return true;
            }
        });
    }
}