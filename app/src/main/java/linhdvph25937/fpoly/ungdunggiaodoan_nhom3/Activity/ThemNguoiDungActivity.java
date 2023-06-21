package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.NguoiDung;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.UserReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemNguoiDungActivity extends AppCompatActivity {
    private TextInputEditText edTenTaiKhoan, edPass, edRePass;
    private Button btnXacNhan, btnHuy;
    private static final String TAG = ThemNguoiDungActivity.class.getName();
    private ArrayList<NguoiDung> listNguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AnhXa();
        GetAllUser();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (CheckConnection.HaveConnection(getApplicationContext())){
            EventButtonConfirm();//sự kiện nút xác nhận
        }else{
            CheckConnection.ShowToast(getApplicationContext(), "Không có kết nối mạng");
        }
    }

    private void EventButtonConfirm(){
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edTenTaiKhoan.getText().toString().trim();
                String passwd = edPass.getText().toString().trim();
                String rePasswd = edRePass.getText().toString().trim();
                boolean isExit = false;
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(rePasswd)){
                    Toast.makeText(ThemNguoiDungActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!passwd.equals(rePasswd)){
                    Toast.makeText(ThemNguoiDungActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < listNguoiDung.size(); i++) {
                    if (listNguoiDung.get(i).getTenTaiKhoan().equals(username)){
                        isExit = true;
                    }
                }

                if (isExit){
                    Toast.makeText(ThemNguoiDungActivity.this, "Username đã tồn tại, vui lòng nhập một tên khác", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyRetrofit.api.signup(new NguoiDung(username, passwd)).enqueue(new Callback<NguoiDung>() {
                    @Override
                    public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                        if (response.body() != null){
                            Toast.makeText(ThemNguoiDungActivity.this, "Thêm tài khoản thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ManHinhDangNhapActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ThemNguoiDungActivity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NguoiDung> call, Throwable t) {
                        Toast.makeText(ThemNguoiDungActivity.this, "Call api error in singup user", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: "+ t);
                    }
                });
            }
        });
    }

    private void GetAllUser(){
        MyRetrofit.api.getAllUser().enqueue(new Callback<UserReceiver>() {
            @Override
            public void onResponse(Call<UserReceiver> call, Response<UserReceiver> response) {
                if (response.body() != null){
                    listNguoiDung = response.body().getUser();
                }else{
                    Toast.makeText(ThemNguoiDungActivity.this, "Không thể lấy danh sách người ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserReceiver> call, Throwable t) {
                Toast.makeText(ThemNguoiDungActivity.this, "Call api error in get all user", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+ t);
            }
        });
    }

    private void AnhXa() {
        edTenTaiKhoan = findViewById(R.id.edTenTaiKhoan);
        edPass = findViewById(R.id.edMatKhauAdd);
        edRePass = findViewById(R.id.edReMatKhau);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnHuy = findViewById(R.id.btnTroVe);
    }
}