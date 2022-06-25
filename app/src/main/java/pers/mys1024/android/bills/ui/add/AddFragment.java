package pers.mys1024.android.bills.ui.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.Objects;

import pers.mys1024.android.bills.databinding.FragmentAddBinding;
import pers.mys1024.android.bills.db.entity.Bill;
import pers.mys1024.android.bills.ui.bills.BillsViewModel;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);

        // 获取 AddViewModel
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        // 监听数字键盘按钮的点击事件
        binding.btn0.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('0'));
        binding.btn1.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('1'));
        binding.btn2.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('2'));
        binding.btn3.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('3'));
        binding.btn4.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('4'));
        binding.btn5.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('5'));
        binding.btn6.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('6'));
        binding.btn7.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('7'));
        binding.btn8.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('8'));
        binding.btn9.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('9'));
        binding.btnDot.setOnClickListener(view -> addViewModel.pushDigitToMoneyText('.'));
        binding.btnDelete.setOnClickListener(view -> addViewModel.popDigitFromMoneyText());
        binding.btnReset.setOnClickListener(view -> addViewModel.resetMoneyText());
        binding.btnSure.setOnClickListener(view -> {
            // 获取 BillsViewModel 并插入一个 Bill
            BillsViewModel billsViewModel = BillsViewModel.getInstance(getActivity());
            billsViewModel.insertBill(new Bill(
                    null,
                    "晚餐",
                    new Date(),
                    Double.parseDouble(Objects.requireNonNull(addViewModel.getMoneyText().getValue())),
                    false
            ));
            // 重置
            addViewModel.resetMoneyText();
        });

        // 监听 AddViewModel 的 LiveData
        addViewModel.getMoneyText().observe(getViewLifecycleOwner(), moneyText -> {
            binding.moneyText.setText(moneyText);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}