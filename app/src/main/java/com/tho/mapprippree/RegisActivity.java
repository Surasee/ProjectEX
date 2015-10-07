package com.tho.mapprippree;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;



public class RegisActivity extends Activity {
    // private static final String TAG = Register.class.getSimpleName();
    private String array_spinner[];
    private Spinner s;
    private Button btnRegister;
    private Button btnLinkToLogin;
    private Button btnClear;
    private EditText inputUserName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputLanguage;
    private EditText inputAge;
    private EditText inputInterest;
    private EditText inputProvince;
    private RadioGroup inputGender;
    private RadioButton inputGenderBT;
    private SessionManager session;
    private String keyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_-0123456789";
    private char[] keyCheck = keyString.toCharArray();

    // private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        inputUserName = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputLanguage = (EditText) findViewById(R.id.language);
        inputAge = (EditText) findViewById(R.id.age);
        inputInterest = (EditText) findViewById(R.id.interest);
        inputProvince = (EditText) findViewById(R.id.province);
        inputGender = (RadioGroup) findViewById(R.id.gender);

        btnRegister = (Button) findViewById(R.id.bt_regis);
        btnLinkToLogin = (Button) findViewById(R.id.bt_back);
        btnClear = (Button) findViewById(R.id.bt_clear);

        array_spinner=new String[5];
        array_spinner[0]="option 1";
        array_spinner[1]="option 2";
        array_spinner[2]="option 3";
        array_spinner[3]="option 4";
        array_spinner[4]="option 5";
        s = (Spinner) findViewById(R.id.spinner01);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);


        // int selectedId = inputGender.getCheckedRadioButtonId();
        // inputGenderBT = (RadioButton) findViewById(selectedId);

        // Progress dialog
        // pDialog = new ProgressDialog(this);
        // pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        // db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisActivity.this, Main.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String username = inputUserName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String language = inputLanguage.getText().toString();
                String interest = inputInterest.getText().toString();
                String age = inputAge.getText().toString();
                String province = inputProvince.getText().toString();
                inputGenderBT = (RadioButton) findViewById(inputGender
                        .getCheckedRadioButtonId());
                String gender = inputGenderBT.getText().toString();

                if (!username.isEmpty() && !email.isEmpty()
                        && !password.isEmpty() && !language.isEmpty()
                        && !interest.isEmpty() && !age.isEmpty()
                        && !province.isEmpty() && !gender.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please Wait!",
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(
                            getApplicationContext(),
                            "1 username : " + username + "/n2 password :"
                                    + password + "/n3 email : " + email
                                    + "/n4 age : " + age + "/n5 language : "
                                    + language + "/n6 interest : " + interest
                                    + "/n7 province : " + province
                                    + "/n8 gender : " + gender,
                            Toast.LENGTH_LONG).show();
                    registerUser(username, email, password, age, language,
                            interest, province, gender);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        // Clear Text
        btnClear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                inputUserName.setText(null);
                inputPassword.setText(null);
                inputEmail.setText(null);
                inputAge.setText(null);
                inputLanguage.setText(null);
                inputInterest.setText(null);
                inputProvince.setText(null);
                inputGender.check(R.id.m);
            }
        });
//Check Input
        inputUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                char[] user = inputUserName.getText().toString().toUpperCase().toCharArray();
                int X = new checkInput().strCheck(user);//Verify input
                Log.d("Check X","X = "+X);
                if (X == user.length) {
                    Toast.makeText(getApplicationContext(), "Username Correct...", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username Incorrect...", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), "Username Method", Toast.LENGTH_LONG).show();
            }
        });
        ///
    }//end Oncreate.

    // ///////////////////////////////

    // //////////////////////////////

    // /////////////////////////////


    // ////////////////////////////////////
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email,
                              final String password, final String age, final String language,
                              final String interest, final String province, final String gender) {

        Toast.makeText(getApplicationContext(), "REGISTER TEST 1",
                Toast.LENGTH_LONG).show();

        // Tag used to cancel the request
        // String tag_string_req = "req_register";

        // /////////////////////////////



        try {
            String url = "http://www.travelapp.pe.hu/";//create class ip config**
            String paramiter = "index.php?name=" + name + "&password="
                    + password + "&age=" + age + "&email=" + email
                    + "&province=" + province + "&language=" + language
                    + "&gender=" + gender + "&interest=" + interest
                    + "&tag=register";
            String resultServerg = new httpLink().getHttpGet(url + paramiter);
            Toast.makeText(getApplicationContext(), resultServerg,
                    Toast.LENGTH_LONG).show();

        } catch (Exception edb) {
            Toast.makeText(getApplicationContext(), edb.toString(),
                    Toast.LENGTH_LONG).show();
        }


        // pDialog.setMessage("Registering ...");
        // showDialog();

    }
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Back");
        dialog.setIcon(R.drawable.icon);
        dialog.setCancelable(true);
        dialog.setMessage("Back to Login?");
        dialog.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        dialog.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

}