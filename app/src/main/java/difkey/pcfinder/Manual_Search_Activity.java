package difkey.pcfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

import difkey.pcfinder.db.com.recherche;

public class Manual_Search_Activity extends AppCompatActivity {

    private EditText searchInput;
    private Button search;

    private recherche finder;
    private Manual_Search_Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_search);

        finder = new recherche();
        searchInput = findViewById(R.id.searchInput);
        search = findViewById(R.id.lanceSearch);

        searchInput.setOnEditorActionListener( new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    searchInput.clearFocus();
                    finder.searchPcForManualInput(searchInput.getText().toString().toUpperCase(), activity);
                    return true;
                }
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                searchInput.clearFocus();
                finder.searchPcForManualInput(searchInput.getText().toString().toUpperCase(), activity);
            }
        });
    }

    /***
     *  Call when an the object on the DB is find
     *  Finish the OCR activity with success and pass in data the object
     *
     * @param data
     */
    public void finded(Intent data){
        setResult(CommonStatusCodes.SUCCESS, data);
        finish();
    }

    boolean over = false;

    /***
     * Call when there is no success on the DB searching and run on the UI the information
     */
    public void notFind(){
        //Dialog + ask if want to retry
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Create the alert dialog
                if (!over) {
                    over = true;
                    createAlertDialogStop();
                }
            }
        });
    }

    /***
     * Basic alert Dialog to interact with the user when the process is over with no success
     */
    private void createAlertDialogStop() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.not_find_title_man)
                .setMessage(R.string.not_find_message_man)
                .setPositiveButton(R.string.restart_man, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("DIALOG NOTFIND_MAN : ", "retry");
                        over = false;
                    }
                })
                .setNegativeButton(R.string.quit_man, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        Log.e("DIALOG NOTFIND_MAN : ", "quit");
                        setResult(CommonStatusCodes.CANCELED, null);
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
