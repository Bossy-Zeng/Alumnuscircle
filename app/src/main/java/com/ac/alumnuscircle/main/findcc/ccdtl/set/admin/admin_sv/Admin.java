/**
 * @author 白洋
 * @Date 2016 8.30
 * 管理管理员的类
 */

package com.ac.alumnuscircle.main.findcc.ccdtl.set.admin.admin_sv;

/**
 * Created by 15359 on 2016/8/29.
 */
public class Admin {
    private String adminImgRes;
    private String adminName;

    public void setAdminImg(String adminImg)
    {
        this.adminImgRes = adminImg;
    }
    public String getAdminImg()
    {
        return adminImgRes;
    }

    public void setAdminName(String adminName)
    {
        this.adminName = adminName;
    }
    public String getAdminName()
    {
        return adminName;
    }
}
