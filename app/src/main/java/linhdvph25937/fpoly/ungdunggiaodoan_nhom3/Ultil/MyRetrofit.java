package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Ultil;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHang;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.DonHangReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.NguoiDung;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.SanPhamReceiver;
import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.DTO.UserReceiver;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyRetrofit {
    //http://192.168.0.14:3000/
    MyRetrofit api = new Retrofit.Builder()
            .baseUrl("http://192.168.0.14:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyRetrofit.class);

    //Lấy danh sách và đăng kí tài khoản
    @GET("api/user")
    Call<UserReceiver> getAllUser();

    //Đăng nhập
    @POST("api/user/signin")
    Call<UserReceiver> signin(@Body NguoiDung nguoiDung);

    //Đăng kí
    @POST("api/user/signup")
    Call<NguoiDung> signup(@Body NguoiDung nguoiDung);

    //Cập nhật thông tin
    @POST("api/user/{id}")
    Call<NguoiDung> updateUser(@Body NguoiDung nguoiDung,
                               @Path("id") int id);

    //CRUD sản phẩm

    //Lấy danh sách sản phẩm theo loại sản phẩm
    @GET("api/sanpham/{id}")
    Call<SanPhamReceiver> getSanPhamByIdLoaiSp(@Path("id") int idSp);

    //Lấy ra 6 sản phẩm mới nhất
    @GET("api/sanpham/spnew")
    Call<SanPhamReceiver> getNewSanPham();

    //Đơn hàng
    @POST("api/donhang/add")
    Call<DonHang> addDonHang(@Body DonHang donHang);

    @GET("api/donhang/{username}")
    Call<DonHangReceiver> getDonHangByUsername(@Path("username") String name);
}
