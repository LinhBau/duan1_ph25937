package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

import com.google.gson.annotations.SerializedName;

public class DonHang {
    private int id;
    @SerializedName("tennguoidung")
    private String tenNguoiDung;
    @SerializedName("sodienthoai")
    private String soDienThoai;
    @SerializedName("diachinhan")
    private String diaChiNhan;
    @SerializedName("thucdon")
    private String thucDon;
    @SerializedName("tongtien")
    private int tongTien;
    @SerializedName("thanhtoan")
    private String thanhToan;
    @SerializedName("ngaydatmua")
    private String ngayDatMua;
    @SerializedName("trangthai")
    private int trangthai;

    public DonHang() {
    }

    public DonHang(String tenNguoiDung, String soDienThoai, String diaChiNhan, String thucDon, int tongTien, String thanhToan, String ngayDatMua, int trangthai) {
        this.tenNguoiDung = tenNguoiDung;
        this.soDienThoai = soDienThoai;
        this.diaChiNhan = diaChiNhan;
        this.thucDon = thucDon;
        this.tongTien = tongTien;
        this.thanhToan = thanhToan;
        this.ngayDatMua = ngayDatMua;
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChiNhan() {
        return diaChiNhan;
    }

    public void setDiaChiNhan(String diaChiNhan) {
        this.diaChiNhan = diaChiNhan;
    }

    public String getThucDon() {
        return thucDon;
    }

    public void setThucDon(String thucDon) {
        this.thucDon = thucDon;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getNgayDatMua() {
        return ngayDatMua;
    }

    public void setNgayDatMua(String ngayDatMua) {
        this.ngayDatMua = ngayDatMua;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
