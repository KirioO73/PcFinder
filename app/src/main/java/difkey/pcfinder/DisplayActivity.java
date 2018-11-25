package difkey.pcfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private TextView title;
    private TextView processeur;
    private TextView annee;
    private TextView ram;
    private TextView stockage;
    private TextView taille;
    private TextView poids;
    private TextView dimensions;
    private TextView resolution;
    private TextView marque;
    private TextView modele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        title = findViewById(R.id.TitreGENERAL);
        processeur = findViewById(R.id.valuePROCESSEUR);
        annee = findViewById(R.id.valueANNEE);
        ram = findViewById(R.id.valueRAM);
        stockage = findViewById(R.id.valueSTOCKAGE);
        taille = findViewById(R.id.valueTAILLE);
        poids = findViewById(R.id.valuePOIDS);
        dimensions = findViewById(R.id.valueDIMENSIONS);
        resolution = findViewById(R.id.valueRESOLUTION);
        marque = findViewById(R.id.valueMARQUE);
        modele = findViewById(R.id.valueMODELE);

        title.setText(getIntent().getStringExtra("modele"));
        processeur.setText(getIntent().getStringExtra("processeur"));
        annee.setText(getIntent().getStringExtra("annee"));
        ram.setText(getIntent().getStringExtra("ram"));
        stockage.setText(getIntent().getStringExtra("stockage"));
        taille.setText(getIntent().getStringExtra("taille"));
        poids.setText(getIntent().getStringExtra("poids"));
        dimensions.setText(getIntent().getStringExtra("dimensions"));
        resolution.setText(getIntent().getStringExtra("resolution"));
        marque.setText(getIntent().getStringExtra("marque"));
        modele.setText(getIntent().getStringExtra("modele"));
    }
}
