package difkey.pcfinder.db.com;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import difkey.pcfinder.modelFirebase.model_pc;
import difkey.pcfinder.ocr.OcrCaptureActivity;
import difkey.pcfinder.Manual_Search_Activity;

public class recherche {

    FirebaseDatabase database;
    DatabaseReference pcRef;
    String retourRecherchePC = null;
    public model_pc retourPC = null;
    private Intent data;

    public recherche(){
        database = FirebaseDatabase.getInstance();
        pcRef = FirebaseDatabase.getInstance().getReference();
        data = new Intent();
    }

    public void recherchePcOcr(final String ref, final OcrCaptureActivity motherActivity){
        Log.e("OPEN_Recherche", ref);
        pcRef = FirebaseDatabase.getInstance().getReference().child("PC");
        pcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                model_pc PC = (dataSnapshot.child(ref).getValue(model_pc.class));
                if (PC != null){
                    retourRecherchePC = (PC.modele + PC.annee + PC.processeur);
                    retourPC = PC;
                    Log.e("SERV", retourRecherchePC + " Ok -------------------------- ");

                    data.putExtra("modele", PC.modele);
                    data.putExtra("processeur", PC.processeur);
                    data.putExtra("annee", PC.annee);
                    data.putExtra("ram", PC.ram);
                    data.putExtra("marque", PC.marque);
                    data.putExtra("poids", PC.poids);
                    data.putExtra("resolution", PC.resolution);
                    data.putExtra("stockage", PC.stockage);
                    data.putExtra("taille", PC.taille);
                    data.putExtra("dimensions", PC.dimensions);

                    motherActivity.finded(data);
                }
                else{
                    retourRecherchePC = "nothing found";
                    retourPC = new model_pc();
                    retourPC.modele = "nothing found";
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException());
            }
        });

    }

    public String recherchePcManual(final String ref, final Manual_Search_Activity motherActivity){
        Log.e("OPEN_Recherche", ref);

        pcRef = FirebaseDatabase.getInstance().getReference().child("PC");
        pcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                model_pc PC = (dataSnapshot.child(ref).getValue(model_pc.class));
                if (PC != null){
                    retourRecherchePC = (PC.modele + PC.annee + PC.processeur);
                    retourPC = PC;
                    Log.e("SERV", retourRecherchePC + " Ok -------------------------- ");
                    //Log.e("TEST RETOUR DATA", "annee : " + PC.annee + " --------------------------------------------- ");

                    data.putExtra("modele", PC.modele);
                    data.putExtra("processeur", PC.processeur);
                    data.putExtra("annee", PC.annee);
                    data.putExtra("ram", PC.ram);
                    data.putExtra("marque", PC.marque);
                    data.putExtra("poids", PC.poids);
                    data.putExtra("resolution", PC.resolution);
                    data.putExtra("stockage", PC.stockage);
                    data.putExtra("taille", PC.taille);
                    data.putExtra("dimensions", PC.dimensions);
                    //Log.e("recherche RETOUR DATA", "annee : " + data.getStringExtra("annee") + " --------------------------------------------- ");

                    motherActivity.finded(data);

                }
                else{
                    motherActivity.notFind();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException());
            }
        });
        return retourRecherchePC;
    }
}
