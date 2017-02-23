package com.demo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by asielgil on 2/22/17.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {

        }
    }

    public void showLoadingDialog() {

        if (!isFinishing()) {
            if (progressDialog == null)
                progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

    }

    public void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
