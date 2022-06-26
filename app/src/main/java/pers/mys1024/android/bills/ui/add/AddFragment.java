package pers.mys1024.android.bills.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        // 监听按钮的点击事件
        binding.btn0.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('0'));
        binding.btn1.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('1'));
        binding.btn2.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('2'));
        binding.btn3.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('3'));
        binding.btn4.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('4'));
        binding.btn5.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('5'));
        binding.btn6.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('6'));
        binding.btn7.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('7'));
        binding.btn8.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('8'));
        binding.btn9.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('9'));
        binding.btnDot.setOnClickListener(view -> addViewModel.pushDigitToMoneyDigitText('.'));
        binding.btnDelete.setOnClickListener(view -> addViewModel.popDigitFromMoneyDigitText());
        binding.btnReset.setOnClickListener(view -> addViewModel.resetMoneyDigitText());
        binding.tvGw.setOnClickListener(view -> addViewModel.setIn(false));
        binding.tvHf.setOnClickListener(view -> addViewModel.setIn(true));
        binding.btnSure.setOnClickListener(view -> {
            // 获取 BillsViewModel 并插入一个 Bill
            BillsViewModel billsViewModel = BillsViewModel.getInstance(getActivity());
            billsViewModel.insertBill(new Bill(
                    null,
                    "晚餐",
                    new Date(),
                    Double.parseDouble(Objects.requireNonNull(addViewModel.getMoneyDigitText().getValue())),
                    addViewModel.getIn().getValue()
            ));
            // 重置
            addViewModel.reset();
            // 弹出气泡提醒
            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
        });

        // 监听 AddViewModel 的 LiveData
        addViewModel.getMoneyText().observe(
                getViewLifecycleOwner(),
                moneyText -> binding.moneyText.setText(moneyText)
        );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}