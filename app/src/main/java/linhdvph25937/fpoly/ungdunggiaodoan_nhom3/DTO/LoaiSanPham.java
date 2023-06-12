package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO;

public class LoaiSanPham {
    private int id;
    private String tenLoai;
    private String hinhAnh;

    public LoaiSanPham(int id, String tenLoai, String hinhAnh) {
        this.id = id;
        this.tenLoai = tenLoai;
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public LoaiSanPham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
