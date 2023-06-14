package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.ManHinhDangNhapActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.NguoiDung;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {
    private TextView tvUsername;
    private TextInputEditText tedUsername, tedOldPasswd, tedNewPasswd, tedRePasswd;
    private Button btnUpdate;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String oldName = "";
    private static final String TAG = "Prrr";
    private LinearLayout layoutUpdateInfo;
    private Button btnDangXuat;
    public UserInfoFragment() {
        // Required empty public constructor
    }


    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        if (sharedPreferences != null){
            tvUsername.setText(sharedPreferences.getString("usernamelogin", ""));
            oldName = sharedPreferences.getString("usernamelogin", "");
        }
        if (oldName.equals("admin")){
            layoutUpdateInfo.setVisibility(View.GONE);
            btnDangXuat.setVisibility(View.VISIBLE);
        }

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Đăng xuất")
                        .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                        .setMessage("Bạn có chắc chắn không?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getActivity(), ManHinhDangNhapActivity.class));
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });
    }

    private void UpdateUser() {
        int idUserLogin = sharedPreferences.getInt("iduserlogin", 0);
        String passwdLogin = sharedPreferences.getString("passwordlogin", "").trim();
//        Toast.makeText(getContext().getApplicationContext(), "id: "+ idUserLogin + "\n" + "Passwd: "+passwdLogin, Toast.LENGTH_SHORT).show();
        String newUsername = tedUsername.getText().toString().trim();
        String oldPasswd = tedOldPasswd.getText().toString().trim();
        String newPasswd = tedNewPasswd.getText().toString().trim();
        String reNewPasswd = tedRePasswd.getText().toString().trim();
        if (TextUtils.isEmpty(newUsername) || TextUtils.isEmpty(oldPasswd) || TextUtils.isEmpty(newPasswd) || TextUtils.isEmpty(reNewPasswd)){
            Toast.makeText(getContext().getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldPasswd.equals(passwdLogin)){
            Toast.makeText(getContext().getApplicationContext(), "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!reNewPasswd.equals(newPasswd)){
            Toast.makeText(getContext().getApplicationContext(), "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
            return;
        }

        MyRetrofit.api.updateUser(new NguoiDung(newUsername, newPasswd), idUserLogin).enqueue(new Callback<NguoiDung>() {
            @Override
            public void onResponse(Call<NguoiDung> call, Response<NguoiDung> response) {
                if (response.body() != null){
                    UpdateDonHang(newUsername, oldName);
                    Toast.makeText(getContext().getApplicationContext(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    editor = sharedPreferences.edit();
                    editor.remove("isLogin");
                    editor.apply();
                    AllowExit();
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "Không thể cập nhật thông tin", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NguoiDung> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Call api error while update user", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private void UpdateDonHang(String newName, String oldName) {
        MyRetrofit.api.updateDonHangWhenUpdateUser(oldName, newName).enqueue(new Callback<DonHang>() {
            @Override
            public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                if (response.body() == null){
                    Toast.makeText(getContext().getApplicationContext(), "Không thể cập nhật thông tin nguoi dung tren don hang", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHang> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Call api error while update donhang when update user", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private void AllowExit() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Đăng xuất")
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setMessage("Bạn nên đăng nhập lại sau khi cập nhật thông tin!")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getActivity(), ManHinhDangNhapActivity.class));
                        getActivity().finish();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void AnhXa(View view) {
        tvUsername = view.findViewById(R.id.tvUsernameInfo);
        tedUsername = view.findViewById(R.id.edUsernameInfo);
        tedOldPasswd = view.findViewById(R.id.edOldPasswdInfo);
        tedNewPasswd = view.findViewById(R.id.edNewPasswdInfo);
        tedRePasswd = view.findViewById(R.id.edRePasswdInfo);
        btnUpdate = view.findViewById(R.id.btnUpdateUserInfo);
        sharedPreferences = getActivity().getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        layoutUpdateInfo = view.findViewById(R.id.layoutUpdateInfoUser);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
    }
}