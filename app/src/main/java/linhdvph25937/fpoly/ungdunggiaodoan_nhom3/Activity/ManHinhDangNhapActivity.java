package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.NguoiDung;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.UserReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhDangNhapActivity extends AppCompatActivity {
    private EditText edTenDangNhap, edMatKhau;
    private CheckBox chkLuu;
    private Button btnDangNhap;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<NguoiDung> list;
    private TextView tvTaoTaiKhoan;
    private String TAG = ManHinhDangNhapActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.layout.activity_man_hinh_dang_nhap);

        AnhXa();
        GetAllUser();
        FillData();
        CheckLogin();
        CreateAccount();
    }

    private void CreateAccount() {
        //Tạo phần gạch chân
        String ten = tvTaoTaiKhoan.getText().toString();
        SpannableString spannableString = new SpannableString(ten);
        spannableString.setSpan(new UnderlineSpan(), 0, ten.length(), 0);
        tvTaoTaiKhoan.setText(spannableString);

        tvTaoTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ThemNguoiDungActivity.class));
            }
        });
    }

    private void FillData() {
        sharedPreferences = getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        edTenDangNhap.setText(sharedPreferences.getString("tendangnhap", ""));
        edMatKhau.setText(sharedPreferences.getString("matkhau", ""));
        chkLuu.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    private void CheckSave(String ten, String mk) {
        if (chkLuu.isChecked()){
            editor = sharedPreferences.edit();
            editor.putString("tendangnhap", ten);
            editor.putString("matkhau", mk);
            editor.putBoolean("checked", true);
        }else{
            editor = sharedPreferences.edit();
            editor.remove("matkhau");
            editor.remove("checked");
        }
        editor.commit();
    }
    
    private void CheckLogin() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edTenDangNhap.getText().toString().trim();
                String passwd = edMatKhau.getText().toString().trim();
                int isExit = 0;
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwd)){
                    Toast.makeText(ManHinhDangNhapActivity.this, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                for (int i = 0; i < list.size(); i++) {
//                    if (list.get(i).getTenTaiKhoan().equals(username)){
//                        isExit++;
//
//                    }
//                    if (list.get(i).getMatKhau().equals(passwd)){
//                        isExit++;
//                    }
//                }

//                int finalIsExit = isExit;
                MyRetrofit.api.signin(new NguoiDung(username, passwd)).enqueue(new Callback<UserReceiver>() {
                    @Override
                    public void onResponse(Call<UserReceiver> call, Response<UserReceiver> response) {
                        if (response.body() != null){
                            ArrayList<NguoiDung> listUser = response.body().getUser();
                            CheckSave(username, passwd);
                            editor = sharedPreferences.edit();
                            editor.putString("usernamelogin", listUser.get(0).getTenTaiKhoan());
                            editor.putString("passwordlogin", listUser.get(0).getMatKhau());
                            editor.putInt("iduserlogin", listUser.get(0).getId());
                            editor.putInt("isLogin", 1);
                            editor.apply();
                            startActivity(new Intent(ManHinhDangNhapActivity.this, MainActivity.class));
//                            }else if (finalIsExit == 1){
//                                Toast.makeText(ManHinhDangNhapActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(ManHinhDangNhapActivity.this, "Tên người dùng không chính xác", Toast.LENGTH_SHORT).show();
//                            }
                        }else {
                            Toast.makeText(ManHinhDangNhapActivity.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserReceiver> call, Throwable t) {
                        Toast.makeText(ManHinhDangNhapActivity.this, "Call api error while sign in", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: "+t);
                    }
                });
            }
        });
    }

    //Lấy danh sách người dùng
    private void GetAllUser(){
        MyRetrofit.api.getAllUser().enqueue(new Callback<UserReceiver>() {
            @Override
            public void onResponse(Call<UserReceiver> call, retrofit2.Response<UserReceiver> response) {
                if (response.body() != null){
                    list = response.body().getUser();
                }else{
                    Toast.makeText(ManHinhDangNhapActivity.this, "Không thể lấy danh sách người ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserReceiver> call, Throwable t) {
                Toast.makeText(ManHinhDangNhapActivity.this, "Call api error in get all user", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+ t);
            }
        });
    }

    private void AnhXa() {
        edTenDangNhap = findViewById(R.id.edTenDangNhap);
        edMatKhau = findViewById(R.id.edMatKhau);
        chkLuu = findViewById(R.id.chkLuuThongTin);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvTaoTaiKhoan = findViewById(R.id.tvTaoTaiKhoan);
        list = new ArrayList<>();
    }
}