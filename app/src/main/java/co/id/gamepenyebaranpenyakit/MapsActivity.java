package co.id.gamepenyebaranpenyakit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import co.id.gamepenyebaranpenyakit.bottomSheet.ChoiceAction;
import co.id.gamepenyebaranpenyakit.model.CaseModel;
import co.id.gamepenyebaranpenyakit.model.UserModel;
import co.id.gamepenyebaranpenyakit.util.Server;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolygonClickListener {

    ChoiceAction choiceAction;
    private int changesPoin = 10;
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

    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

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
        finish();

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

        showAction(98);

    }

    private void showAction(int kasus) {
        choiceAction.totalPenyebaran.setText("Total Kasus : "+kasus);
        choiceAction.poinChanges.setText("Poin Changes : "+changesPoin);
        choiceAction.plusSosilisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin <=0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (sosialisasiPoin >= 2) {
                        Toast.makeText(MapsActivity.this, "Maksimal untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        sosialisasiPoin += 1;
                        changesPoin -= 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorSosilisasi.setText(String.valueOf(sosialisasiPoin));
                    }
                }
            }
        });
        choiceAction.minusSosilisasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin < 0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (sosialisasiPoin <= 0) {
                        Toast.makeText(MapsActivity.this, "Minimum untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        sosialisasiPoin -= 1;
                        changesPoin += 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorSosilisasi.setText(String.valueOf(sosialisasiPoin));
                    }
                }
            }
        });
        choiceAction.plusPreventif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin <=0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (preventifPoin >= 2) {
                        Toast.makeText(MapsActivity.this, "Maksimal untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        preventifPoin += 1;
                        changesPoin -= 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorPreventif.setText(String.valueOf(preventifPoin));
                    }
                }
            }
        });
        choiceAction.minusPreventif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin <0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (preventifPoin <= 0) {
                        Toast.makeText(MapsActivity.this, "Minimum untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        preventifPoin -= 1;
                        changesPoin += 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorPreventif.setText(String.valueOf(preventifPoin));
                    }
                }
            }
        });
        choiceAction.plusFogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin <=0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (foggingPoin >= 2) {
                        Toast.makeText(MapsActivity.this, "Maksimal untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        foggingPoin += 1;
                        changesPoin -= 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorFogging.setText(String.valueOf(foggingPoin));
                    }
                }
            }
        });
        choiceAction.minusFogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changesPoin <0){
                    Toast.makeText(MapsActivity.this, "Anda sudah menggunakan semua changes poin", Toast.LENGTH_SHORT).show();
                } else {
                    if (foggingPoin <= 0) {
                        Toast.makeText(MapsActivity.this, "Minimum untuk aksi ini", Toast.LENGTH_SHORT).show();
                    } else {
                        foggingPoin -= 1;
                        changesPoin += 1;
                        choiceAction.poinChanges.setText("Poin Changes : " + changesPoin);
                        choiceAction.skorFogging.setText(String.valueOf(foggingPoin));
                    }
                }
            }
        });

        choiceAction.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sosialisasiPoin <= 0){
                    Toast.makeText(MapsActivity.this, "Anda harus memasukkan poin untuk sosialisasi", Toast.LENGTH_SHORT).show();
                } else if (foggingPoin <=0){
                    Toast.makeText(MapsActivity.this, "Anda harus memasukkan poin untuk fogging", Toast.LENGTH_SHORT).show();
                } else if (preventifPoin <=0){
                    Toast.makeText(MapsActivity.this, "Anda harus memasukkan poin untuk preventif", Toast.LENGTH_SHORT).show();
                } else {
                    if (sosialisasiPoin == 1){
                        totalSkor+=5;
                    } else if (sosialisasiPoin == 2){
                        totalSkor+=10;
                    }

                    if (preventifPoin == 1){
                        totalSkor+=10;
                    } else if (preventifPoin == 2){
                        totalSkor+=20;
                    }

                    if (foggingPoin == 1){
                        totalSkor+=30;
                    } else if (foggingPoin == 2){
                        totalSkor+=40;
                    }
                    System.out.println("Total Skor "+totalSkor);
                    choiceAction.hide();

                }
            }
        });
        choiceAction.setCanceledOnTouchOutside(true);
        choiceAction.show();
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
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.9784695,112.5617425), 10));

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
}