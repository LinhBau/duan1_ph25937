package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.ChiTietSanPhamActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class SanPhamMoiNhatAdapter extends RecyclerView.Adapter<SanPhamMoiNhatAdapter.BauHolder> implements Filterable {
    private Context context;
    private ArrayList<SanPham> list;
    private ArrayList<SanPham> listOld;

    public SanPhamMoiNhatAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SanPham> arr){
        this.list = arr;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BauHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sp_moi_nhat, parent, false);
        return new BauHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BauHolder holder, int position) {
        SanPham obj = list.get(position);
        if (obj == null){
            return;
        }

        holder.tvTen.setText(obj.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGia.setText("đ"+decimalFormat.format(obj.getGia()));
        Picasso.get().load(obj.getHinhAnh())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String timKiem = charSequence.toString();
                if (timKiem.isEmpty()){
                    list = listOld;
                }else{
                    ArrayList<SanPham> listSP = new ArrayList<>();
                    for (SanPham obj : listOld){
                        if (obj.getTenSp().toLowerCase().contains(timKiem.toLowerCase())){
                            listSP.add(obj);
                        }
                    }
                    list = listSP;
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class BauHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvTen, tvGia;
        public BauHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgSpMoiNhat);
            tvTen = itemView.findViewById(R.id.tvTenSpMoiNhat);
            tvGia = itemView.findViewById(R.id.tvGiaSpMoiNhat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinsanpham", list.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
