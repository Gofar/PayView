package com.lcf.payview;

/**
 * @author lcf
 * @date 2018/2/24 10:10
 * @since 1.0
 */
@PayModule("pay")
public class PayHandlerImpl extends PayHandler {
    @Override
    public String onPay(String psd) throws Exception {
        return "";
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailed(Throwable e) {

    }
}
