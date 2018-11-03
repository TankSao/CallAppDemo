package com.example.administrator.callapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/11/3.
 */

public class JsInterface {
    private Context mContext;
    public JsInterface(Context mContext){
        this.mContext = mContext;
    }
    @JavascriptInterface
    public void callApp(final String url) {
        Log.e("url",url);
        if(isInstalled(url)){
            PackageManager packageManager = mContext.getPackageManager();
            Intent intent=new Intent();
            intent =packageManager.getLaunchIntentForPackage(url);
            mContext.startActivity(intent);
        }else{
            Toast.makeText(mContext,"应用未安装",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isInstalled(String url){
        PackageInfo packageInfo;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(
                    url, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo ==null){
            return false;
        }else{
            return true;
        }
    }
}
