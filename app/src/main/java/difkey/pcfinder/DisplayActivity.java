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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        title = findViewById(R.id.title);
        processeur = findViewById(R.id.processor);
        annee = findViewById(R.id.annee);
        ram = findViewById(R.id.ram);
        stockage = findViewById(R.id.stockage);
        taille = findViewById(R.id.taille);
        poids = findViewById(R.id.poids);
        dimensions = findViewById(R.id.dimensions);
        resolution = findViewById(R.id.resolution);
        marque = findViewById(R.id.marque);

        title.setText(getIntent().getStringExtra("title"));
        processeur.setText(getIntent().getStringExtra("processeur"));
        annee.setText(getIntent().getStringExtra("annee"));
        ram.setText(getIntent().getStringExtra("ram"));
        stockage.setText(getIntent().getStringExtra("stockage"));
        taille.setText(getIntent().getStringExtra("taille"));
        poids.setText(getIntent().getStringExtra("poids"));
        dimensions.setText(getIntent().getStringExtra("dimensions"));
        resolution.setText(getIntent().getStringExtra("resolution"));
        marque.setText(getIntent().getStringExtra("marque"));
    }
}
