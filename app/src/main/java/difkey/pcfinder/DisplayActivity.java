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

        title.setText("Title" + getIntent().getStringExtra("title"));
        processeur.setText("Processeur " + getIntent().getStringExtra("processeur"));
        annee.setText( "Anne" + getIntent().getStringExtra("annee"));
        ram.setText("Ram" + getIntent().getStringExtra("ram"));
        stockage.setText("Stockage" + getIntent().getStringExtra("stockage"));
        taille.setText("Taille" + getIntent().getStringExtra("taille"));
        poids.setText("Poids" + getIntent().getStringExtra("poids"));
        dimensions.setText("Dimensions" + getIntent().getStringExtra("dimensions"));
        resolution.setText("Resolution" + getIntent().getStringExtra("resolution"));
        marque.setText("Marque" + getIntent().getStringExtra("marque"));
    }
}
