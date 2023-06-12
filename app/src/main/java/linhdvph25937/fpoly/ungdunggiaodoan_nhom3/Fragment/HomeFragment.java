package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.HinhAnhAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.LoaiSanPhamAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.SanPhamMoiNhatAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Animation.ZoomOutPageTransformer;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.HinhAnh;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.LoaiSanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPhamReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.Server;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Button btnTimKiem;
    private ImageView imgDoAn, imgDoUong, imgVanChuyen, imgGiaoHang, imgKhuyenMai, imgCuaHang;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private ArrayList<HinhAnh> list = new ArrayList<>();
    private RecyclerView dsSanPhamMoiNhat;
    private ArrayList<LoaiSanPham> listLoaiSanPham;
    private ArrayList<SanPham> listSanPhamNew;
    private LoaiSanPhamAdapter loaiSanPhamAdapter;
    private SanPhamMoiNhatAdapter sanPhamMoiNhatAdapter;
    private static final String TAG = "Prrr";
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size()-1){
                viewPager2.setCurrentItem(0);
            }else{
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        }
    };
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        SliderImg();
            ActionImpressImg();
//            GetDataFromServer();
            GetDataNew();
            ButtonFindData();
    }

    private void ButtonFindData() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_search);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                SearchView searchView = dialog.findViewById(R.id.searchViewDialog);
                RecyclerView recyclerView = dialog.findViewById(R.id.dsTimKiemDialog);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(sanPhamMoiNhatAdapter);
                SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        sanPhamMoiNhatAdapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        sanPhamMoiNhatAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
                dialog.show();
            }
        });
    }

    private void GetDataNew() {
        MyRetrofit.api.getNewSanPham().enqueue(new Callback<SanPhamReceiver>() {
            @Override
            public void onResponse(Call<SanPhamReceiver> call, Response<SanPhamReceiver> response) {
                if (response.body() != null){
                    listSanPhamNew = response.body().getList();
                    sanPhamMoiNhatAdapter.setData(listSanPhamNew);
                    dsSanPhamMoiNhat.setAdapter(sanPhamMoiNhatAdapter);
                }else{
                    Toast.makeText(getContext().getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SanPhamReceiver> call, Throwable t) {
                Toast.makeText(getContext().getApplicationContext(), "Call api error while get new product", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    private void ActionImpressImg() {
        imgDoAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DoAnFragment doAnFragment = DoAnFragment.newInstance();
               FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, doAnFragment);
                transaction.addToBackStack("back");
                transaction.commit();
            }
        });

        imgDoUong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoUongFragment fragment = DoUongFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack("back");
                transaction.commit();
            }
        });

        ActionButton(imgCuaHang);
        ActionButton(imgGiaoHang);
        ActionButton(imgVanChuyen);
        ActionButton(imgKhuyenMai);
    }

    private void ActionButton(ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OthersFragment fragment = OthersFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack("prrrrrrrrr");
                transaction.commit();
            }
        });
    }

    private void SliderImg() {
        list.add(new HinhAnh(R.drawable.imgfood1));
        list.add(new HinhAnh(R.drawable.imgfood2));
        list.add(new HinhAnh(R.drawable.imgfood3));
        list.add(new HinhAnh(R.drawable.imgfood4));
        list.add(new HinhAnh(R.drawable.imgfood5));
        list.add(new HinhAnh(R.drawable.imgfood6));
        list.add(new HinhAnh(R.drawable.imgfood7));
        list.add(new HinhAnh(R.drawable.imgfood8));
        HinhAnhAdapter hinhAnhAdapter = new HinhAnhAdapter(list);
        viewPager2.setAdapter(hinhAnhAdapter);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 5000);
            }
        });
    }

    private void AnhXa(View view) {
        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        imgDoAn = view.findViewById(R.id.imgDoAn);
        imgDoUong = view.findViewById(R.id.imgDoUong);
        imgVanChuyen = view.findViewById(R.id.imgVanChuyen);
        imgGiaoHang = view.findViewById(R.id.imgGiaoHang);
        imgKhuyenMai = view.findViewById(R.id.imgKhuyenMai);
        imgCuaHang = view.findViewById(R.id.imgCuaHang);
        viewPager2 = view.findViewById(R.id.viewPager2);
        circleIndicator3 = view.findViewById(R.id.circle_indicator_3);
        dsSanPhamMoiNhat = view.findViewById(R.id.dsSanPhamMoiNhat);
        listLoaiSanPham = new ArrayList<>();
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(getActivity(), listLoaiSanPham);

        sanPhamMoiNhatAdapter = new SanPhamMoiNhatAdapter(getActivity());
        dsSanPhamMoiNhat.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dsSanPhamMoiNhat.setHasFixedSize(true);
        dsSanPhamMoiNhat.setAdapter(sanPhamMoiNhatAdapter);
    }
}