package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

import java.util.ArrayList;

public class DonHangReceiver {
    private ArrayList<DonHang> list;

    public DonHangReceiver(ArrayList<DonHang> list) {
        this.list = list;
    }

    public ArrayList<DonHang> getList() {
        return list;
    }

    public void setList(ArrayList<DonHang> list) {
        this.list = list;
    }
}
