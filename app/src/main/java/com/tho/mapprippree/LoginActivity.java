package com.tho.mapprippree;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private Button btnLogin;
    private EditText inputUsername;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                    Toast.makeText(getApplicationContext(), username+"  "+password,
                            Toast.LENGTH_LONG).show();
                    checkLogin(username, password);
                    Toast.makeText(getApplicationContext(), "Login ---- 1",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    } //end onCreate


    // Check login
    private void checkLogin(final String username, final String password) {
        Toast.makeText(getApplicationContext(), "Logging in ....",
                Toast.LENGTH_LONG).show();
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        try {
            String url = "http://www.travelapp.pe.hu/";
            //String url = "192.168.1.2:2407/prippree/";
            String paramiter = "index.php?name=" + username + "&password="
                    + password + "&tag=login";
            String resultServerg = new httpLink().getJSONUrl(url + paramiter);
            Toast.makeText(getApplicationContext(), resultServerg,
                    Toast.LENGTH_LONG).show();
            Log.d("LoginWEB_Host", resultServerg);
            for (int i = 0; i <resultServerg.length(); i++) {
                //Log.d("testJS3 & testJS2","JS3 = "+testJS3.charAt(i)+"  JS2 = "+testJS2.charAt(i));
                Log.d("testWEBBBB", "JSWEB = " + resultServerg.charAt(i));

            }

            try{
                JSONObject c = new JSONObject(resultServerg);
                boolean error = c.getBoolean("error");
                String name = c.getString("user_name");
                Toast.makeText(getApplicationContext(), String.valueOf(error),
                        Toast.LENGTH_LONG).show();
                if(!error)
                {
                    Toast.makeText(getApplicationContext(), "Login OK... Hello  "+c.getString("user_name"),
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, Main.class);
                    i.putExtra("name", name);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Loading.....",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception er){
                Toast.makeText(getApplicationContext(),er.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception edb) {
            Toast.makeText(getApplicationContext(), edb.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }// end checklogin
    public void reg(View v) {
        Intent i = new Intent(LoginActivity.this, RegisActivity.class);
        startActivity(i);

    }
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.drawable.icon);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}// end class



