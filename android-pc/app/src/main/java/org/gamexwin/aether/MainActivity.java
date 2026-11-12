package org.gamexwin.aether;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.view.View;

public final class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        web = new WebView(this);
        web.setBackgroundColor(0xff08051d);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true); // only bundled, local UI
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setDomStorageEnabled(false);
        web.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                if ("https".equals(uri.getScheme()) &&
                    ("www.apkmirror.com".equals(uri.getHost()) || "apkmirror.com".equals(uri.getHost()))) {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                return true; // do not load remote pages inside privileged shell
            }
        });
        setContentView(web);
        web.loadUrl("file:///android_asset/desktop.html");
    }
    @Override public void onBackPressed() { /* keep desktop as home */ }
    @Override protected void onDestroy() { if (web != null) web.destroy(); super.onDestroy(); }
}
