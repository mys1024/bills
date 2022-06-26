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

    public TagsViewModel() {
        mTags = new MutableLiveData<>(new ArrayList<>());
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

    public void refreshTags() {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            List<Tag> tags = tagDao.getAll();
            mTags.postValue(tags);
        });
    }

    /***********************************************************
     *************************** 其他 ***************************
     ***********************************************************/
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
        refreshTags();
    }
}