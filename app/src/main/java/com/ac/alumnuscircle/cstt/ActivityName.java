/**
 * @author Zhengfan
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
    public static final String main_MainAct = "android.intent.action.MAIN";

    /**
     * home包下的Activity名称。
     */


    /**
     * ctc包下的Activity名称。
     */
    public static final String ctc_ContactDetailAct = "com.ac.alumnuscircle.main.ctc.ContactDetailAct";
    public static final String hlyflt_ContactHlyFltAct =
             "com.ac.alumnuscircle.main.ctc.hlyflt.ContactHlyFltAct";
    /**
     * findcc包下的Activity名称。
     */
    public static final String ccdtl_CircleDetailAct = "com.ac.alumnuscircle.main.findcc.ccdtl.CircleDetailAct";
    public static final String set_ResetNameHdimg = "com.ac.alumnuscircle.main.findcc.ccdtl.set.ResetNameHdimg";

    /**
     * msg包下的Activity名称。
     */


    /**
     * mine包下的Activity名称。
     */


    /**
     * auth包下的Activity名称。
     */


    /**
     * web包下的Activity名称。
     */
    public static final String web_BaseWebAct = "com.ac.alumnuscircle.web.BaseWebAct";
}
