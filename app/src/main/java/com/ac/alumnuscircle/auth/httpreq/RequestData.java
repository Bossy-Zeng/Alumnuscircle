
/**
 * Created by 曾博晖 on 2016/9/4.
 * 对登陆时接收到的数据进行处理
 * @author 曾博晖
 * @date 2016年9月4日13:53:34
 * @verson 1
 *
 */

package com.ac.alumnuscircle.auth.httpreq;


/**
 * Created by 曾博晖 on 2016/9/4.
 * 对登陆时接收到的数据进行处理
 * @author 曾博晖
 * @date 2016年9月4日13:53:34
 * @verson 1
 *
 */
public class RequestData {
    private String message;
    private String code;
    private Data Data;
    public RequestData(String msg, String code, Data data){
        setMessage(msg);
        setCode(code);
        setData(data);
    }
    public void setMessage(String msg){this.message=msg;}
    public void setCode(String code){this.code=code;}
    public void setData(Data data ){this.Data=data;}

    public String getMessage(){
        return this.message;
    }
    public String getCode(){
        return this.code;
    }
    public Data getData(){
        return this.Data;
    }

    public class Data{
        private String _xsrf;
        public Data(String xsr){
            this._xsrf=xsr;
        }
        public String get_xsrf(){
            return _xsrf;
        }
    }
}
