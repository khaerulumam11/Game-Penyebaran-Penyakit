package co.id.gamepenyebaranpenyakit.bottomSheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import co.id.gamepenyebaranpenyakit.R;

public class ChoiceAction extends BottomSheetDialog {
    private Context context;
    public TextView totalPenyebaran,poinChanges, skorSosilisasi, skorPreventif, skorFogging,save;
    public Button  plusSosilisasi, plusPreventif, plusFogging,minusSosilisasi, minusPreventif, minusFogging;

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

        ImageView closeButton = (ImageView) bottomView.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        totalPenyebaran = bottomView.findViewById(R.id.totalPenyebaran);
        poinChanges = bottomView.findViewById(R.id.poinChanges);
        skorSosilisasi = bottomView.findViewById(R.id.skorSosilisasi);
        skorPreventif = bottomView.findViewById(R.id.skorPreventif);
        skorFogging = bottomView.findViewById(R.id.skorFogging);
        plusSosilisasi = bottomView.findViewById(R.id.plusSosialisasi);
        plusPreventif = bottomView.findViewById(R.id.plusPreventif);
        plusFogging = bottomView.findViewById(R.id.plusFogging);
        minusSosilisasi = bottomView.findViewById(R.id.minusSosilisasi);
        minusPreventif = bottomView.findViewById(R.id.minusPreventif);
        minusFogging = bottomView.findViewById(R.id.minusFogging);
        save = bottomView.findViewById(R.id.save);
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

    public Button getPlusSosilisasi() {
        return plusSosilisasi;
    }

    public void setPlusSosilisasi(Button plusSosilisasi) {
        this.plusSosilisasi = plusSosilisasi;
    }

    public Button getPlusPreventif() {
        return plusPreventif;
    }

    public void setPlusPreventif(Button plusPreventif) {
        this.plusPreventif = plusPreventif;
    }

    public Button getPlusFogging() {
        return plusFogging;
    }

    public void setPlusFogging(Button plusFogging) {
        this.plusFogging = plusFogging;
    }

    public Button getMinusSosilisasi() {
        return minusSosilisasi;
    }

    public void setMinusSosilisasi(Button minusSosilisasi) {
        this.minusSosilisasi = minusSosilisasi;
    }

    public Button getMinusPreventif() {
        return minusPreventif;
    }

    public void setMinusPreventif(Button minusPreventif) {
        this.minusPreventif = minusPreventif;
    }

    public Button getMinusFogging() {
        return minusFogging;
    }

    public void setMinusFogging(Button minusFogging) {
        this.minusFogging = minusFogging;
    }
}
