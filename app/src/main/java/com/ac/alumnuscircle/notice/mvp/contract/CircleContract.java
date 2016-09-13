package com.ac.alumnuscircle.notice.mvp.contract;

import com.ac.alumnuscircle.beans.CircleItem;
import com.ac.alumnuscircle.beans.CommentConfig;
import com.ac.alumnuscircle.beans.CommentItem;
import com.ac.alumnuscircle.beans.FavortItem;

import java.util.List;

/**
 * @author 白洋
 */
public interface CircleContract {

    interface View extends BaseView{
        void update2DeleteCircle(String circleId);
        void update2AddFavorite(int circlePosition, FavortItem addItem);
        void update2DeleteFavort(int circlePosition, String favortId);
        void update2AddComment(int circlePosition, CommentItem addItem);
        void update2DeleteComment(int circlePosition, String commentId);
        void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig);
        void update2loadData(int loadType, List<CircleItem> datas);
    }

    interface Presenter extends BasePresenter {
        void loadData(int loadType, List<CircleItem> data);
        void deleteCircle(final String circleId);
        void addFavort(final int circlePosition);
        void deleteFavort(final int circlePosition, final String favortId);
        void deleteComment(final int circlePosition, final String commentId);

    }
}
