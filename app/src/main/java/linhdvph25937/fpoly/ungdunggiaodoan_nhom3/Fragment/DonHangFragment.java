package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.MainActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.DonHangAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHangReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonHangFragment extends Fragment {
    private TextView tvThongBao;
    private RecyclerView rvDs;
    private ArrayList<DonHang> list;
    private SharedPreferences sharedPreferences;
    private String username = "";
    private static final String TAG = "Prrr";
    public DonHangFragment() {
        // Required empty public constructor
    }

    public static DonHangFragment newInstance() {
        DonHangFragment fragment = new DonHangFragment();
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
        return inflater.inflate(R.layout.fragment_don_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            username = sharedPreferences.getString("usernamelogin", "");
        }
        tvThongBao = view.findViewById(R.id.tvThongBaoDonHang);
        rvDs = view.findViewById(R.id.rvDsDonHang);

        GetListDonHangByUsername();
    }

    private void GetListDonHangByUsername() {
        MyRetrofit.api.getDonHangByUsername(username).enqueue(new Callback<DonHangReceiver>() {
            @Override
            public void onResponse(Call<DonHangReceiver> call, Response<DonHangReceiver> response) {
                if (response.body() != null){
                    list = response.body().getList();
                    if (list.size() > 0){
                        tvThongBao.setVisibility(View.INVISIBLE);
                        rvDs.setVisibility(View.VISIBLE);
                        DonHangAdapter donHangAdapter = new DonHangAdapter(getActivity(), list);
                        rvDs.setLayoutManager(new LinearLayoutManager(getActivity()));
                        rvDs.setAdapter(donHangAdapter);
                    }else{
                        tvThongBao.setVisibility(View.VISIBLE);
                        rvDs.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "Đơn hàng không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DonHangReceiver> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Call api error while get list don hang by username", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+ t);
            }
        });
    }
}