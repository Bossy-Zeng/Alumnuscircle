package com.ac.alumnuscircle.notice.mvp.contract;

/**
 * @author 白洋
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);


}
