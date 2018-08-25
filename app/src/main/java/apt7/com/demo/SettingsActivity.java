package apt7.com.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SettingsActivity extends Activity {
    EditText editText1;
    EditText editText2;
    Button button;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ServerPref serverPref = new ServerPref(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);

        editText1.setText(serverPref.readServerAddress());
        editText2.setText(String.valueOf(serverPref.readServerPort()));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editText2.getText().toString().matches("[0-9]+") || editText1.getText().toString().replaceAll("\\s", "").length() == 0 || editText2.getText().toString().replaceAll("\\s", "").length() == 0) {
                    showAlertToUser();
                } else {
                    serverPref.writeServerAddress(editText1.getText().toString().replaceAll("\\s", ""));
                    serverPref.writeServerPort(Integer.parseInt(editText2.getText().toString().replaceAll("\\s", "")));
                    onBackPressed();

                }
            }
        });
    }

    private void showAlertToUser() {
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alert;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attention!\n\nInformation is missing.")
                .setCancelable(false)
                .setPositiveButton("Re Enter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                }).setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        alert = alertDialogBuilder.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
