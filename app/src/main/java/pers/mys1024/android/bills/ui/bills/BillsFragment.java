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

        // 获取 BillsViewModel
        BillsViewModel billsViewModel = BillsViewModel.getInstance(getContext());

        // 观察 BillsViewModel 的数据变化
        billsViewModel.getBills().observe(getViewLifecycleOwner(), rvAdapter::updateBills);
        billsViewModel.getTotalIn().observe(
                getViewLifecycleOwner(),
                totalIn -> binding.tvTotalIn.setText(String.format(Locale.CHINA, "总收入：+¥%.2f", totalIn))
        );
        billsViewModel.getTotalOut().observe(
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