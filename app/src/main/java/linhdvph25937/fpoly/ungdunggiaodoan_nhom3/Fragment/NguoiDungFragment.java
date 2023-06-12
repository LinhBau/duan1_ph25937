package linhdvph25937.fpoly.ungdunggiaodoan_nhom3.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import linhdvph25937.fpoly.ungdunggiaodoan_nhom3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NguoiDungFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NguoiDungFragment extends Fragment {

    public NguoiDungFragment() {
        // Required empty public constructor
    }


    public static NguoiDungFragment newInstance() {
        NguoiDungFragment fragment = new NguoiDungFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
    }
}