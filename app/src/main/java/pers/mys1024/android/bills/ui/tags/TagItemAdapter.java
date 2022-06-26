package pers.mys1024.android.bills.ui.tags;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.mys1024.android.bills.R;
import pers.mys1024.android.bills.db.entity.Tag;

public class TagItemAdapter extends RecyclerView.Adapter<TagHolder> {
    private List<Tag> tags;
    private final List<TagItemAdapterListener> longClickListeners = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public synchronized void updateTags(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    public void onItemLongClick(TagItemAdapterListener listener) {
        longClickListeners.add(listener);
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.tag_item,
                parent,
                false
        );
        TagHolder holder = new TagHolder(view);
        view.setOnLongClickListener(v -> {
            for (TagItemAdapterListener l : longClickListeners) {
                l.run(holder.getTag());
            }
            return false;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        holder.setTag(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return (tags == null) ? 0 : tags.size();
    }
}

class TagHolder extends RecyclerView.ViewHolder {
    private Tag tag;
    private final TextView tvTagName;

    public TagHolder(@NonNull View itemView) {
        super(itemView);
        this.tvTagName = itemView.findViewById(R.id.tv_tag_name);
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        tvTagName.setText(tag.getName());
    }

    public Tag getTag() {
        return tag;
    }
}

interface TagItemAdapterListener {
    void run(Tag tag);
}
