package com.marioparrillamaroto.myeventsapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marioparrillamaroto.myeventsapp.MainActivity;
import com.marioparrillamaroto.myeventsapp.R;
import com.marioparrillamaroto.myeventsapp.core.FunctionsDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private String usernameText, passwordText;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        LoginModel lg = new LoginModel(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    usernameText = username.getText().toString();
                    passwordText = password.getText().toString();
                    boolean allRigth = false;

                    if (lg.userExists(usernameText, passwordText)==1){
                        allRigth = lg.registerUserLogin(usernameText);
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
}