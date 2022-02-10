package co.id.gamepenyebaranpenyakit.bottomSheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import co.id.gamepenyebaranpenyakit.R;

public class ChoiceAction extends BottomSheetDialog {
    private Context context;
    public TextView totalPenyebaran,poinChanges, skorSosilisasi, skorPreventif, skorFogging,save, txtDate;
    public SeekBar seekSosilisasi, seekPreventif, seekFogging;
    public LinearLayout contentLy;
    ImageView closeButton;
    @SuppressLint("StaticFieldLeak")
    private static ChoiceAction instance;

    public static ChoiceAction getInstance(@NonNull Context context) {
        return instance == null ? new ChoiceAction(context) : instance;
    }

    public ChoiceAction(@NonNull Context context) {
        super(context);
        this.context = context;
        createSort();

    }

    private void createSort() {
        View bottomView = getLayoutInflater().inflate(R.layout.choice_action,null);
        setContentView(bottomView);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomView.getParent());
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        };

         closeButton = (ImageView) bottomView.findViewById(R.id.close);

        txtDate = bottomView.findViewById(R.id.dateSekarang);
        totalPenyebaran = bottomView.findViewById(R.id.caseTotal);
        poinChanges = bottomView.findViewById(R.id.poinChanges);
        skorSosilisasi = bottomView.findViewById(R.id.poinSosialisasi);
        skorPreventif = bottomView.findViewById(R.id.poinPreventif);
        skorFogging = bottomView.findViewById(R.id.poinFogging);
        seekSosilisasi = bottomView.findViewById(R.id.seekbarSosialisasi);
        seekPreventif = bottomView.findViewById(R.id.seekbarPreventif);
        seekFogging = bottomView.findViewById(R.id.seekbarFogging);
        contentLy = bottomView.findViewById(R.id.contentLy);
        save = bottomView.findViewById(R.id.btn_save);
    }


    public TextView getTotalPenyebaran() {
        return totalPenyebaran;
    }

    public void setTotalPenyebaran(TextView totalPenyebaran) {
        this.totalPenyebaran = totalPenyebaran;
    }

    public TextView getPoinChanges() {
        return poinChanges;
    }

    public void setPoinChanges(TextView poinChanges) {
        this.poinChanges = poinChanges;
    }

    public TextView getSkorSosilisasi() {
        return skorSosilisasi;
    }

    public void setSkorSosilisasi(TextView skorSosilisasi) {
        this.skorSosilisasi = skorSosilisasi;
    }

    public TextView getSkorPreventif() {
        return skorPreventif;
    }

    public void setSkorPreventif(TextView skorPreventif) {
        this.skorPreventif = skorPreventif;
    }

    public TextView getSkorFogging() {
        return skorFogging;
    }

    public void setSkorFogging(TextView skorFogging) {
        this.skorFogging = skorFogging;
    }

    public TextView getSave() {
        return save;
    }

    public void setSave(TextView save) {
        this.save = save;
    }

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(TextView txtDate) {
        this.txtDate = txtDate;
    }

    public SeekBar getSeekSosilisasi() {
        return seekSosilisasi;
    }

    public void setSeekSosilisasi(SeekBar seekSosilisasi) {
        this.seekSosilisasi = seekSosilisasi;
    }

    public SeekBar getSeekPreventif() {
        return seekPreventif;
    }

    public void setSeekPreventif(SeekBar seekPreventif) {
        this.seekPreventif = seekPreventif;
    }

    public SeekBar getSeekFogging() {
        return seekFogging;
    }

    public void setSeekFogging(SeekBar seekFogging) {
        this.seekFogging = seekFogging;
    }

    public LinearLayout getContentLy() {
        return contentLy;
    }

    public void setContentLy(LinearLayout contentLy) {
        this.contentLy = contentLy;
    }
}
