package com.lcf.payview.keyboard;

/**
 * @author lcf
 * @date 2018/2/3 18:18
 * @since 1.0
 */
public class KeyboardItem {
    private int type;
    private String title;

    public KeyboardItem() {
    }

    public KeyboardItem(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
