package ylj.mofunk.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import ylj.mofunk.R;
import ylj.mofunk.model.Base.BaseActivity;
import ylj.mofunk.model.Entity.GankAndroid;

/**
 * @author Administrator
 */
public class WebActivity extends BaseActivity {

    private GankAndroid gankandroid=null;
    private Toolbar toolbar;
    private ImageView image;
    private WebView webview;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_web);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         image = (ImageView) findViewById(R.id.web_iv);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        ViewCompat.setTransitionName(image, "tran_01");
        //设置Web视图

        image.setVisibility(View.GONE);
        webview.setWebViewClient(new HelloWebViewClient ());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void initData() {
        gankandroid= (GankAndroid) getIntent().getSerializableExtra("android_url");
        if(gankandroid!=null){
            toolbar.setTitle(gankandroid.getDesc());

            //加载需要显示的网页
            webview.loadUrl(gankandroid.getUrl());
        }

    }

//Web视图
private class HelloWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}

}
