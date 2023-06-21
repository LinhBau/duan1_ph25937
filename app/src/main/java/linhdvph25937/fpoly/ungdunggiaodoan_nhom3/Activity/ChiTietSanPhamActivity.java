package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.GioHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imgChiTiet;
    private TextView tvTen, tvGia, tvMoTa;
    private Spinner spinner;
    private Button btnDatMua;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
    private Calendar calendar = Calendar.getInstance();
    private String ngayDatHang = sdf.format(calendar.getTime());
    int id = 0;
    String tenChiTiet = "";
    int giaChiTiet = 0;
    String hinhAnhCT = "";
    String mota = "";
    int idsp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.layout.activity_chi_tiet_san_pham);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AnhXa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton(){
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listGioHang.size() > 0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                        if (MainActivity.listGioHang.get(i).getIdsp() == id){
                            MainActivity.listGioHang.get(i).setSoluong(MainActivity.listGioHang.get(i).getSoluong()+sl);
                            if (MainActivity.listGioHang.get(i).getSoluong() >= 10){
                                MainActivity.listGioHang.get(i).setSoluong(10);
                            }
                            MainActivity.listGioHang.get(i).setGiasp(MainActivity.listGioHang.get(i).getSoluong() * giaChiTiet);
                            exists = true;
                        }
                    }
                    if (exists == false)//đi vào vòng lặp không có sp nào trùng id
                    {
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = giaChiTiet * soLuong;
                        MainActivity.listGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhCT, soLuong, ngayDatHang));
//                        GioHangFragment.TotalMoney();
                    }
                }else//trong giỏ hàng lúc này chưa có dữ liệu
                {
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = giaChiTiet * soLuong;
                    MainActivity.listGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhCT, soLuong, ngayDatHang));
                }
                CheckConnection.ShowToast(getApplicationContext(), "Đã thêm vào giỏ hàng");
            }
        });
    }


    private void CatchEventSpinner() {
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, soLuong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        SanPham obj = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = obj.getId();
        tenChiTiet = obj.getTenSp();
        giaChiTiet = obj.getGia();
        hinhAnhCT = obj.getHinhAnh();
        mota = obj.getMoTa();
        idsp = obj.getIdLoaiSp();

        tvTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGia.setText("Giá: "+decimalFormat.format(giaChiTiet)+" Đ");
        tvMoTa.setText(mota);
        Picasso.get().load(hinhAnhCT)
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(imgChiTiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarChiTietSP);
        imgChiTiet = findViewById(R.id.imgChiTietSP);
        tvTen = findViewById(R.id.tvTenChiTietSP);
        tvGia = findViewById(R.id.tvGiaChiTietSP);
        tvMoTa = findViewById(R.id.tvMoTaChiTietSP);
        spinner = findViewById(R.id.spinnerChiTietSP);
        btnDatMua = findViewById(R.id.btnAddChiTietSP);
    }
}