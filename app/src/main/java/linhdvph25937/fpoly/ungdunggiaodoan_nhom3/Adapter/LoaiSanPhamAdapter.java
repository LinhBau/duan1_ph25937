package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.LoaiSanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class LoaiSanPhamAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LoaiSanPham> list;

    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    public class ViewHolder{
        ImageView img;
        TextView tvTen;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder bau = null;
        if (view == null){
            bau = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.layout.layout_item_loai_san_pham, null);
            bau.img = view.findViewById(R.id.imgLoaiSanPham);
            bau.tvTen = view.findViewById(R.id.tvTenLoaiSanPham);
            view.setTag(bau);
        }else{
            bau = (ViewHolder) view.getTag();
        }
        LoaiSanPham obj = list.get(i);
        bau.tvTen.setText(obj.getTenLoai());
        Picasso.get().load(obj.getHinhAnh())
                .placeholder(R.drawable.home)
                .error(R.drawable.ic_baseline_error_24)
                .into(bau.img);
        return view;
    }
}

