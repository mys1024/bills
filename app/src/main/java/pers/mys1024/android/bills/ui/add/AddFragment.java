package pers.mys1024.android.bills.ui.add;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.databinding.FragmentAddBinding;
import pers.mys1024.android.bills.db.AppDatabase;
import pers.mys1024.android.bills.db.entity.Bill;
import pers.mys1024.android.bills.db.entity.Tag;
import pers.mys1024.android.bills.ui.bills.BillsViewModel;
import pers.mys1024.android.bills.ui.tags.TagsViewModel;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);

        // 获取 AddViewModel
        AddViewModel addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);

        // 获取 BillsViewModel
        BillsViewModel billsViewModel =
                new ViewModelProvider(this).get(BillsViewModel.class);
        billsViewModel.setBillDao(AppDatabase.getInstance(getActivity()).billDao());

        // 获取 TagsViewModel
        TagsViewModel tagsViewModel =
                new ViewModelProvider(this).get(TagsViewModel.class);
        tagsViewModel.setTagDao(AppDatabase.getInstance(getActivity()).tagDao());

        // 设置 Spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<>()
        );
        binding.spinnerTag.setAdapter(arrayAdapter);

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
        binding.btnOut.setOnClickListener(view -> {
            binding.btnIn.setBackgroundColor(getResources().getColor(R.color.gray_500));
            binding.btnOut.setBackgroundColor(getResources().getColor(R.color.purple_500));
            binding.moneyText.setTextColor(getResources().getColor(R.color.red));
            addViewModel.setIn(false);
        });
        binding.btnIn.setOnClickListener(view -> {
            binding.btnIn.setBackgroundColor(getResources().getColor(R.color.purple_500));
            binding.btnOut.setBackgroundColor(getResources().getColor(R.color.gray_500));
            binding.moneyText.setTextColor(getResources().getColor(R.color.green));
            addViewModel.setIn(true);
        });
        binding.btnSure.setOnClickListener(view -> {
            // 插入一个 Bill
            Object selected = binding.spinnerTag.getSelectedItem();
            billsViewModel.insertBill(new Bill(
                    null,
                    selected == null ? "未分类" : selected.toString(),
                    new Date(),
                    Double.parseDouble(Objects.requireNonNull(addViewModel.getMoneyDigitText().getValue())),
                    addViewModel.getIn().getValue()
            ));
            // 重置
            addViewModel.resetMoneyDigitText();
            // 弹出气泡提醒
            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
        });

        // 监听 AddViewModel 的 LiveData
        addViewModel.getMoneyText().observe(
                getViewLifecycleOwner(),
                moneyText -> binding.moneyText.setText(moneyText)
        );
        addViewModel.getIn().observe(
                getViewLifecycleOwner(),
                in -> {
                    arrayAdapter.clear();
                    List<Tag> tags = (in ? tagsViewModel.getInTags() : tagsViewModel.getOutTags()).getValue();
                    assert tags != null;
                    for (Tag tag : tags) {
                        arrayAdapter.add(tag.getName());
                    }
                }
        );

        // 监听 TagsViewModel 的 LiveData
        tagsViewModel.getInTags().observe(
                getViewLifecycleOwner(),
                tags -> {
                    if (!Boolean.TRUE.equals(addViewModel.getIn().getValue())) {
                        return;
                    }
                    arrayAdapter.clear();
                    for (Tag tag : tags) {
                        arrayAdapter.add(tag.getName());
                    }
                }
        );
        tagsViewModel.getOutTags().observe(
                getViewLifecycleOwner(),
                tags -> {
                    if (Boolean.TRUE.equals(addViewModel.getIn().getValue())) {
                        return;
                    }
                    arrayAdapter.clear();
                    for (Tag tag : tags) {
                        arrayAdapter.add(tag.getName());
                    }
                }
        );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}