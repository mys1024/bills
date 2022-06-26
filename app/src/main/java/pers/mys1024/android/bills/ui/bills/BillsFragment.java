package pers.mys1024.android.bills.ui.bills;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import pers.mys1024.android.bills.databinding.FragmentBillsBinding;
import pers.mys1024.android.bills.db.AppDatabase;

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
        BillsViewModel billsViewModel =
                new ViewModelProvider(this).get(BillsViewModel.class);
        billsViewModel.setBillDao(AppDatabase.getInstance(getActivity()).billDao());

        // 长按删除 Bill
        rvAdapter.onItemLongClick(bill -> {
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setMessage("是否要删除？");
            ab.setPositiveButton("确认", (a, b) -> {
                billsViewModel.deleteBill(bill);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            });
            ab.setNegativeButton("取消", (a, b) -> a.dismiss());
            ab.show();
        });

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