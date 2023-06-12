package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class ManHinhChaoActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private int isLogin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.layout.activity_man_hinh_chao);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        if (sharedPreferences != null){
            isLogin = sharedPreferences.getInt("isLogin", 0);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               if (isLogin == 1){
                   startActivity(new Intent(ManHinhChaoActivity.this, MainActivity.class));
                   finish();
               }else{
                   startActivity(new Intent(ManHinhChaoActivity.this, ManHinhDangNhapActivity.class));
                   finish();
               }
            }
        },2500);
    }
}