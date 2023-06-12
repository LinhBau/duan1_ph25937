package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Activity.MainActivity;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.GioHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment.GioHangFragment;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> list;

    public GioHangAdapter(Context context, ArrayList<GioHang> list) {
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
        return list.get(i).getIdsp();
    }

    public class ViewHolder{
        ImageView imgGioHang;
        TextView tvTenGioHang, tvGiaGioHang;
        Button btnCong, btnTru, btnSoLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder bau = null;
        if (view == null){
            bau = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.layout.layout_item_gio_hang, null);

            bau.imgGioHang = view.findViewById(linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R.id.imgGioHang);
            bau.tvTenGioHang = view.findViewById(R.id.tvTenGioHang);
            bau.tvGiaGioHang = view.findViewById(R.id.tvGiaGioHang);
            bau.btnCong = view.findViewById(R.id.btnCongSanPham);
            bau.btnTru = view.findViewById(R.id.btnTruSanPham);
            bau.btnSoLuong = view.findViewById(R.id.btnSoLuongSanPham);
            view.setTag(bau);
        }else{
            bau = (ViewHolder) view.getTag();
        }
        bau.tvTenGioHang.setText(list.get(i).getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        bau.tvGiaGioHang.setText("đ"+decimalFormat.format(list.get(i).getGiasp()));
        Picasso.get().load(list.get(i).getHinhsp())
                .placeholder(R.drawable.home)
                .error(R.drawable.erro)
                .into(bau.imgGioHang);
        bau.btnSoLuong.setText(list.get(i).getSoluong()+"");

        int soluong = Integer.parseInt(bau.btnSoLuong.getText().toString());
        if (soluong >= 10){
            bau.btnCong.setVisibility(View.INVISIBLE);
        }else if(soluong <= 1){
            bau.btnTru.setVisibility(View.INVISIBLE);
        }else{
            bau.btnCong.setVisibility(View.VISIBLE);
            bau.btnTru.setVisibility(View.VISIBLE);
        }
        ViewHolder finalBau = bau;
        bau.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalBau.btnSoLuong.getText().toString())+1;
                int slht = MainActivity.listGioHang.get(i).getSoluong();
                long giaht = MainActivity.listGioHang.get(i).getGiasp();
                MainActivity.listGioHang.get(i).setSoluong(soluongmoinhat);
                long giamoinhat = (soluongmoinhat * giaht) / slht;
                MainActivity.listGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalBau.tvGiaGioHang.setText(decimalFormat.format(giamoinhat)+" Đ");
                GioHangFragment.TotalMoney();
                if (soluongmoinhat > 9){
                    finalBau.btnCong.setVisibility(View.INVISIBLE);
                    finalBau.btnTru.setVisibility(View.VISIBLE);
                    finalBau.btnSoLuong.setText(soluongmoinhat+"");
                }else{
                    finalBau.btnCong.setVisibility(View.VISIBLE);
                    finalBau.btnTru.setVisibility(View.VISIBLE);
                    finalBau.btnSoLuong.setText(soluongmoinhat+"");
                }
            }
        });


        bau.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalBau.btnSoLuong.getText().toString())-1;
                int slht = MainActivity.listGioHang.get(i).getSoluong();
                long giaht = MainActivity.listGioHang.get(i).getGiasp();
                MainActivity.listGioHang.get(i).setSoluong(soluongmoinhat);
                long giamoinhat = (soluongmoinhat * giaht) / slht;
                MainActivity.listGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalBau.tvGiaGioHang.setText(decimalFormat.format(giamoinhat)+" Đ");
                GioHangFragment.TotalMoney();
                if (soluongmoinhat < 2){
                    finalBau.btnCong.setVisibility(View.VISIBLE);
                    finalBau.btnTru.setVisibility(View.INVISIBLE);
                    finalBau.btnSoLuong.setText(soluongmoinhat+"");
                }else{
                    finalBau.btnCong.setVisibility(View.VISIBLE);
                    finalBau.btnTru.setVisibility(View.VISIBLE);
                    finalBau.btnSoLuong.setText(soluongmoinhat+"");
                }
            }
        });
        return view;
    }
}
