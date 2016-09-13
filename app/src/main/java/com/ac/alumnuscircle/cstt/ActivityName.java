/**
 * @author 吴正凡
 * @date 16.08.28
 * @version 2
 * 功能：这个类封装了Activity在AndroidManifest注册时的name，可以在Intent隐式调用时用到。
 *
 */

package com.ac.alumnuscircle.cstt;

public class ActivityName {

    /**
     * main包下的Activity名称。
     */
    public static final String main_MainAct ="com.ac.alumnuscircle.main.MainAct";

    /**
     * home包下的Activity名称。
     */
    public static final String create_cc_CreateCoverAct = "com.ac.alumnuscircle.main.home.create_cc.CreateCoverAct";
    public static final String create_cc_CreateMainInfoAct= "com.ac.alumnuscircle.main.home.create_cc.CreateMainInfo";


    /**
     * ctc包下的Activity名称。
     */
    //人脉详情
    public static final String ctc_ContactDetailAct =
            "com.ac.alumnuscircle.main.ctc.ContactDetailAct";
    //高级筛选
    public static final String hlyflt_HighlyFilterAct =
            "com.ac.alumnuscircle.main.ctc.hlyflt.HighlyFilterAct";
    //模糊搜索
    public static final String ctc_FuzzySearchAct=
            "com.ac.alumnuscircle.main.ctc.FuzzySearchAct";
    /**
     * findcc包下的Activity名称。
     */
    public static final String set_ResetNameHdimg = "com.ac.alumnuscircle.main.findcc.ccdtl.set.ResetNameHdimg";
    public static final String fccdtl_FindCricleDetailAct = "com.ac.alumnuscircle.main.findcc.fccdtl.FindCircleDetailAct";
    public static final String admin_AdminAct = "com.ac.alumnuscircle.main.findcc.ccdtl.set.admin.AdminAct";

    /**
     * msg包下的Activity名称。
     */

    /**
     * notice包下的Activity名称。
     */
    public static final String notice_IssueNoticeAct = "com.ac.alumnuscircle.notice.activity.IssueNoticeAct";
    public static final String notice_NoticeDetailAct = "com.ac.alumnuscircle.notice.activity.NoticeDetailAct";
    public static final String notice_NoticeAct = "com.ac.alumnuscircle.notice.activity.NoticeAct";


    /**
     * mine包下的Activity名称。
     */
    /**
     * mine包下的Activity名称。
     */
    public static final String mine_SettingAct = "com.ac.alumnuscircle.main.mine.SettingAct";

    /**
     * auth包下的Activity名称。
     */

      public static final String register_AuthMajor=
            "com.ac.alumnuscircle.auth.register.AuthMajor";
      public static final String register_AuthName=
              "com.ac.alumnuscircle.auth.register.AuthName";
      public static final String register_AuthPhone=
              "com.ac.alumnuscircle.auth.register.AuthPhone";
      public static final String register_AuthWork=
              "com.ac.alumnuscircle.auth.register.AuthWork";
      public static final String register_AuthHeadImg=
              "com.ac.alumnuscircle.auth.register.AuthHeadImg";

    /**
     * web包下的Activity名称。
     */
    public static final String web_BaseWebAct = "com.ac.alumnuscircle.web.BaseWebAct";

    /**
     * supercamera包下的Activity名称。
     */
    public static final String normalcamera_PaPaCamera =
            "com.ac.alumnuscircle.supercamera.normalcamera.PaPaCamera";
    public static final String normalcamera_PaPaCrop =
            "com.ac.alumnuscircle.supercamera.normalcamera.PaPaCrop";
    public static final String onetoonecamera_PaPaCamera =
            "com.ac.alumnuscircle.supercamera.onetoonecamera.PaPaCamera";
    public static final String onetoonecamera_PaPaCrop =
            "com.ac.alumnuscircle.supercamera.onetoonecamera.PaPaCrop";

}
