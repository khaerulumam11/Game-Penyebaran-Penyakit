package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import co.id.gamepenyebaranpenyakit.util.Server;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    AppCompatTextView btnLogin, btnRegis;
    ConnectivityManager conMgr;
    String id, email, name, chance, level, score;
    ProgressDialog pDialog;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btn_login);
        btnRegis = findViewById(R.id.btn_register);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString("id", null);
        email = sharedpreferences.getString("email", null);
        name = sharedpreferences.getString("name", null);
        chance = sharedpreferences.getString("chance", null);
        level = sharedpreferences.getString("level", null);
        score = sharedpreferences.getString("score", null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("chance", chance);
            intent.putExtra("level", level);
            startActivity(intent);
            finish();
        }

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistrasiActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(email, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLogin(String email, String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();
        AndroidNetworking.post(Server.ENDPOINT_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password",password)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        System.out.println("Response "+response.toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        try {
                            editor.putString("id", response.getString("id"));
                            editor.putString("name", response.getString("name"));
                            editor.putString("chance", response.getString("chance"));
                            editor.putString("score", response.getString("score"));
                            editor.putString("level", response.getString("level"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editor.putString("email", email);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("email", email);
                        hideDialog();
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Login");
                        hideDialog();
                    }
                });
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}