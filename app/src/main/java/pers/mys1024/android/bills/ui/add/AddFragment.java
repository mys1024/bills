package pers.mys1024.android.bills.ui.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;

import pers.mys1024.android.bills.databinding.FragmentAddBinding;
import pers.mys1024.android.bills.db.entity.Bill;
import pers.mys1024.android.bills.ui.bills.BillsViewModel;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        binding = FragmentAddBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.btnSure.setOnClickListener(view -> {
            // 获取 BillsViewModel 并插入一个 Bill
            BillsViewModel billsViewModel = BillsViewModel.getInstance(getContext());
            billsViewModel.insertBill(new Bill(null, "晚餐", new Date(), 15.0, false));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}