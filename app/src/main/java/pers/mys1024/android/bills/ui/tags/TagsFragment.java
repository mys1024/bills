package pers.mys1024.android.bills.ui.tags;

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

import java.util.Objects;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.databinding.FragmentTagsBinding;
import pers.mys1024.android.bills.db.AppDatabase;
import pers.mys1024.android.bills.db.entity.Tag;

public class TagsFragment extends Fragment {

    private FragmentTagsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTagsBinding.inflate(inflater, container, false);

        // 获取 TagsViewModel
        TagsViewModel tagsViewModel =
                new ViewModelProvider(this).get(TagsViewModel.class);
        tagsViewModel.setTagDao(AppDatabase.getInstance(getActivity()).tagDao());

        // 初始化 RecyclerView
        final RecyclerView rvBills = binding.rvTags;
        TagItemAdapter rvAdapter = new TagItemAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(linearLayoutManager);
        rvBills.setAdapter(rvAdapter);

        // 长按删除 Tag
        rvAdapter.onItemLongClick(tag -> {
            AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
            ab.setMessage("是否要删除？");
            ab.setPositiveButton("确认", (a, b) -> {
                tagsViewModel.deleteTag(tag);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            });
            ab.setNegativeButton("取消", (a, b) -> a.dismiss());
            ab.show();
        });

        // 监听按钮点击事件
        binding.btnIn.setOnClickListener(view -> {
            binding.btnIn.setBackgroundColor(getResources().getColor(R.color.purple_500));
            binding.btnOut.setBackgroundColor(getResources().getColor(R.color.gray_500));
            tagsViewModel.setIn(true);
        });
        binding.btnOut.setOnClickListener(view -> {
            binding.btnIn.setBackgroundColor(getResources().getColor(R.color.gray_500));
            binding.btnOut.setBackgroundColor(getResources().getColor(R.color.purple_500));
            tagsViewModel.setIn(false);
        });
        binding.btnAddTag.setOnClickListener(view -> {
            String tagName = binding.etTagName.getText().toString();
            if (!tagName.equals("")) {
                tagsViewModel.insertTag(new Tag(null, tagName, tagsViewModel.getIn().getValue()));
                Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                binding.etTagName.getText().clear();
            }
        });

        // 监听 TagsViewModel 数据变化
        tagsViewModel.getIn().observe(getViewLifecycleOwner(), in -> {
            if (in) {
                rvAdapter.updateTags(Objects.requireNonNull(tagsViewModel.getInTags().getValue()));
            } else {
                rvAdapter.updateTags(Objects.requireNonNull(tagsViewModel.getOutTags().getValue()));
            }
        });
        tagsViewModel.getInTags().observe(
                getViewLifecycleOwner(),
                tags -> {
                    if (!Boolean.TRUE.equals(tagsViewModel.getIn().getValue())) {
                        return;
                    }
                    rvAdapter.updateTags(tags);
                }
        );
        tagsViewModel.getOutTags().observe(
                getViewLifecycleOwner(),
                tags -> {
                    if (Boolean.TRUE.equals(tagsViewModel.getIn().getValue())) {
                        return;
                    }
                    rvAdapter.updateTags(tags);
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