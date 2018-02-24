package com.lcf.payview;

import android.os.AsyncTask;

/**
 * @author lcf
 * @date 2018/2/23 16:32
 * @since 1.0
 */
public class PayHelper {
    private static PayHelper INSTANCE;
    private PayHandler mPayHandler;
    private IPayCallBack mCallBack;

    private PayHelper() {
    }

    public static PayHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PayHelper();
        }
        return INSTANCE;
    }

    public void initPayHandler(PayHandler handler) {
        this.mPayHandler = handler;
    }

    public void doPay(String psd, IPayCallBack callBack) {
        if (mPayHandler == null) {
            return;
        }
        this.mCallBack = callBack;
        new PayTask().execute(psd);
    }

    private class PayTask extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            Object result;
            try {
                result = mPayHandler.onPay(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
                result = e;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (o instanceof Throwable) {
                Throwable e = (Throwable) o;
                mCallBack.onFailed(e);
            } else if (o instanceof String) {
                String result = (String) o;
                mCallBack.onSuccess(result);
            }
        }
    }
}
