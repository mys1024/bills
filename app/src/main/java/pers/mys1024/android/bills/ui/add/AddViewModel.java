package pers.mys1024.android.bills.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

public class AddViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mIn;
    private final MutableLiveData<String> mMoneyDigitText;
    private final MutableLiveData<String> mMoneyText;

    public AddViewModel() {
        mMoneyDigitText = new MutableLiveData<>("0");
        mIn = new MutableLiveData<>(false);
        mMoneyText = new MutableLiveData<>("-¥0");
    }

    /***********************************************************
     *************************** mIn ***************************
     ***********************************************************/
    public LiveData<Boolean> getIn() {
        return mIn;
    }

    public void setIn(boolean in) {
        mIn.setValue(in);
        updateMoneyText();
    }

    /***********************************************************
     ********************* mMoneyDigitText *********************
     ***********************************************************/
    public LiveData<String> getMoneyDigitText() {
        return mMoneyDigitText;
    }

    public void pushDigitToMoneyDigitText(char c) {
        String moneyText = mMoneyDigitText.getValue();
        if (Objects.equals(moneyText, "0") && c != '.') {
            moneyText = "" + c;
        } else if (c == '.') {
            if (moneyText != null && !moneyText.contains(".")) {
                moneyText += c;
            }
        } else {
            moneyText += c;
        }
        mMoneyDigitText.setValue(moneyText);
        updateMoneyText();
    }

    public void popDigitFromMoneyDigitText() {
        String moneyText = mMoneyDigitText.getValue();
        if (moneyText == null || moneyText.length() == 1) {
            moneyText = "0";
        } else {
            moneyText = moneyText.substring(0, moneyText.length() - 1);
        }
        mMoneyDigitText.setValue(moneyText);
        updateMoneyText();
    }

    /***********************************************************
     *********************** mMoneyText ************************
     ***********************************************************/
    public LiveData<String> getMoneyText() {
        return mMoneyText;
    }

    public void resetMoneyDigitText() {
        mMoneyDigitText.setValue("0");
        updateMoneyText();
    }

    private void updateMoneyText() {
        mMoneyText.setValue(
                (Boolean.TRUE.equals(mIn.getValue()) ? "+¥" : "-¥") + mMoneyDigitText.getValue()
        );
    }
}