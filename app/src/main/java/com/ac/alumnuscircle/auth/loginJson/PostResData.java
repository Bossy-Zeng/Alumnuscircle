
/**
 * Created by Administrator on 2016/9/4.
 * 对Post请求时发来的数据进行处理的第一步类
 * 2016年9月4日21:45:47
 * 曾博晖
 * 添加注释
 */
package com.ac.alumnuscircle.auth.loginJson;



public class PostResData  {
    
    private String message;
    private String code;
    private Data mData;
    public PostResData(String msg, String cod, Data data){
        this.message=msg;
        this.code=cod;
        this.mData=data;
    }
    public String getMessage(){return this.message;}
    public String getCode(){return this.code;}
    public Data getData(){return this.mData;}
    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data data) {
        this.mData = data;
    }

    /**
     * Created by Administrator on 2016/9/4.
     * 代表用户的详细信息的类
     * 2016年9月4日21:44:55
     * 曾博晖创建
     */

    public class Data{
        public response getResponse() {
            return response;
        }

        public void setResponse(response response) {
            this.response = response;
        }

        public update getUpdate() {
            return update;
        }

        public void setUpdate(update update) {
            this.update = update;
        }

        /**
         *{"update": {},
         * "response": {"last_update_time": "2016-08-28 17:11:37",
         * "major": "1", "uid": 14, "admission_year": 2014,
         * "instroduction": "{}", "create_circle_list": "_",
         * "city": "123", "icon_url": "default", "my_circle_list": "{}",
         * "state": "", "protect_contact_list": "{}", "detail_id": 14,
         * "company_publicity_level": 0, "public_contact_list": "{}",
         * "admin_circle_list": "_", "tags": "{}", "company": "google China",
         * "job": "student", "faculty": "71", "name": "\u9648\u96c4\u8f89",
         * "gender": 0, "job_list_level": 0, "country": "", "adlevel": 0,
         * "job_list": "{}"}}
         * */
         private update update;
         private response response;
        public Data(update update, response  response) {
            this.update = update;
            this.response = response;
        }




    }

}
