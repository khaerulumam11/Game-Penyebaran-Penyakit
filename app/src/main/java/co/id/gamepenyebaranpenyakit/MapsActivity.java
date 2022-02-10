package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import co.id.gamepenyebaranpenyakit.bottomSheet.ChoiceAction;
import co.id.gamepenyebaranpenyakit.model.CaseModel;
import co.id.gamepenyebaranpenyakit.model.UserModel;
import co.id.gamepenyebaranpenyakit.util.Server;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolygonClickListener {

    ChoiceAction choiceAction;
    private int changesPoin = 0;
    private int sosialisasiPoin =0;
    private  int preventifPoin =0;
    private  int foggingPoin =0;
    private  int totalSkor =0;
    private Handler mHandler;
    private Gson gson;
    private List<CaseModel.ResultEntity> list = new ArrayList<>();

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_RED_ARGB = 0xffff0000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private Polygon polygon1,polygon2,polygon3;
    private ImageView imgBack;
    SharedPreferences sharedpreferences;
    private TextView txtLokasi;
    private LinearLayout choiceLy, toolbarLy;
    public static final String my_shared_preferences = "my_shared_preferences";
    private String polygonSet="";

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
    MediaPlayer ring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        
        imgBack = findViewById(R.id.back);
        toolbarLy = findViewById(R.id.toolbarLy);
        choiceLy = findViewById(R.id.choiceLy);
        txtLokasi = findViewById(R.id.txtLocation);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        ring= MediaPlayer.create(MapsActivity.this,R.raw.backsound);
        ring.start();
        ring.setLooping(true);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        choiceLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygonSet.isEmpty()){
                    Toast.makeText(MapsActivity.this, "Please choose affected area", Toast.LENGTH_SHORT).show();
                }
            }
        });

        choiceLy.getBackground().setAlpha(220);
        toolbarLy.getBackground().setAlpha(200);
        choiceAction = new ChoiceAction(this);
        this.mHandler = new Handler();

//        this.mHandler.postDelayed(m_Runnable,2000);
    }

//    private final Runnable m_Runnable = new Runnable()
//    {
//        List<PatternItem> pattern = null;
//        int strokeColor = COLOR_BLACK_ARGB;
//        int fillColor = COLOR_WHITE_ARGB;
//        public void run()
//
//        {
////            Toast.makeText(MapsActivity.this,"in runnable",Toast.LENGTH_SHORT).show();
//            if (totalSkor > 70){
//                pattern = PATTERN_POLYGON_ALPHA;
//                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_GREEN_ARGB;
//            }
//            else if (totalSkor > 60 && totalSkor <=70){
//                pattern = PATTERN_POLYGON_ALPHA;
//                strokeColor = COLOR_GREEN_ARGB;
//                fillColor = COLOR_GREEN_ARGB;
//            } else if (totalSkor > 30 && totalSkor <= 60){
//                pattern = PATTERN_POLYGON_ALPHA;
//                strokeColor = COLOR_ORANGE_ARGB;
//                fillColor = COLOR_ORANGE_ARGB;
//            } else {
//                pattern = PATTERN_POLYGON_ALPHA;
//                strokeColor = COLOR_RED_ARGB;
//                fillColor = COLOR_RED_ARGB;
//            }
//            polygon1.setStrokePattern(pattern);
//            polygon1.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
//            polygon1.setStrokeColor(strokeColor);
//            polygon1.setFillColor(fillColor);
//
//            polygon2.setStrokePattern(pattern);
//            polygon2.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
//            polygon2.setStrokeColor(strokeColor);
//            polygon2.setFillColor(fillColor);
//
//            polygon3.setStrokePattern(pattern);
//            polygon3.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
//            polygon3.setStrokeColor(strokeColor);
//            polygon3.setFillColor(fillColor);
//            MapsActivity.this.mHandler.postDelayed(m_Runnable, 2000);
//        }
//
//    };//runnable

    @Override
    protected void onPause() {
        super.onPause();
//        mHandler.removeCallbacks(m_Runnable);
        ring.pause();
        ring.setLooping(false);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {
        System.out.println("Name Polygon "+polygon.getTag());
        if (polygon.getTag().equals("alpha")){
            txtLokasi.setText("Pandaan, Malang Kabupaten");
            showAction(125, polygon.getTag().toString());
            choiceAction.contentLy.getBackground().setAlpha(200);
        } else if (polygon.getTag().equals("beta")){
            txtLokasi.setText("Wonokerto, Malang Kabupaten");
            showAction(90, polygon.getTag().toString());
            choiceAction.contentLy.getBackground().setAlpha(200);
        } else if (polygon.getTag().equals("teta")){
            txtLokasi.setText("Kanjuruhan, Malang Kabupaten");
            Toast.makeText(MapsActivity.this, "This area is not affected dengue virus", Toast.LENGTH_SHORT).show();
        }
        choiceLy.setVisibility(View.GONE);

    }

    private void showAction(int kasus, String daerah) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        choiceAction.txtDate.setText("Date "+sdf.format(now));
        changesPoin = Integer.parseInt(getIntent().getStringExtra("chance"));
        choiceAction.totalPenyebaran.setText("Total Kasus : "+kasus);
        choiceAction.poinChanges.setText(""+changesPoin);
        choiceAction.seekSosilisasi.setMax(changesPoin);
        choiceAction.seekSosilisasi.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valueProgress =0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               valueProgress =i;
               choiceAction.skorSosilisasi.setText(String.valueOf(valueProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choiceAction.seekSosilisasi.getProgress() > changesPoin){
                    Toast.makeText(MapsActivity.this, "You've used up all the change points", Toast.LENGTH_SHORT).show();
                } else {
                    sosialisasiPoin += choiceAction.seekSosilisasi.getProgress();
                    changesPoin -= choiceAction.seekSosilisasi.getProgress();
                    choiceAction.poinChanges.setText("" + changesPoin);
                }
            }
        });

        choiceAction.seekPreventif.setMax(changesPoin);
        choiceAction.seekPreventif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressValue =0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue = i;
                choiceAction.skorPreventif.setText(String.valueOf(progressValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choiceAction.seekPreventif.getProgress() > changesPoin){
                    Toast.makeText(MapsActivity.this, "You've used up all the change points", Toast.LENGTH_SHORT).show();
                } else {
                    preventifPoin += choiceAction.seekPreventif.getProgress();
                    changesPoin -= choiceAction.seekPreventif.getProgress();
                    choiceAction.poinChanges.setText("" + changesPoin);

                }
            }
        });


        choiceAction.seekFogging.setMax(changesPoin);
        choiceAction.seekFogging.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int valueProgress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                valueProgress = i;
                choiceAction.skorFogging.setText(String.valueOf(valueProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choiceAction.seekFogging.getProgress() > changesPoin){
                    Toast.makeText(MapsActivity.this, "You've used up all the change points", Toast.LENGTH_SHORT).show();
                } else {
                    foggingPoin += choiceAction.seekFogging.getProgress();
                    changesPoin -= choiceAction.seekFogging.getProgress();
                    choiceAction.skorFogging.setText(String.valueOf(choiceAction.seekFogging.getProgress()));
                    choiceAction.poinChanges.setText("" + changesPoin);
                }
            }
        });


        choiceAction.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int maxSosialiasi=0;
                int maxPreventif =0;
                int maxFogging =0;
                double persentaseSosialisasi = 0.0;
                double persentasePreventif = 0.0;
                double persentaseFogging = 0.0;
                if (daerah.equalsIgnoreCase("alpha")){
                    persentaseSosialisasi = 0.3;
                    maxSosialiasi = 3;
                    persentasePreventif = 0.3;
                    maxPreventif =3;
                    persentaseFogging = 0.4;
                    maxFogging = 4;
                } else {
                    persentaseSosialisasi = 0.5;
                    maxSosialiasi = 5;
                    persentasePreventif = 0.4;
                    maxPreventif =4;
                    persentaseFogging = 0.1;
                    maxFogging = 1;
                }
                    if (sosialisasiPoin > maxSosialiasi){
                        int hitung = maxSosialiasi * 10;
                        totalSkor+=hitung;
                    } else if (sosialisasiPoin == maxSosialiasi){
                        int hitung = maxSosialiasi * 10;
                        totalSkor+=hitung;
                    } else {
                        int hitung = (int)(persentaseSosialisasi * Double.parseDouble(choiceAction.skorSosilisasi.getText().toString()));
                        totalSkor+=hitung;
                    }

                if (preventifPoin > maxPreventif){
                    int hitung = maxPreventif * 10;
                    totalSkor+=hitung;
                } else if (preventifPoin == maxPreventif){
                    int hitung = maxPreventif * 10;
                    totalSkor+=hitung;
                } else {
                    int hitung = (int)(persentasePreventif * Double.parseDouble(choiceAction.skorPreventif.getText().toString()));
                    totalSkor+=hitung;
                }

                if (foggingPoin > maxFogging){
                    int hitung = maxFogging * 10;
                    totalSkor+=hitung;
                } else if (foggingPoin == maxFogging){
                    int hitung = maxFogging * 10;
                    totalSkor+=hitung;
                } else {
                    int hitung = (int)(persentaseFogging * Double.parseDouble(choiceAction.skorFogging.getText().toString()));
                    totalSkor+=hitung;
                }
                    System.out.println("Total Skor "+totalSkor);
                if (totalSkor == 100){
                    int chance = Integer.parseInt(getIntent().getStringExtra("chance")) + 10;
                    updateDataScore(totalSkor, chance);
                } else {
                    int chance = Integer.parseInt(getIntent().getStringExtra("chance")) + 3;
                    updateDataScore(totalSkor, chance);
                }
                }
        });
        choiceAction.setCanceledOnTouchOutside(false);
        choiceAction.show();
    }

    private void updateDataScore(int totalSkor, int chance) {
        AndroidNetworking.post(Server.ENDPOINT_UPDATE_SCORE+getIntent().getStringExtra("id"))
                .addBodyParameter("chance", String.valueOf(chance))
                .addBodyParameter("score",String.valueOf(totalSkor))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        try {
                            if (response.getString("message").equalsIgnoreCase("success")) {
                                System.out.println("Response " + response.toString());
                                getDetailUser();
                            } else {
                                Toast.makeText(MapsActivity.this, "Update Score Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr " + anError.getMessage());
                    }
                });
    }

    private void getDetailUser() {
        AndroidNetworking.post(Server.ENDPOINT_GET_DETAIL_USER+getIntent().getStringExtra("id"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        System.out.println("Response "+response.toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        try {
                            editor.putString("chance", response.getString("chance"));
                            editor.putString("score", response.getString("score"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        editor.commit();
                        System.out.println("Chance Now : "+ sharedpreferences.getString("chance", null));
                        // Memanggil main activity
                        choiceAction.hide();
                        choiceLy.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Get Data");
                    }
                });
    }


    private void stylePolygon(Polygon polygon) {
        polygon1 = polygon;
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_RED_ARGB;
                fillColor = COLOR_RED_ARGB;

                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;

            case "teta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_GREEN_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        getDataCase();

        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-8.154984,112.588902),
                        new LatLng(-8.183191,112.549935),
                        new LatLng(-8.239428,112.636624),
                        new LatLng(-8.205958, 112.649842)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
        // [END maps_poly_activity_add_polygon]
        // Style the polygon.
        stylePolygon(polygon1);

        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-8.216462, 112.588504),
                        new LatLng(-8.230874, 112.605279),
                        new LatLng(-8.2603437,112.6042053),
                        new LatLng(-8.252492, 112.578939)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon2.setTag("beta");
        // [END maps_poly_activity_add_polygon]
        // Style the polygon.
        stylePolygon(polygon2);

        Polygon polygon3 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-8.135891, 112.549022),
                        new LatLng(-8.147829, 112.572496),
                        new LatLng(-8.156325, 112.554043),
                        new LatLng(-8.145280, 112.541726)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon3.setTag("teta");
        // [END maps_poly_activity_add_polygon]
        // Style the polygon.
        stylePolygon(polygon3);

//        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
//                .clickable(true)
//                .add(
//                        new LatLng(-31.673, 128.892),
//                        new LatLng(-31.952, 115.857),
//                        new LatLng(-17.785, 122.258),
//                        new LatLng(-12.4258, 130.7932)));
//        polygon2.setTag("beta");
//        stylePolygon(polygon2);
        // [END_EXCLUDE]

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-8.230874, 112.605279), 12));

        // Set listeners for click events.
        googleMap.setOnPolygonClickListener(this);
    }

    private void getDataCase() {
        list.clear();
        AndroidNetworking.post(Server.ENDPOINT_GET_CASE)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        setIsLoading(false);
                        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
                        gson = gsonBuilder.create();
                        System.out.println("Response "+response.toString());
                        CaseModel courseRespMdl = gson.fromJson(String.valueOf(response), CaseModel.class);
                        if (courseRespMdl.getResult() != null) {
                            list.addAll(courseRespMdl.getResult());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Erorr Data");
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        ring.start();
        ring.setLooping(true);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ring.stop();
        ring.setLooping(false);
    }

}