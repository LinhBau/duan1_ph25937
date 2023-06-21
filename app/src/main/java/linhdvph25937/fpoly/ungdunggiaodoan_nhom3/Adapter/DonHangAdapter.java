package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.GioHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil.MyRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.BauHolder>{
    private Context context;
    private ArrayList<DonHang> list;
    private SharedPreferences sharedPreferences;

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_don_hang, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        DonHang obj = list.get(position);
        if (obj == null){
            return;
        }

        holder.tvTen.setText(obj.getTenNguoiDung());
        holder.tvSdt.setText(obj.getSoDienThoai());
        holder.tvDiaChi.setText(obj.getDiaChiNhan());
        holder.tvNgayDat.setText(obj.getNgayDatMua());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvTongTien.setText("đ"+decimalFormat.format(obj.getTongTien()));
        holder.tvThanhToan.setText(obj.getThanhToan());
        if (obj.getTrangthai() == 0){
            holder.tvTrangThai.setText("Đang giao hàng");
            holder.btnXoa.setVisibility(View.VISIBLE);
        }else{
            holder.tvTrangThai.setText("Giao hàng thành công");
            holder.btnXoa.setVisibility(View.GONE);
        }
        holder.tvThucDon.setText(obj.getThucDon());
        //Nếu người đăng nhập là admin thì cho phép cập nhật trạng thái giao hàng
        if (sharedPreferences != null && sharedPreferences.getString("usernamelogin", "").equals("admin")){
            holder.chkAdmin.setVisibility(View.VISIBLE);
            holder.btnXoa.setVisibility(View.GONE);
        }

        //Set trạng thái cho checkbox
        if(obj.getTrangthai() == 0){
            holder.chkAdmin.setChecked(false);
        }else{
            holder.chkAdmin.setChecked(true);
        }

        holder.chkAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.layoutItemDonHang.setBackgroundColor(Color.GRAY);
                    MyRetrofit.api.updateStatusDonHang(obj.getId(), 1).enqueue(new Callback<DonHang>() {
                        @Override
                        public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                            if (response.body() != null){
                                obj.setTrangthai(1);
                                Toast.makeText(context, "Cập nhật trạng thái giao hàng thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Không thể cập nhật trạng thái giao hàng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonHang> call, Throwable t) {
                            Toast.makeText(context, "Call api error while update status don hang", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    holder.layoutItemDonHang.setBackgroundColor(Color.WHITE);
                    MyRetrofit.api.updateStatusDonHang(obj.getId(), 0).enqueue(new Callback<DonHang>() {
                        @Override
                        public void onResponse(Call<DonHang> call, Response<DonHang> response) {
                            if (response.body() != null){
                                obj.setTrangthai(0);
                            }else{
                                Toast.makeText(context, "Không thể cập nhật trạng thái giao hàng", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonHang> call, Throwable t) {
                            Toast.makeText(context, "Call api error while update status don hang", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });



//        TenSpDonHangVerticalAdapter tenSpDonHangVerticalAdapter = new TenSpDonHangVerticalAdapter(context, GioHangFragment.listThucDon);
//        holder.rvDs.setLayoutManager(new LinearLayoutManager(context));
//        holder.rvDs.setAdapter(tenSpDonHangVerticalAdapter);
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Hủy đơn hàng");
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setMessage("Chắc chắn hủy đơn hàng");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Huỷ thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvTen, tvSdt, tvDiaChi, tvNgayDat, tvTongTien, tvThanhToan, tvTrangThai, tvThucDon;
        private RecyclerView rvDs;
        private ImageView btnXoa;
        private CheckBox chkAdmin;
        private CardView layoutItemDonHang;
        public BauHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenNguoiDungDonHang);
            tvSdt = itemView.findViewById(R.id.tvSoDienThoaiDonHang);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChiNhanDonHang);
            tvNgayDat = itemView.findViewById(R.id.tvNgayDatHangDonHang);
            tvTongTien = itemView.findViewById(R.id.tvTongTienDonHang);
            tvThanhToan = itemView.findViewById(R.id.tvThanhToanDonHang);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThaiDonHang);
            tvThucDon = itemView.findViewById(R.id.tvThucDon);
//            rvDs = itemView.findViewById(R.id.rvDsThucDonDonHang);
            btnXoa = itemView.findViewById(R.id.btnXoaDonHang);
            chkAdmin = itemView.findViewById(R.id.chkAdmin);
            sharedPreferences = itemView.getContext().getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
            layoutItemDonHang = itemView.findViewById(R.id.layoutItemDonHang);
        }
    }
}