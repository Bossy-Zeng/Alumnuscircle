/**
 * @author 吴正凡
 * @date 16.08.26
 * 我的页面中，加入的圈子和管理的圈子中的子项。
 */

package com.ac.alumnuscircle.main.mine.minecontent.rvcircle;

public class CircleItem {

    private String circleImgUrl;
    private String circleName;

    public CircleItem(String url, String name){
        this.circleImgUrl=url;
        this.circleName=name;
    }

    public String getCircleName() {
        return circleName;
    }

    public String getCircleImgUrl() {
        return circleImgUrl;
    }
}
