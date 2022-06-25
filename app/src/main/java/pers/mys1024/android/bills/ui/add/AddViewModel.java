package pers.mys1024.android.bills.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class AddViewModel extends ViewModel {

    private final MutableLiveData<String> mMoneyText;

    public AddViewModel() {
        mMoneyText = new MutableLiveData<>();
        mMoneyText.setValue("0");
    }

    public void pushDigitToMoneyText(char c) {
        String moneyText = mMoneyText.getValue();
        if (Objects.equals(moneyText, "0") && c != '.') {
            moneyText = "" + c;
        } else {
            moneyText += c;
        }
        mMoneyText.postValue(moneyText);
    }

    public void popDigitFromMoneyText() {
        String moneyText = mMoneyText.getValue();
        if (moneyText == null || moneyText.length() == 1) {
            moneyText = "0";
        } else {
            moneyText = moneyText.substring(0, moneyText.length() - 1);
        }
        mMoneyText.postValue(moneyText);
    }

    public void resetMoneyText() {
        mMoneyText.postValue("0");
    }

    public MutableLiveData<String> getMoneyText() {
        return mMoneyText;
    }
}