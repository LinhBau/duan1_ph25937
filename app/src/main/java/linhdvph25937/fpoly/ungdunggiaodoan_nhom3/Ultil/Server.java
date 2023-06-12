package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class Server {
    public static String localhost = "https://bauvn.000webhostapp.com";
    public static String linkLoaiSp = localhost + "/duan1/getloaisanpham.php";
    public static String linkSanPham = localhost + "/duan1/getsanpham.php?page=";
    public static String linkSanPhamMoiNhat = localhost + "/duan1/getspmoinhat.php";
    public static String linkThongTinNguoiDung = localhost + "/duan1/thongtinkhachhang.php";
    public static String linkGetThongTinNguoiDung = localhost + "/duan1/getthongtinkhachhang.php";
    public static String linkDonHang = localhost + "/duan1/donhang.php";


}
