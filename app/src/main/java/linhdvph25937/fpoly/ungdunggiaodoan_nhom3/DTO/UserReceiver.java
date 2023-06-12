package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

import java.util.ArrayList;

public class UserReceiver {
    private ArrayList<NguoiDung> user;

    public UserReceiver(ArrayList<NguoiDung> user) {
        this.user = user;
    }

    public ArrayList<NguoiDung> getUser() {
        return user;
    }

    public void setUser(ArrayList<NguoiDung> user) {
        this.user = user;
    }
}
