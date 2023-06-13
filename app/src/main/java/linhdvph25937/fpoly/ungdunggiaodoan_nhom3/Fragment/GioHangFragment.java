package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.MainActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.GioHangAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter.TenSpDonHangAdapter;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.CheckConnection;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.Server;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GioHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GioHangFragment extends Fragment {
    private ListView lvDs;
    private TextView tvThongBao;
    public static TextView tvTongTien;
    private Button btnDatMua;
    private GioHangAdapter adapter;
    public static ArrayList<GioHang> listThucDon = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private static final String TAG = "Prrr";
    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance() {
        GioHangFragment fragment = new GioHangFragment();
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
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnhXa(view);
        CheckData();//Kiểm tra có dữ liệu hay không
        TotalMoney();//tổng tiền
        DeleteProduct();//xóa sản phẩm
        ButtonBought();//sự kiện đặt mua
        sharedPreferences = getActivity().getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
    }

    private void ButtonBought() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.listGioHang.size() > 0){
                    Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.layout_chi_tiet_don_hang);
                    dialog.setCanceledOnTouchOutside(false);

                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    //Set vị trị dialog
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.gravity = Gravity.BOTTOM;
                    window.setAttributes(layoutParams);

                    TextView tvTongTien;
                    Spinner spinnerThanhToan;
                    EditText edHoTen, edDiaChi, edSoDienThoai;
                    Button btnDatHang, btnHuyBo;
                    String ten = "";
                    if (sharedPreferences != null){
                        ten = sharedPreferences.getString("usernamelogin", "");
                    }

                    tvTongTien = dialog.findViewById(R.id.tvTongTienDonHangCt);
                    spinnerThanhToan = dialog.findViewById(R.id.spinnerThanhToan);

                    String[] phuongThuc = new String[]{"Tiền mặt", "Ngân hàng"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, phuongThuc);
                    spinnerThanhToan.setAdapter(arrayAdapter);

                    edHoTen = dialog.findViewById(R.id.edTenDonHangCt);
                    edHoTen.setText(ten);
                    edHoTen.setEnabled(false);
                    edDiaChi = dialog.findViewById(R.id.edDiaChiDonHangCt);
                    edSoDienThoai = dialog.findViewById(R.id.edSoDienThoaiDonHangCt);
                    btnDatHang = dialog.findViewById(R.id.btnDatHangDonHangCt);
                    btnHuyBo = dialog.findViewById(R.id.btnHuyDonHangCt);
                    RecyclerView lvDsTenSp = dialog.findViewById(R.id.lvDsTenSpDonHangCt);

                    ArrayList<GioHang> listTenSpDonHang = new ArrayList<>();
                    int money = 0;
                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                        money += MainActivity.listGioHang.get(i).getGiasp();
                        listTenSpDonHang.add(MainActivity.listGioHang.get(i));
                        listThucDon.add(MainActivity.listGioHang.get(i));
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvTongTien.setText("đ"+decimalFormat.format(money));

                    TenSpDonHangAdapter tenSpDonHangAdapter = new TenSpDonHangAdapter(getActivity(), listTenSpDonHang);
                    lvDsTenSp.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lvDsTenSp.setAdapter(tenSpDonHangAdapter);

                    int finalMoney = money;
                    String finalTen = ten;
                    btnDatHang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String diachi = edDiaChi.getText().toString();
                            String sdt = edSoDienThoai.getText().toString();
                            for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                if (finalTen.equals("") || diachi.equals("") || sdt.equals("")){
                                    CheckConnection.ShowToast(getActivity(), "Vui lòng điền đầy đủ thông tin");
                                }else{
                                    String thanhToan = spinnerThanhToan.getSelectedItem().toString();
                                    StringBuilder result = new StringBuilder();
//                                    String tenSp = MainActivity.listGioHang.get(i).getTensp();
//                                    long giasp = MainActivity.listGioHang.get(i).getGiasp();
//                                    int soLuong = MainActivity.listGioHang.get(i).getSoluong();
//                                    thucDon += " - "+tenSp+" (đ"+giasp+")"+" - Số lượng: "+soLuong +"\n";
                                    for (GioHang obj: MainActivity.listGioHang) {
                                        result.append(" - "+obj.getTensp()+" (đ"+obj.getGiasp()+")" + ", Số lượng: "+obj.getSoluong()).append("\n");
                                    }
                                  String thucDon = result.toString();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
                                    String ngayDatHang = sdf.format(Calendar.getInstance().getTime());
                                    Toast.makeText(getContext().getApplicationContext(), thucDon, Toast.LENGTH_SHORT).show();
                                    MyRetrofit.api.addDonHang(new DonHang(finalTen, sdt, diachi, thucDon, finalMoney, thanhToan, ngayDatHang, 0)).enqueue(new Callback<DonHang>() {
                                        @Override
                                        public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                                            if (response.body() != null){
                                                MainActivity.listGioHang.clear();
                                                listTenSpDonHang.clear();
                                                adapter.notifyDataSetChanged();
                                                TotalMoney();
                                                dialog.dismiss();
                                                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                                transaction.replace(R.id.fragment_gio_hang, DonHangFragment.newInstance());
                                                transaction.commit();
                                                Toast.makeText(getContext().getApplicationContext(), "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(getContext().getApplicationContext(), "Không thêm được đơn hàng", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DonHang> call, Throwable t) {
                                            Toast.makeText(getContext().getApplicationContext(), "Call api error while add don hang", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "onFailure: " + t);
                                        }
                                    });
                                }
                            }
                        }
                    });

                    btnHuyBo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            listTenSpDonHang.clear();
                        }
                    });
                    dialog.show();

                }else{
                    CheckConnection.ShowToast(getActivity(), "Không có sản phẩm thanh toán");
                }
            }
        });
    }

    private void DeleteProduct() {
        lvDs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xóa sản phẩm");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Chắc chắn xóa sản phẩm này?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.listGioHang.remove(position);
                        adapter.notifyDataSetChanged();
                        TotalMoney();
                        if (MainActivity.listGioHang.size() <= 0){
                            tvThongBao.setVisibility(View.VISIBLE);
                        }
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TotalMoney();
                    }
                });

                builder.show();
                return true;
            }
        });
    }

    public static void TotalMoney() {
        int money = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            money += MainActivity.listGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText("đ"+decimalFormat.format(money));
    }

    private void CheckData() {
        if (MainActivity.listGioHang.size() <= 0){
            tvThongBao.setVisibility(View.VISIBLE);
            lvDs.setVisibility(View.INVISIBLE);
        }else{
            tvThongBao.setVisibility(View.INVISIBLE);
            lvDs.setVisibility(View.VISIBLE);
        }
    }

    private void AnhXa(View view) {
        lvDs = view.findViewById(R.id.lvGioHang);
        tvThongBao = view.findViewById(R.id.tvThongBao);
        tvTongTien = view.findViewById(R.id.tvGiaGioHang);
        btnDatMua = view.findViewById(R.id.btnThanhToanGioHang);
        adapter = new GioHangAdapter(getActivity(), MainActivity.listGioHang);
        lvDs.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        CheckData();
    }
}