package apt7.com.demo;

        import android.Manifest;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.ActionBarActivity;
        import android.view.KeyEvent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.inputmethod.EditorInfo;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.koushikdutta.ion.Ion;


public class MainActivity extends ActionBarActivity {
//    static EditText editText1;
//    static EditText editText2;
//    Button button;
//    static ServerPref serverPref;
//    static Utils utils;
//    TelnetServer telnet;
//    public Handler mHandler;

     EditText editText1;
     EditText editText2;
    Button button;
     ServerPref serverPref;
     Utils utils;
    TelnetServer telnet;
    public Handler mHandler;
    private boolean isOpenPermission=false;
    private RequestPermission _requestPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.id5).setVisibility(View.GONE);
        utils = new Utils(this);
        editText1 = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText1);

        serverPref = new ServerPref(this);
        mHandler = new Handler();

        button = (Button) findViewById(R.id.button);

        if (utils.isConnectedMobile() || utils.isConnectedWifi()) {
            runApp();
        } else {
            showAlertToUser();
        }

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editText1.setText("");
                connect();
                Toast.makeText(getApplicationContext(),telnet+"",Toast.LENGTH_LONG).show();
                findViewById(R.id.button2).setEnabled(true);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showExitAlert();
            }
        });





    }





    private void connect() {
        telnet = new TelnetServer(this, this);
        telnet.start();
        findViewById(R.id.button1).setEnabled(false);
    }

    private void showExitAlert() {
        telnet.disconnect();
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alert;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attention!\n\nDisconnected from Port closing app.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        alert = alertDialogBuilder.create();
        alert.show();
    }

    private void runApp() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editText2.getText().toString().replaceAll("\\s", "").length() == 0) {
                    showAlertEmpty();
                } else {
                    send();
                }
            }
        });

        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND && editText2.getText().toString().replaceAll("\\s", "").length() > 0) {
                    send();
                    handled = true;
                }
                return handled;
            }
        });
    }

    private void showAlertEmpty() {
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alert;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attention!\n\nInformation is missing.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alert = alertDialogBuilder.create();
        alert.show();
    }

    private void showAlertToUser() {
        AlertDialog.Builder alertDialogBuilder;
        AlertDialog alert;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attention!\n\nPlease enable internet to shop.\nWould you like to enable them now?")
                .setCancelable(false)
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                }).setNegativeButton("Exit App", new DialogInterface.OnClickListener() {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
//            if (!telnet.isAlive())
                startActivity(new Intent(this, SettingsActivity.class));
//            else
//                Toast.makeText(this, "Please disconnect connection to go to setting.", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void send() {


        NetworkThread thread = new NetworkThread();
        thread.start();
        thread.interrupt();

//        mHandler.post(new Runnable() {
//            public void run() {
//
//                String lastLine = editText1.getText().toString();
//                editText1.setText("");
//                editText1.setText(lastLine + "\n>" + editText2.getText().toString());
//
//                editText2.setText("");
//                if (editText2.getText().toString().toLowerCase().equals("quit")) {
//                    lastLine = editText1.getText().toString();
//                    editText1.setText(lastLine + "\n" + "Disconnection from Server");
//                    showExitAlert();
//                }
//            }
//        });
    }

//    Thread temporary_thread = new Thread() {
//        public void run() {
//            telnet.out.println(editText2.getText().toString());
//            telnet.out.flush();
//        }
//    }.start();


    private class NetworkThread extends Thread {

        public void run() {
//            telnet.out.println(editText2.getText().toString());
            telnet.out.println("가나다");

            telnet.out.flush();
        }
    }




    public void hideOrShow(String msg) {
        if (msg == null) {
            findViewById(R.id.id5).setVisibility(View.GONE);
        } else {
            findViewById(R.id.id5).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textView)).setText(msg);
            ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
            Ion.with(imageButton).load(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(telnet != null)
            telnet.disconnect();
    }






}