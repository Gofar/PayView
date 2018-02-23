package com.lcf.payview.keyboard;

/**
 * @author lcf
 * @date 2018/2/5 11:04
 * @since 1.0
 */
public interface OnKeyboardClickListener {
    /**
     * 按数字键
     * @param text
     */
    void onTextClick(String text);
    /**
     * 按删除键
     */
    void onDeleteClick();
}
