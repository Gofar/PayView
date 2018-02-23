package com.lcf.payview;

/**
 * @author lcf
 * @date 2018/2/23 16:32
 * @since 1.0
 */
public class PayHelper {
    private static PayHelper INSTANCE;
    private PayHandler mPayHandler;

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

    public void doPay(String psd) {
        if (mPayHandler == null) {
            return;
        }
        mPayHandler.onPay(psd);
    }
}
