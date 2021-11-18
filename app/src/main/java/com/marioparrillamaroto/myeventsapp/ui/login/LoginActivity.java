package com.marioparrillamaroto.myeventsapp.ui.login;

import androidx.annotation.NonNull;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.MyEventAppActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.CoreFuntions;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private String usernameText, passwordText;
    private Button btnLogin;
    private Switch swGuardarSesion;
    private boolean guardarSesion;
    private CheckBox captcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        swGuardarSesion = (Switch) findViewById(R.id.SwGuardarSesion);
        LoginModel lg = new LoginModel(getApplicationContext());
        captcha = (CheckBox)findViewById(R.id.Chbcaptcha);

        captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoreFuntions.checkGatcha(getApplicationContext(), captcha);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText = username.getText().toString().trim();
                passwordText = password.getText().toString();
                if (usernameText.length()>0 && passwordText.length()>0){
                    if (captcha.isChecked()){
                        try {
                            guardarSesion = swGuardarSesion.isChecked();
                            boolean allRigth = false;

                            if (lg.userExists(usernameText, passwordText)){
                                allRigth = lg.registerUserLogin(usernameText, guardarSesion);
                                if (allRigth){
                                    Intent nuevaPantalla = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(nuevaPantalla);
                                }else throw new Exception();
                            }else Toast.makeText(getApplicationContext(), "Introduce un usuario/contraseña correctos!",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "ERROR al sincronizar datos!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Completa el captcha", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Completa el login!", Toast.LENGTH_SHORT).show();
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