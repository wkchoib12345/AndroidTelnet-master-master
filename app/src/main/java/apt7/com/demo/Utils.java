package apt7.com.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by MW-Ravi on 2/28/2015 Project FacebookShopping under com.apt7.facebookshopping.
 */
public class Utils {
    Context context;

    public Utils(Context context) {
        this.context = context;
    }

    NetworkInfo info;
    ConnectivityManager cm;

    public boolean isConnectedMobile() {
        if (isPhone()) {
            info = getNetworkInfo();
            return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
        }
        return isConnectedWifi();
    }

    public boolean isConnectedWifi() {
        info = getNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public NetworkInfo getNetworkInfo() {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public boolean isPhone() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getPhoneType() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public ProgressDialog createProgressDialog(String message) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return mProgressDialog;
    }
}