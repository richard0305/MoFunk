package ylj.mofunk.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ylj.mofunk.R;
import ylj.mofunk.model.Api.ApiConstans;
import ylj.mofunk.model.Base.ActivityController;
import ylj.mofunk.model.Base.BaseActivity;
import ylj.mofunk.model.tools.ToastUtils;


/**
 * Created by 我的样子平平无奇 on 2017/9/5 10:58.
 * Email: 2256669598@qq.com
 */

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fragment)
    FrameLayout frameLayout;
    private OneFragment oneFragment;
    private GankFragment gankFragment;
    private MapFragment mapFragment;
    private FragmentTransaction fragmentTransaction;



    @Override
    protected void initView() {
        setContentView(R.layout.activity_main_home1);
        ButterKnife.bind(this);
//        startService(
//                new Intent(HomeActivity.this, BadgeIntentService.class).putExtra("badgeCount", 5)
//        );



        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;
//
        ToastUtils.showLongToast("currentHomePackage="+currentHomePackage);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("我的样子平平无奇");
//        toolbar.setNavigationIcon(R.mipmap.huge);
//        toolbar.setTitle("");
//        setSupportActionBar(toolbar);


        List<String> permissionList = new ArrayList<String>();

        if(Build.VERSION.SDK_INT>=23){

            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_CONTACTS);
            }

            if (!permissionList.isEmpty()) {
                String [] permissions = permissionList.toArray(new String[permissionList.size()]);
                ActivityCompat.requestPermissions(HomeActivity.this, permissions, 1);
            } else {
//                SatrtLocationServer();
            }


        }else if(Build.VERSION.SDK_INT<23){
//            SatrtLocationServer();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showHomeFragment();
        commitShowFragment(fragmentTransaction, gankFragment);

        File tmpFile = new File(ApiConstans.SavePath);
        if (!tmpFile.exists()) {
            tmpFile.mkdir();
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
//                    SatrtLocationServer();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            commitShowFragment(getSupportFragmentManager().beginTransaction(),gankFragment);
        } else if (id == R.id.nav_gallery) {
            commitShowFragment(getSupportFragmentManager().beginTransaction(),oneFragment);
        }
        else if (id == R.id.nav_map) {
            commitShowFragment(getSupportFragmentManager().beginTransaction(),mapFragment);
        }else if(id==R.id.nav_share){
            ActivityController.jumpToActivity(this,ShareActivity.class);
        }
//        else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showHomeFragment() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (gankFragment == null) {
            gankFragment = new GankFragment();
            fragmentTransaction.add(R.id.fragment, gankFragment);
            fragmentTransaction.hide(gankFragment);
        }
        if (oneFragment == null) {
            oneFragment = new OneFragment();
            fragmentTransaction.add(R.id.fragment, oneFragment);
            fragmentTransaction.hide(oneFragment);
        }
        if (mapFragment == null) {
            mapFragment = new MapFragment();
            fragmentTransaction.add(R.id.fragment, mapFragment);
            fragmentTransaction.hide(mapFragment);
        }

    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.hide(oneFragment);
        fragmentTransaction.hide(gankFragment);
        fragmentTransaction.hide(mapFragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }


}
