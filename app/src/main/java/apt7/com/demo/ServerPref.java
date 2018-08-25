package apt7.com.demo;

        import android.content.Context;
        import android.content.SharedPreferences;

/**
 * Created by MW-Ravi on 4/27/2015 Project Demo under apt7.com.demo.
 */
public class ServerPref {
    String Pref = "server";
    String serverAddress = "storageBox";
    String serverPort = "storageKey";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;

    public ServerPref(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(Pref, Context.MODE_PRIVATE);
    }

    public String readServerAddress() {
        return sharedpreferences.getString(serverAddress, "192.168.4.1");
    }

    public int readServerPort() {
        return sharedpreferences.getInt(serverPort, 23);
    }

    public void writeServerAddress(String value) {
        editor = sharedpreferences.edit();
        editor.putString(serverAddress, value);
        editor.apply();
    }

    public void writeServerPort(int value) {
        editor = sharedpreferences.edit();
        editor.putInt(serverPort, value);
        editor.apply();
    }
}
