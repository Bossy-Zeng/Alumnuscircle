package com.ac.alumnuscircle.beans;

import java.util.List;

/**
 * 动态列表
 * @author 白洋
 */
public class NoticeList {


    /**
     * message : get umeng api successfully
     * code : 0
     * Data : {"update":{},"response":{"count":10,"total":2,"page":1,"results":[{"related_users":"empty","liked":"False","stats":{"liked":1,"forwards":0,"comments":8},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!","is_top":0,"create_time":"2016-09-10 00:33:17","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2e44dd36ef3fbfcb032e4","share_link":"http://wsq.umeng.com/feeds/57d2e44dd36ef3fbfcb032e4/"},{"related_users":"empty","liked":"False","stats":{"liked":0,"forwards":0,"comments":0},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条中文动态 62066","is_top":0,"create_time":"2016-09-10 00:11:06","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2df1ad36ef3fc508af255","share_link":"http://wsq.umeng.com/feeds/57d2df1ad36ef3fc508af255/"}]}}
     */

    private String message;
    private int code;
    /**
     * update : {}
     * response : {"count":10,"total":2,"page":1,"results":[{"related_users":"empty","liked":"False","stats":{"liked":1,"forwards":0,"comments":8},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!","is_top":0,"create_time":"2016-09-10 00:33:17","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2e44dd36ef3fbfcb032e4","share_link":"http://wsq.umeng.com/feeds/57d2e44dd36ef3fbfcb032e4/"},{"related_users":"empty","liked":"False","stats":{"liked":0,"forwards":0,"comments":0},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条中文动态 62066","is_top":0,"create_time":"2016-09-10 00:11:06","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2df1ad36ef3fc508af255","share_link":"http://wsq.umeng.com/feeds/57d2df1ad36ef3fc508af255/"}]}
     */

    private DataBean Data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private UpdateBean update;
        /**
         * count : 10
         * total : 2
         * page : 1
         * results : [{"related_users":"empty","liked":"False","stats":{"liked":1,"forwards":0,"comments":8},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!","is_top":0,"create_time":"2016-09-10 00:33:17","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2e44dd36ef3fbfcb032e4","share_link":"http://wsq.umeng.com/feeds/57d2e44dd36ef3fbfcb032e4/"},{"related_users":"empty","liked":"False","stats":{"liked":0,"forwards":0,"comments":0},"has_collected":"False","title":"circle feed list !","creator":{"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"},"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"content":"这是一条中文动态 62066","is_top":0,"create_time":"2016-09-10 00:11:06","parent_feed_id":"","topic_tag":"","is_recommended":"True","id":"57d2df1ad36ef3fc508af255","share_link":"http://wsq.umeng.com/feeds/57d2df1ad36ef3fc508af255/"}]
         */

        private ResponseBean response;

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public static class UpdateBean {
        }

        public static class ResponseBean {
            private int count;
            private int total;
            private int page;
            /**
             * related_users : empty
             * liked : False
             * stats : {"liked":1,"forwards":0,"comments":8}
             * has_collected : False
             * title : circle feed list !
             * creator : {"icon_url":"http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg","id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}
             * is_topic_top : empty
             * image_urls : ["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"]
             * content : 这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!这是一条很长的中文动态,里面有很长的内容, 大神在里面评论了好多东西!!!!!
             * is_top : 0
             * create_time : 2016-09-10 00:33:17
             * parent_feed_id :
             * topic_tag :
             * is_recommended : True
             * id : 57d2e44dd36ef3fbfcb032e4
             * share_link : http://wsq.umeng.com/feeds/57d2e44dd36ef3fbfcb032e4/
             */

            private List<ResultsBean> results;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public List<ResultsBean> getResults() {
                return results;
            }

            public void setResults(List<ResultsBean> results) {
                this.results = results;
            }

            public static class ResultsBean {
                private String related_users;
                private String liked;
                /**
                 * liked : 1
                 * forwards : 0
                 * comments : 8
                 */

                private StatsBean stats;
                private String has_collected;
                private String title;
                /**
                 * icon_url : http://b.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=45f10be75edf8db1bc7b74603c13f162/023b5bb5c9ea15ce2f42ea76b6003af33a87b224.jpg
                 * id : 57d2c60fd36ef3ede3236ebb
                 * name : 15888888888大神
                 */

                private CreatorBean creator;
                private String is_topic_top;
                private String content;
                private int is_top;
                private String create_time;
                private String parent_feed_id;
                private String topic_tag;
                private String is_recommended;
                private String id;
                private String share_link;
                private List<String> image_urls;

                public String getRelated_users() {
                    return related_users;
                }

                public void setRelated_users(String related_users) {
                    this.related_users = related_users;
                }

                public String getLiked() {
                    return liked;
                }

                public void setLiked(String liked) {
                    this.liked = liked;
                }

                public StatsBean getStats() {
                    return stats;
                }

                public void setStats(StatsBean stats) {
                    this.stats = stats;
                }

                public String getHas_collected() {
                    return has_collected;
                }

                public void setHas_collected(String has_collected) {
                    this.has_collected = has_collected;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public CreatorBean getCreator() {
                    return creator;
                }

                public void setCreator(CreatorBean creator) {
                    this.creator = creator;
                }

                public String getIs_topic_top() {
                    return is_topic_top;
                }

                public void setIs_topic_top(String is_topic_top) {
                    this.is_topic_top = is_topic_top;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getIs_top() {
                    return is_top;
                }

                public void setIs_top(int is_top) {
                    this.is_top = is_top;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getParent_feed_id() {
                    return parent_feed_id;
                }

                public void setParent_feed_id(String parent_feed_id) {
                    this.parent_feed_id = parent_feed_id;
                }

                public String getTopic_tag() {
                    return topic_tag;
                }

                public void setTopic_tag(String topic_tag) {
                    this.topic_tag = topic_tag;
                }

                public String getIs_recommended() {
                    return is_recommended;
                }

                public void setIs_recommended(String is_recommended) {
                    this.is_recommended = is_recommended;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getShare_link() {
                    return share_link;
                }

                public void setShare_link(String share_link) {
                    this.share_link = share_link;
                }

                public List<String> getImage_urls() {
                    return image_urls;
                }

                public void setImage_urls(List<String> image_urls) {
                    this.image_urls = image_urls;
                }

                public static class StatsBean {
                    private int liked;
                    private int forwards;
                    private int comments;

                    public int getLiked() {
                        return liked;
                    }

                    public void setLiked(int liked) {
                        this.liked = liked;
                    }

                    public int getForwards() {
                        return forwards;
                    }

                    public void setForwards(int forwards) {
                        this.forwards = forwards;
                    }

                    public int getComments() {
                        return comments;
                    }

                    public void setComments(int comments) {
                        this.comments = comments;
                    }
                }

                public static class CreatorBean {
                    private String icon_url;
                    private String id;
                    private String name;

                    public String getIcon_url() {
                        return icon_url;
                    }

                    public void setIcon_url(String icon_url) {
                        this.icon_url = icon_url;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }
        }
    }
}
