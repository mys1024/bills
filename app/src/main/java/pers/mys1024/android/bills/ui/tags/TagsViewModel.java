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
    private final MutableLiveData<List<Tag>> mOutTags;
    private final MutableLiveData<List<Tag>> mInTags;
    private final MutableLiveData<Boolean> mIn;

    public TagsViewModel() {
        mTags = new MutableLiveData<>(new ArrayList<>());
        mOutTags = new MutableLiveData<>(new ArrayList<>());
        mInTags = new MutableLiveData<>(new ArrayList<>());
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
            refreshAllTags();
        });
    }

    public void deleteTag(Tag tag) {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            tagDao.delete(tag);
            refreshAllTags();
        });
    }

    /***********************************************************
     ************************* mOutTags ************************
     ***********************************************************/
    public LiveData<List<Tag>> getOutTags() {
        return mOutTags;
    }

    /***********************************************************
     ************************* mInTags *************************
     ***********************************************************/
    public LiveData<List<Tag>> getInTags() {
        return mInTags;
    }

    /***********************************************************
     *************************** mIn ***************************
     ***********************************************************/
    public LiveData<Boolean> getIn() {
        return mIn;
    }

    public void setIn(boolean in) {
        if (in == Boolean.TRUE.equals(mIn.getValue())) {
            return;
        }
        mIn.setValue(in);
        refreshAllTags();
    }

    /***********************************************************
     *************************** 其他 ***************************
     ***********************************************************/
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
        refreshAllTags();
    }

    private void refreshAllTags() {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            List<Tag> tags = tagDao.getAll();
            List<Tag> outTags = new ArrayList<>();
            List<Tag> inTags = new ArrayList<>();
            for (Tag tag : tags) {
                (tag.getIn() ? inTags : outTags).add(tag);
            }
            mTags.postValue(tags);
            mOutTags.postValue(outTags);
            mInTags.postValue(inTags);
        });
    }
}