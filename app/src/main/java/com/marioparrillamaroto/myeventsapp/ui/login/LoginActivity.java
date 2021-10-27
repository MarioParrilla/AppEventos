package com.marioparrillamaroto.myeventsapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.MyEventAppActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private String usernameText, passwordText;
    private Button btnLogin;
    private Switch swGuardarSesion;
    private boolean guardarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        swGuardarSesion = (Switch) findViewById(R.id.SwGuardarSesion);
        LoginModel lg = new LoginModel(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    usernameText = username.getText().toString();
                    passwordText = password.getText().toString();
                    guardarSesion = swGuardarSesion.isChecked();
                    boolean allRigth = false;

                    if (lg.userExists(usernameText, passwordText)==1){
                        allRigth = lg.registerUserLogin(usernameText, guardarSesion);

                        if (allRigth){
                            Intent nuevaPantalla = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(nuevaPantalla);
                        }else throw new Exception();
                    }else Toast.makeText(getApplicationContext(), "Introduce un usuario/contraseña correctos!",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "ERROR al sincronizar datos!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    //Poner que cierre la apliacion al darle al boton de cerrar
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==event.KEYCODE_BACK){
            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
            startActivity(i);
            i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}