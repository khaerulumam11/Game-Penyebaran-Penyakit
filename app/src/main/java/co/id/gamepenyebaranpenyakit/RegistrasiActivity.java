package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import co.id.gamepenyebaranpenyakit.model.CityModel;
import co.id.gamepenyebaranpenyakit.model.UserModel;
import co.id.gamepenyebaranpenyakit.util.Server;

public class RegistrasiActivity extends AppCompatActivity {
    EditText etEmail, etPassword,etName, etDate;
    AppCompatTextView btnLogin, btnRegis;
    BetterSpinner atCities;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    private ArrayList<CityModel.ResultEntity> list = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    private Gson gson;
    private String idCity="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

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
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDateBirth);
        btnLogin = findViewById(R.id.btn_login);
        btnRegis = findViewById(R.id.btn_register);
        atCities = findViewById(R.id.etDomicile);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        loadDateCities();

//        atCities.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().isEmpty()){
//                    loadDateCities();
//                } else {
//                    loadDateCitiesByName(editable.toString());
//                }
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0 && name.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        register(email, password, name);
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

    private Calendar calendar;
    private int year, month, days;

    private void showDatePicker() {
        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                etDate.setText(sdf.format(calendar.getTime()));
            }
        }, year, month,days);
        datePickerDialog.show();
    }

    private void loadDateCitiesByName(String name) {
        list.clear();
        listName.clear();
        AndroidNetworking.post(Server.ENDPOINT_GET_CITIES_BY_NAME+name)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
                        gson = gsonBuilder.create();
                        System.out.println("Response "+response.toString());
                        CityModel courseRespMdl = gson.fromJson(String.valueOf(response), CityModel.class);
                        if (courseRespMdl.getResult() != null) {
                            list.addAll(courseRespMdl.getResult());
                            for (int a =0; a < list.size(); a++){
                                listName.add(list.get(a).getCity());
                            }
                            buildAdapter();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Data");
                    }
                });



    }

    private void loadDateCities() {
        list.clear();
        listName.clear();
        AndroidNetworking.post(Server.ENDPOINT_GET_CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
                        gson = gsonBuilder.create();
                        System.out.println("Response "+response.toString());
                        CityModel courseRespMdl = gson.fromJson(String.valueOf(response), CityModel.class);
                        if (courseRespMdl.getResult() != null) {
                            list.addAll(courseRespMdl.getResult());
                            for (int a =0; a < list.size(); a++){
                                listName.add(list.get(a).getCity());
                            }
                            buildAdapter();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Data");
                    }
                });



    }

    private void buildAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (RegistrasiActivity.this, android.R.layout.select_dialog_item, listName);
        //Getting the instance of AutoCompleteTextView
        atCities.setThreshold(1);//will start working from first character
        atCities.setText("Choose City");
        atCities.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//        atCities.showDropDown();
        atCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                idCity = list.get(i).getId();
            }
        });
        atCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atCities.showDropDown();
            }
        });
    }

    private void register(String email, String password, String name) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");
        showDialog();
        AndroidNetworking.post(Server.ENDPOINT_REGISTER)
                .addBodyParameter("email", email)
                .addBodyParameter("password",password)
                .addBodyParameter("nama",name)
                .addBodyParameter("dateBirth",etDate.getText().toString())
                .addBodyParameter("domicile",atCities.getText().toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        System.out.println("Response "+response.toString());
                        Toast.makeText(RegistrasiActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                        hideDialog();
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr " + anError.getMessage());
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