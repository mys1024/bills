package pers.mys1024.android.bills.ui.tags;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pers.mys1024.android.bills.ThreadPoolManager;
import pers.mys1024.android.bills.db.dao.TagDao;
import pers.mys1024.android.bills.db.entity.Tag;

public class TagsViewModel extends ViewModel {

    private TagDao tagDao;
    private final MutableLiveData<List<Tag>> mTags;
    private final MutableLiveData<Boolean> mIn;

    public TagsViewModel() {
        mTags = new MutableLiveData<>(new ArrayList<>());
        mIn = new MutableLiveData<>(false);
    }

    /***********************************************************
     ************************** mTags **************************
     ***********************************************************/
    public LiveData<List<Tag>> getTags() {
        return mTags;
    }

    public void insertTag(Tag tag) {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            tagDao.insert(tag);
            refreshTags();
        });
    }

    public void deleteTag(Tag tag) {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            tagDao.delete(tag);
            refreshTags();
        });
    }

    private void refreshTags() {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            boolean in = Boolean.TRUE.equals(mIn.getValue());
            List<Tag> tags = new ArrayList<>();
            for (Tag tag : tagDao.getAll()) {
                if (tag.getIn() == in) {
                    tags.add(tag);
                }
            }
            mTags.postValue(tags);
        });
    }

    /***********************************************************
     *************************** mIn ***************************
     ***********************************************************/
    public LiveData<Boolean> getIn() {
        return mIn;
    }

    public void setIn(boolean in) {
        mIn.setValue(in);
        refreshTags();
    }

    /***********************************************************
     *************************** 其他 ***************************
     ***********************************************************/
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
        refreshTags();
    }
}