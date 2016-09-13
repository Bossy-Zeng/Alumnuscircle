package com.ac.alumnuscircle.net;

/**
 * @author 白洋
 * 用来处理服务器的cookie请求
 */
public class CookieUtils {
    public static String _xsrf="";
    public static String uid ="" ;
    public static String cookie="";
    public static String _xsrfKey="";

    public static void Init_XsrfKey()
    {
        _xsrfKey = _xsrf.substring(6,_xsrf.length());
    }

    public static void Init_xsrf(String _xsrfGet)
    {
        _xsrf = _xsrfGet.substring(0,_xsrfGet.length()-8);
        Init_XsrfKey();
    }

    public static void Init_uid(String uidGet)
    {
        uid = uidGet.split(";")[0];

    }

    /**
     * 用于之后uid更新时的操作
     * @param uidGet
     *
     */
    public static void Init_Cookie(String uidGet)

    {

        if(_xsrf.equals(""))
            try {
                throw new Exception("没有获取_xsrf");
            } catch (Exception e) {
                e.printStackTrace();
            }
        Init_uid(uidGet);
        cookie = _xsrf+";"+uid;
    }


}
