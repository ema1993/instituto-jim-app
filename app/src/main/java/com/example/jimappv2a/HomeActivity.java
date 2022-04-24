package com.example.jimappv2a;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimappv2a.Common.Common;
import com.example.jimappv2a.Preferences.SaveSharedPreference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private DrawerLayout drawer;
    int menuClickId=-1;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subscribeToTopic(Common.createTopicAviso());
        updateToken();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calificaciones, R.id.nav_inasistencias, R.id.nav_avisos)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront(); //Fixed

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.txt_draw);

        navUsername.setText(Common.selectedAlumno.getNombre());

        checkIsOpenFromActivity();
    }

    private void checkIsOpenFromActivity() {
        boolean isOpenFromNewOrder = getIntent().getBooleanExtra(Common.IS_OPEN_ACTIVITY_NEW_AVISO,false);
        if(isOpenFromNewOrder)
        {
            navController.popBackStack();
            navController.navigate(R.id.nav_avisos);
            menuClickId = R.id.nav_avisos;
        }
    }

    private void subscribeToTopic(String topicAviso) {
        FirebaseMessaging.getInstance()
                .subscribeToTopic(topicAviso)
                .addOnFailureListener(e -> {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                })
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful())
                        Toast.makeText(this, "Error: "+task.isSuccessful(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updateToken() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnFailureListener(e -> Toast.makeText(HomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show())
                .addOnSuccessListener(instanceIdResult -> {
                    Common.updateToken(HomeActivity.this,instanceIdResult.getToken());

                    Log.d("Token",instanceIdResult.getToken());
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        menuitem.setChecked(true);
        drawer.closeDrawers();
        switch (menuitem.getItemId())
        {
            case R.id.nav_home:
                if(menuitem.getItemId() != menuClickId)
                    navController.navigate(R.id.nav_home);
                break;
            case R.id.nav_calificaciones:
                if(menuitem.getItemId() != menuClickId)
                    navController.navigate(R.id.nav_calificaciones);
                break;
            case R.id.nav_inasistencias:
                if(menuitem.getItemId() != menuClickId)
                    navController.navigate(R.id.nav_inasistencias);
                break;
            case R.id.nav_avisos:
                if(menuitem.getItemId() != menuClickId)
                    navController.navigate(R.id.nav_avisos);
                break;
            case R.id.nav_sign_out:
                signOut();
                break;
        }
        menuClickId = menuitem.getItemId();
        return true;
    }

    private void signOut() {
        Common.selectedAlumno = null;
        Common.currentNombre = null;
        Common.currentCurso = null;

        //SaveSharedPreference.clearUserName(HomeActivity.this);


        SharedPreferences sharedPreferences = HomeActivity.this.getSharedPreferences(
                "Datos", MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();



        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
