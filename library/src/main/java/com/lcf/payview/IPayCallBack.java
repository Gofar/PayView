package com.lcf.payview;

/**
 * @author lcf
 * @date 2018/2/24 10:13
 * @since 1.0
 */
public interface IPayCallBack {
    void onSuccess(String msg);

    void onFailed(Throwable e);
}
