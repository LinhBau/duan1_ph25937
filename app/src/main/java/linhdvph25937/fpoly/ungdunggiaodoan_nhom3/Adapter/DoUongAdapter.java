package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPham;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class DoUongAdapter extends BaseAdapter  {
    private Context context;
    private ArrayList<SanPham> list;
    private ArrayList<SanPham> listOld;

    public DoUongAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
        this.listOld = list;
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

    public class ViewHolder {
        ImageView img;
        TextView tvTen, tvGia, tvDiaChi;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder bau = null;
        if (view == null) {
            bau = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_item_do_uong, null);
            bau.img = view.findViewById(R.id.imgDoUongOneRow);
            bau.tvTen = view.findViewById(R.id.tvTenDoUong);
            bau.tvGia = view.findViewById(R.id.tvGiaDoUong);
            bau.tvDiaChi = view.findViewById(R.id.tvDiaChiDoUong);
            view.setTag(bau);
        } else {
            bau = (ViewHolder) view.getTag();
        }
        SanPham obj = list.get(i);
        bau.tvTen.setText(obj.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        bau.tvGia.setText(decimalFormat.format(obj.getGia()) + " đ");
        bau.tvDiaChi.setMaxLines(1);
        bau.tvDiaChi.setEllipsize(TextUtils.TruncateAt.END);
        bau.tvDiaChi.setText(obj.getDiaChi());
        Picasso.get().load(obj.getHinhAnh())
                .placeholder(R.drawable.home)
                .error(R.drawable.ic_baseline_error_24)
                .into(bau.img);
        return view;
    }
}