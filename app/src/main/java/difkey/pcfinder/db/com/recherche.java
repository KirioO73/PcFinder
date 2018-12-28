package difkey.pcfinder.db.com;

import android.content.Intent;
import android.support.annotation.NonNull;
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

    private FirebaseDatabase database;
    private DatabaseReference pcRef;

    private Intent data;

    public recherche(){
        database = FirebaseDatabase.getInstance();
        pcRef = database.getReference();
        data = new Intent();
    }

    public void searchPcforOCR(final String ref, final OcrCaptureActivity motherActivity){
        Log.e("OPEN_Recherche", ref);

        //Check size of the ref to avoid many useless DB's request
        if (ref.length() < 3) return;

        pcRef = FirebaseDatabase.getInstance().getReference().child("PC");
        pcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                model_pc PC = (dataSnapshot.child(ref).getValue(model_pc.class));
                if (PC != null){
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
                    Log.w("INFO", "Nothing Found For" + ref);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException());
            }
        });

    }

    public void searchPcForManualInput(final String ref, final Manual_Search_Activity motherActivity){
        Log.e("OPEN_Recherche", ref);

        if (ref.length() < 3) return;

        pcRef = FirebaseDatabase.getInstance().getReference().child("PC");
        pcRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                model_pc PC = (dataSnapshot.child(ref).getValue(model_pc.class));
                if (PC != null){

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
                    Log.w("INFO", "Nothing Found For" + ref);
                    motherActivity.notFind();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERR", "Failed to read value.", error.toException());
            }
        });
    }
}
