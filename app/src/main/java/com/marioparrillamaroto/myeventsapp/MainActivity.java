package com.marioparrillamaroto.myeventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;
import com.marioparrillamaroto.myeventsapp.ui.chats.ChatsActivity;
import com.marioparrillamaroto.myeventsapp.ui.home.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration2;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView =  findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.icon_home, R.id.icon_buscar, R.id.icon_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navContainer);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        NavController navController2 = Navigation.findNavController(this, R.id.navContainer);
        appBarConfiguration2 = new AppBarConfiguration.Builder(navController2.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController2, appBarConfiguration2);

}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_main, menu);
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
            Intent nuevaPantalla = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(nuevaPantalla);
            return true;
        }else if (id == R.id.action_chats) {
            Intent nuevaPantalla = new Intent(MainActivity.this, ChatsActivity.class);
            startActivity(nuevaPantalla);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navContainer);
        return NavigationUI.navigateUp(navController, appBarConfiguration2)
                || super.onSupportNavigateUp();
    }
}