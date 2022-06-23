package pers.mys1024.android.bills.ui.bills;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pers.mys1024.android.bills.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {

    private FragmentBillsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BillsViewModel billsViewModel =
                new ViewModelProvider(this).get(BillsViewModel.class);

        binding = FragmentBillsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView rvBills = binding.rvBills;
        BillItemAdapter adapter = new BillItemAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(linearLayoutManager);
        rvBills.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}