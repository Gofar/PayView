package com.lcf.payview;

/**
 * @author lcf
 * @date 2018/2/23 15:37
 * @since 1.0
 */
public abstract class PayHandler {
    /**
     * 发起支付
     *
     * @param psd
     */
    public abstract String onPay(String psd) throws Exception;

    /**
     * 支付成功
     *
     * @param msg 支付信息
     */
    public abstract void onSuccess(String msg);

    /**
     * 支付失败
     *
     * @param msg 支付信息
     */
    public abstract void onFailed(Throwable msg);
}
