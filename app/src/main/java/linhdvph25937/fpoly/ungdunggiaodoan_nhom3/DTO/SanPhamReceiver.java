package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SanPhamReceiver {
    @SerializedName("list")
    private ArrayList<SanPham> list;

    public SanPhamReceiver(ArrayList<SanPham> list) {
        this.list = list;
    }

    public ArrayList<SanPham> getList() {
        return list;
    }

    public void setList(ArrayList<SanPham> list) {
        this.list = list;
    }
}
