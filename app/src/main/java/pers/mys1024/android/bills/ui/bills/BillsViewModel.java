package pers.mys1024.android.bills.ui.bills;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import pers.mys1024.android.bills.db.AppDatabase;
import pers.mys1024.android.bills.db.dao.BillDao;
import pers.mys1024.android.bills.db.entity.Bill;

public class BillsViewModel extends ViewModel {
    private static BillsViewModel singleton;

    public static BillsViewModel getInstance(Context context) {
        if (singleton == null) {
            singleton = new BillsViewModel(AppDatabase.getInstance(context).billDao());
            singleton.refreshAll();
        }
        return singleton;
    }

    private final BillDao billDao;
    private final MutableLiveData<List<Bill>> mBills;
    private final MutableLiveData<Double> mTotalIn;
    private final MutableLiveData<Double> mTotalOut;

    private BillsViewModel(BillDao billDao) {
        this.billDao = billDao;
        mBills = new MutableLiveData<>(new ArrayList<>());
        mTotalIn = new MutableLiveData<>(0.0);
        mTotalOut = new MutableLiveData<>(0.0);
    }

    public void refreshAll() {
        new Thread(() -> {
            List<Bill> bills = billDao.getAll();
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
        }).start();
    }

    public void insertBill(Bill bill) {
        new Thread(() -> {
            billDao.insert(bill);
            refreshAll();
        }).start();
    }

    public LiveData<List<Bill>> getBills() {
        return mBills;
    }

    public MutableLiveData<Double> getTotalIn() {
        return mTotalIn;
    }

    public MutableLiveData<Double> getTotalOut() {
        return mTotalOut;
    }
}