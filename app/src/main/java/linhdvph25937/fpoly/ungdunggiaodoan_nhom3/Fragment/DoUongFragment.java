package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.ChiTietSanPhamActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.DoAnAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.DoUongAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPhamReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoUongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoUongFragment extends Fragment {
    private ListView lvDs;
    private ArrayList<SanPham> list;
    private DoUongAdapter adapter;
    private int id = 0, page = 1;
    private View footerView;
    private boolean isLoading = false, limitData = false;
//    private MyHandler myHandler;
    private static final String TAG = "Prrr";
    public DoUongFragment() {
        // Required empty public constructor
    }

    public static DoUongFragment newInstance() {
        DoUongFragment fragment = new DoUongFragment();
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
        return inflater.inflate(R.layout.fragment_do_uong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvDs = view.findViewById(R.id.lvDsDoUong);
        GetData();
        //Xử lí sự kiện khi người dùng ấn vào từng sản phẩm
        ClickProduct();
    }

    private void ClickProduct() {
        lvDs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", list.get(i));
                startActivity(intent);
            }
        });
    }

    private void GetData(){
        MyRetrofit.api.getSanPhamByIdLoaiSp(2).enqueue(new Callback<SanPhamReceiver>() {
            @Override
            public void onResponse(Call<SanPhamReceiver> call, Response<SanPhamReceiver> response) {
                if (response.body() != null){
                    list = response.body().getList();
                    adapter = new DoUongAdapter(getActivity(), list);
                    lvDs.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "Không lấy được danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SanPhamReceiver> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Call api error while get list product", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }
}