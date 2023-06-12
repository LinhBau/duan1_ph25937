package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int id;
    @SerializedName("tensanpham")
    private String tenSp;
    @SerializedName("giasanpham")
    private int gia;
    @SerializedName("diachi")
    private String diaChi;
    @SerializedName("mota")
    private String moTa;
    @SerializedName("hinhanh")
    private String hinhAnh;
    @SerializedName("idloaisanpham")
    private int idLoaiSp;

    public SanPham(int id, String tenSp, int gia, String diaChi, String moTa, String hinhAnh, int idLoaiSp) {
        this.id = id;
        this.tenSp = tenSp;
        this.gia = gia;
        this.diaChi = diaChi;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.idLoaiSp = idLoaiSp;
    }

    public SanPham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getIdLoaiSp() {
        return idLoaiSp;
    }

    public void setIdLoaiSp(int idLoaiSp) {
        this.idLoaiSp = idLoaiSp;
    }
}
