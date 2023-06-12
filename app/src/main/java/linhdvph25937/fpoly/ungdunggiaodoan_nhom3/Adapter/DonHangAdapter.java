package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.GioHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.BauHolder>{
    private Context context;
    private ArrayList<DonHang> list;

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
        TenSpDonHangVerticalAdapter tenSpDonHangVerticalAdapter = new TenSpDonHangVerticalAdapter(context, GioHangFragment.listThucDon);
        holder.rvDs.setLayoutManager(new LinearLayoutManager(context));
        holder.rvDs.setAdapter(tenSpDonHangVerticalAdapter);
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
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Lí do hủy đơn hàng");
                        String[] reasons = {"Đổi ý không muốn mua nữa","Muốn thay đổi sản phẩm",
                                "Thời gian gửi hàng lâu","Tìm thấy giá rẻ hơn ở chỗ khác",
                                "Thủ tục thanh toán rắc rối","Muốn thay đổi địa chỉ giao hàng"};
                        builder1.setMultiChoiceItems(reasons,null,(click,position,status) ->{
                        });

                        builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                list.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Huỷ thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder1.show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
        private TextView tvTen, tvSdt, tvDiaChi, tvNgayDat, tvTongTien, tvThanhToan, tvTrangThai;
        private RecyclerView rvDs;
        private ImageView btnXoa;
        public BauHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenNguoiDungDonHang);
            tvSdt = itemView.findViewById(R.id.tvSoDienThoaiDonHang);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChiNhanDonHang);
            tvNgayDat = itemView.findViewById(R.id.tvNgayDatHangDonHang);
            tvTongTien = itemView.findViewById(R.id.tvTongTienDonHang);
            tvThanhToan = itemView.findViewById(R.id.tvThanhToanDonHang);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThaiDonHang);
            rvDs = itemView.findViewById(R.id.rvDsThucDonDonHang);
            btnXoa = itemView.findViewById(R.id.btnXoaDonHang);
        }
    }
}