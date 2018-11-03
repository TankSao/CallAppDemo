package com.example.administrator.callapp1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");
        webView.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (TextUtils.isEmpty(url)) return false;
            // 通过webView打开其它app
            try {
                if (!url.startsWith("http") || !url.startsWith("https") || !url.startsWith("ftp")) {
                    Log.e("url",url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (isInstall(intent)) {
                        intent.putExtra("source","web");
                        startActivity(intent);
                        return true;
                    }else{
                        Toast.makeText(MainActivity.this,"应用未安装",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.e("err",e.getMessage());
                return false;
            }

            return false;
        }
    }

    private boolean isInstall(Intent intent) {
        //return getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
        PackageManager packageManager = getPackageManager();
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("abraham://jingewenku.com:8888/goodsDetail?goodsId=10011002"));
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        return  isValid;
    }



}
