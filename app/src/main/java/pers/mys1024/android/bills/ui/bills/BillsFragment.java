package pers.mys1024.android.bills.ui.bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import pers.mys1024.android.bills.databinding.FragmentBillsBinding;
import pers.mys1024.android.bills.db.AppDatabase;
import pers.mys1024.android.bills.db.dao.BillDao;

public class BillsFragment extends Fragment {

    private FragmentBillsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);

        // 初始化 RecyclerView
        final RecyclerView rvBills = binding.rvBills;
        BillItemAdapter rvAdapter = new BillItemAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(linearLayoutManager);
        rvBills.setAdapter(rvAdapter);

        // 获取 ViewModel
        BillDao billDao = AppDatabase.getInstance(getContext()).billDao();
        BillsViewModel viewModel = BillsViewModel.getInstance();
        viewModel.setBillDao(billDao);

        // 观察 ViewModel 的数据变化
        viewModel.getBills().observe(getViewLifecycleOwner(), rvAdapter::updateBills);
        viewModel.getTotalIn().observe(
                getViewLifecycleOwner(),
                totalIn -> binding.tvTotalIn.setText(String.format(Locale.CHINA, "总收入：+¥%.2f", totalIn))
        );
        viewModel.getTotalOut().observe(
                getViewLifecycleOwner(),
                totalOut -> binding.tvTotalOut.setText(String.format(Locale.CHINA, "总支出：-¥%.2f", totalOut))
        );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}