package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.DoAnFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.DoUongFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.DonHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.GioHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.HomeFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.NguoiDungFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.UserInfoFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    public static ArrayList<GioHang> listGioHang;
    public static ArrayList<DonHang> listDonHang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView tvTenNguoiDung;
    private ImageView imgUserToInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AnhXa();
        if (sharedPreferences != null){
            tvTenNguoiDung.setText("Welcome, "+sharedPreferences.getString("usernamelogin", ""));
        }
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0,0);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ChooseFragment(HomeFragment.newInstance());
        toolbar.setTitle("Trang chủ");

        imgUserToInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void AnhXa() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        listDonHang = new ArrayList<>();
        if (listGioHang == null){
            listGioHang = new ArrayList<>();
        }
        sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        tvTenNguoiDung = navigationView.getHeaderView(0).findViewById(R.id.tvTenNguoiDung);
        imgUserToInfo = navigationView.getHeaderView(0).findViewById(R.id.imgUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shopping, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_shopping:
                ChooseFragment(GioHangFragment.newInstance());
                toolbar.setTitle("Giỏ hàng");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_trang_chu:
                ChooseFragment(HomeFragment.newInstance());
                toolbar.setTitle("Trang chủ");
                break;
            case R.id.menu_do_an:
                ChooseFragment(DoAnFragment.newInstance());
                toolbar.setTitle("Đồ ăn");
                break;
            case R.id.menu_do_uong:
                ChooseFragment(DoUongFragment.newInstance());
                toolbar.setTitle("Đồ uống");
                break;
            case R.id.menu_gio_hang:
                ChooseFragment(GioHangFragment.newInstance());
                toolbar.setTitle("Giỏ hàng");
                break;
            case R.id.menu_don_hang:
                ChooseFragment(DonHangFragment.newInstance());
                toolbar.setTitle("Đơn hàng");
                break;
            case R.id.menu_user_info:
                ChooseFragment(UserInfoFragment.newInstance());
                toolbar.setTitle("Thông tin tài khoản");
                break;
            case R.id.menu_nguoi_dung:
                ChooseFragment(NguoiDungFragment.newInstance());
                toolbar.setTitle("Liên hệ");
                break;
            case R.id.menu_dang_xuat:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setIcon(R.drawable.ic_baseline_exit_to_app_24);
                builder.setMessage("Bạn chắc chắn muốn đăng xuất?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listGioHang.clear();
                        editor = sharedPreferences.edit();
                        editor.remove("isLogin");
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), ManHinhDangNhapActivity.class));
                        finish();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return false;
    }

    private void ChooseFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack("back");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)){
            drawerLayout.closeDrawer(navigationView);
        }else{
            super.onBackPressed();
            toolbar.setTitle("Trang chủ");
        }

    }
}