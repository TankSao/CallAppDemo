# CallAppDemo
WebView启动APP</br>
CallApp1：</br>
通过设置href启动CallApp2，并传递参数</br>
CallApp2：</br>
通过调用安卓方法，利用包名启动CallApp1</br>
截图：</br>
![image](https://github.com/TankSao/CallAppDemo/blob/master/image/img1.png)</br>
![image](https://github.com/TankSao/CallAppDemo/blob/master/image/img2.png)</br>
关键代码：</br>
``` Android
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
