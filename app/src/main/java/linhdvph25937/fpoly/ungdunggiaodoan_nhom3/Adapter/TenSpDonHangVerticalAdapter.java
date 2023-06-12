package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class TenSpDonHangVerticalAdapter extends RecyclerView.Adapter<TenSpDonHangVerticalAdapter.BauHolder> {
    private Context context;
    private ArrayList<GioHang> listDonHang;

    public TenSpDonHangVerticalAdapter(Context context, ArrayList<GioHang> listDonHang) {
        this.context = context;
        this.listDonHang = listDonHang;
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ten_sp_chit_tiet_don_hang_vertical, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        GioHang obj = listDonHang.get(position);
        if (obj == null){
            return;
        }
        holder.tvTenSp.setText(" - "+obj.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setTextColor(Color.RED);
        holder.tvGia.setText(" (đ"+decimalFormat.format(obj.getGiasp())+")");
        holder.tvSoluong.setText(" Số lượng: "+obj.getSoluong());
    }

    @Override
    public int getItemCount() {
        if (listDonHang != null){
            return listDonHang.size();
        }
        return 0;
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSp, tvGia, tvSoluong;
        public BauHolder(@NonNull View itemView) {
            super(itemView);

            tvTenSp = itemView.findViewById(R.id.tvTenSanPhamDonHangCt);
            tvGia = itemView.findViewById(R.id.tvGiaSanPhamDonHangCt);
            tvSoluong = itemView.findViewById(R.id.tvSoLuongDonHangCt);
        }
    }
}
