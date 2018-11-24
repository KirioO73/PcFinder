package difkey.pcfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

import difkey.pcfinder.db.com.recherche;
import difkey.pcfinder.ocr.OcrCaptureActivity;


public class MainActivity extends AppCompatActivity {

    TextView res;

    private Button B_recoImg;
    private Button B_recoOcr;
    private CompoundButton autoFocus;
    private CompoundButton useFlash;

    private static final int RC_OCR_CAPTURE = 9003;

    private recherche finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finder = new recherche();

        B_recoImg = findViewById(R.id.StartImgRecognize);
        B_recoOcr = findViewById(R.id.StartOCR);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        res = findViewById(R.id.Recuptext);
        res.setText("");

        B_recoOcr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // launch Ocr capture activity.
                Intent intent = new Intent(view.getContext(), OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });

        B_recoImg.setOnClickListener(new View.OnClickListener() {
            int nb = 0;
            @Override
            public void onClick(View view) {
                //finder.recherchePc((ref + (nb%3 + 1)),);
                //res.setText(finder.recherchePc(ref + (nb%3 + 1)));
                Log.d("Info", "Value is: " + res.getText());
                nb ++;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Log.e("MAIN RETOUR DATA", "annee : " + data.getStringExtra("annee") + " --------------------------------------------- ");
                    data.setClass(this, DisplayActivity.class);
                    startActivity(data);
                    Log.d("TTG", "Something finded");
                } else {
                    Log.d("TTE", "No Text captured, intent data is null");
                }
            }
            else {
                Log.d("TTE", "No Success");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
