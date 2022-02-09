package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

import co.id.gamepenyebaranpenyakit.adapter.ListUserAdapter;
import co.id.gamepenyebaranpenyakit.model.UserModel;
import co.id.gamepenyebaranpenyakit.util.Server;

public class ListScoreUser extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private ListUserAdapter adapter;
    private ArrayList<UserModel.ResultEntity> list = new ArrayList<>();
    ConnectivityManager conMgr;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_score_user);

        mToolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rvListUser);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                getAPIData();
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
        mToolbar.setTitle("Ranking");
        mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new ListUserAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void getAPIData() {
        list.clear();
        AndroidNetworking.post(Server.ENDPOINT_GET_RANKING_USER)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
                        gson = gsonBuilder.create();
                        System.out.println("Response "+response.toString());
                        UserModel courseRespMdl = gson.fromJson(String.valueOf(response), UserModel.class);
                        if (courseRespMdl.getResult() != null) {
                            list.addAll(courseRespMdl.getResult());
                            adapter.setPost(list);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Data");
                    }
                });
    }
}