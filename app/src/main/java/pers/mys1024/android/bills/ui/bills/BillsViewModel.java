package pers.mys1024.android.bills.ui.bills;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pers.mys1024.android.bills.ThreadPoolManager;
import pers.mys1024.android.bills.db.dao.BillDao;
import pers.mys1024.android.bills.db.entity.Bill;

public class BillsViewModel extends ViewModel {
    private BillDao billDao;
    private final MutableLiveData<List<Bill>> mBills;
    private final MutableLiveData<Double> mTotalIn;
    private final MutableLiveData<Double> mTotalOut;

    public BillsViewModel() {
        mBills = new MutableLiveData<>(new ArrayList<>());
        mTotalIn = new MutableLiveData<>(0.0);
        mTotalOut = new MutableLiveData<>(0.0);
    }

    /***********************************************************
     ************************** mBills *************************
     ***********************************************************/
    public LiveData<List<Bill>> getBills() {
        return mBills;
    }

    public void insertBill(Bill bill) {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            billDao.insert(bill);
            refreshAll();
        });
    }

    public void deleteBill(Bill bill) {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            billDao.delete(bill);
            refreshAll();
        });
    }

    /***********************************************************
     ************************* mTotalIn ************************
     ***********************************************************/
    public MutableLiveData<Double> getTotalIn() {
        return mTotalIn;
    }

    /***********************************************************
     ************************ mTotalOut ************************
     ***********************************************************/
    public MutableLiveData<Double> getTotalOut() {
        return mTotalOut;
    }

    /***********************************************************
     *************************** 其他 ***************************
     ***********************************************************/
    public void setBillDao(BillDao billDao) {
        this.billDao = billDao;
        refreshAll();
    }

    private void refreshAll() {
        ThreadPoolManager.getCacheThreadPool().submit(() -> {
            List<Bill> bills = billDao.getDescAll();
            mBills.postValue(bills);
            double totalIn = 0;
            double totalOut = 0;
            for (Bill bill : bills) {
                if (bill.getIn()) {
                    totalIn += bill.getMoney();
                } else {
                    totalOut += bill.getMoney();
                }
            }
            mTotalIn.postValue(totalIn);
            mTotalOut.postValue(totalOut);
        });
    }
}
