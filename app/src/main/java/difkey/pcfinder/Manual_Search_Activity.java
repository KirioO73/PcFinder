package difkey.pcfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;

import difkey.pcfinder.db.com.recherche;

public class Manual_Search_Activity extends AppCompatActivity {

    private EditText searchInput;
    private recherche finder;
    private Manual_Search_Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_search);

        finder = new recherche();
        searchInput = findViewById(R.id.searchInput);

        searchInput.setOnEditorActionListener( new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    searchInput.clearFocus();
                    finder.recherchePcManual(searchInput.getText().toString().toUpperCase(), activity);
                    return true;
                }
                return false;
            }
        });
    }

    public void finded(Intent data){
        //Finish and return to main
        setResult(CommonStatusCodes.SUCCESS, data);
        finish();
    }

    boolean over = false;
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
                        //Reset search engine ?
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
