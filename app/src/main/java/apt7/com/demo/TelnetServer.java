package apt7.com.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;

import org.apache.commons.net.telnet.TelnetClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class TelnetServer extends Thread implements Runnable {
    MainActivity mainActivity;

    public PrintStream out;
    public InputStream in;

    int count;
    String data;

    public StringBuffer sb;
    public ServerPref serverPref;
    public TelnetClient telnet;

    TelnetServer(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        serverPref = new ServerPref(context);
    }

    public void disconnect() {
        try {
            telnet.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean connected = false;
        telnet = new TelnetClient();
        sb = new StringBuffer();

        try {
            postToEditText("Establishing Connection");
            telnet.connect(serverPref.readServerAddress(), serverPref.readServerPort());
            if (telnet.isConnected())
                postToEditText("Connected to " + serverPref.readServerAddress());
            else
                postToEditText("Connection error to " + serverPref.readServerAddress());
            telnet.setKeepAlive(true);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            connected = true;
            while (true) {
                count = in.read();
                data = Character.toString((char) count);
                Log.i("data @@", data);
                sb.append(data);
                Log.i("sb @@", sb.toString());

                if (in.available() == 0) {
                    postToEditText(sb.toString());
                    sb = new StringBuffer();
                    if (connected && sb.toString().length() > 3 && !sb.toString().substring(0, 3).equals("220")) {
                        showExitAlert();
                    }
                    mainActivity.hideOrShow(getURL(sb.toString()));
                    if (sb.toString().length() < 4)
                        connected = false;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String getURL(String message) {
        String url = null;
        if (message != null && message.contains("http")) {
            url = message.split("http")[1];
            if (url.contains(" ")) {
                url = url.split(" ")[0];
            }
            url = "http" + url;
        }
        return url;
    }

    private void showExitAlert() {
        AlertDialog.Builder alertDialogBuilder;
        final AlertDialog alert;
        alertDialogBuilder = new AlertDialog.Builder(mainActivity);
        alertDialogBuilder.setMessage("Attention!\n\nGreeting Failed. Try Again Later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        alert = alertDialogBuilder.create();
        alert.show();
    }

    void postToEditText(final String msg) {
        mainActivity.mHandler.post(new Runnable() {
            public void run() {
                String lastLine = mainActivity.editText1.getText().toString();
                mainActivity.editText1.setText(lastLine + "\n" + msg);
                Log.i("msg @@ ", msg);
            }
        });
    }







}
