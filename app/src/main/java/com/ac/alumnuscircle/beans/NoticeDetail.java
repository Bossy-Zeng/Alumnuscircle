package com.ac.alumnuscircle.beans;

import java.util.List;

/**
 * Created by 白洋 on 2016/9/8.
 * 动态详情
 */
public class NoticeDetail {

    /**
     * message : get umeng api successfully
     * code : 0
     * Data : {"update":"empty","response":{"total":1,"results":[{"liked":false,"seq":3615432,"creator":{"icon_url":"empty","medal_ids":"empty","id":"57cd37cfb9a9967eb0367d76","source_uid":"15996198251","name":"15996198251"},"topics":[{"stats":"empty","description":"the circle will be beautiful!","icon_url":"empty","image_urls":"empty","custom":{"virtual_cid":"57c69d67d36ef3151eb80ba9","creator_uid":"123"},"id":"57c69d68d36ef3151eb80bac","name":"new circle 983"}],"tag":0,"readable_create_time":"23:53","id":"57d18972d36ef3e57a928106","stats":{"liked":0,"forwards":0,"comments":0},"title":"circle feed list !","origin_feed":"empty","custom":"","content":"这是一条中文动态 24191","source":"社区","location":"empty","media_type":0,"type":0,"rich_text":"","status":0,"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"is_top":0,"topic_tag":"","related_users":"empty","has_collected":false,"create_time":"2016-09-08 23:53:22","parent_feed_id":"","is_recommended":false,"share_link":"http://wsq.umeng.com/feeds/57d18972d36ef3e57a928106/"}]}}
     */

    private String message;
    private int code;
    /**
     * update : empty
     * response : {"total":1,"results":[{"liked":false,"seq":3615432,"creator":{"icon_url":"empty","medal_ids":"empty","id":"57cd37cfb9a9967eb0367d76","source_uid":"15996198251","name":"15996198251"},"topics":[{"stats":"empty","description":"the circle will be beautiful!","icon_url":"empty","image_urls":"empty","custom":{"virtual_cid":"57c69d67d36ef3151eb80ba9","creator_uid":"123"},"id":"57c69d68d36ef3151eb80bac","name":"new circle 983"}],"tag":0,"readable_create_time":"23:53","id":"57d18972d36ef3e57a928106","stats":{"liked":0,"forwards":0,"comments":0},"title":"circle feed list !","origin_feed":"empty","custom":"","content":"这是一条中文动态 24191","source":"社区","location":"empty","media_type":0,"type":0,"rich_text":"","status":0,"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"is_top":0,"topic_tag":"","related_users":"empty","has_collected":false,"create_time":"2016-09-08 23:53:22","parent_feed_id":"","is_recommended":false,"share_link":"http://wsq.umeng.com/feeds/57d18972d36ef3e57a928106/"}]}
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
        private String update;
        /**
         * total : 1
         * results : [{"liked":false,"seq":3615432,"creator":{"icon_url":"empty","medal_ids":"empty","id":"57cd37cfb9a9967eb0367d76","source_uid":"15996198251","name":"15996198251"},"topics":[{"stats":"empty","description":"the circle will be beautiful!","icon_url":"empty","image_urls":"empty","custom":{"virtual_cid":"57c69d67d36ef3151eb80ba9","creator_uid":"123"},"id":"57c69d68d36ef3151eb80bac","name":"new circle 983"}],"tag":0,"readable_create_time":"23:53","id":"57d18972d36ef3e57a928106","stats":{"liked":0,"forwards":0,"comments":0},"title":"circle feed list !","origin_feed":"empty","custom":"","content":"这是一条中文动态 24191","source":"社区","location":"empty","media_type":0,"type":0,"rich_text":"","status":0,"is_topic_top":"empty","image_urls":["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"],"is_top":0,"topic_tag":"","related_users":"empty","has_collected":false,"create_time":"2016-09-08 23:53:22","parent_feed_id":"","is_recommended":false,"share_link":"http://wsq.umeng.com/feeds/57d18972d36ef3e57a928106/"}]
         */

        private ResponseBean response;

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public static class ResponseBean {
            private int total;
            /**
             * liked : false
             * seq : 3615432
             * creator : {"icon_url":"empty","medal_ids":"empty","id":"57cd37cfb9a9967eb0367d76","source_uid":"15996198251","name":"15996198251"}
             * topics : [{"stats":"empty","description":"the circle will be beautiful!","icon_url":"empty","image_urls":"empty","custom":{"virtual_cid":"57c69d67d36ef3151eb80ba9","creator_uid":"123"},"id":"57c69d68d36ef3151eb80bac","name":"new circle 983"}]
             * tag : 0
             * readable_create_time : 23:53
             * id : 57d18972d36ef3e57a928106
             * stats : {"liked":0,"forwards":0,"comments":0}
             * title : circle feed list !
             * origin_feed : empty
             * custom :
             * content : 这是一条中文动态 24191
             * source : 社区
             * location : empty
             * media_type : 0
             * type : 0
             * rich_text :
             * status : 0
             * is_topic_top : empty
             * image_urls : ["http://tupian.qqjay.com/tou3/2016/0605/9848ad4d58f2cf2ac07a2645d66e20e6.jpg","http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg"]
             * is_top : 0
             * topic_tag :
             * related_users : empty
             * has_collected : false
             * create_time : 2016-09-08 23:53:22
             * parent_feed_id :
             * is_recommended : false
             * share_link : http://wsq.umeng.com/feeds/57d18972d36ef3e57a928106/
             */

            private List<ResultsBean> results;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<ResultsBean> getResults() {
                return results;
            }

            public void setResults(List<ResultsBean> results) {
                this.results = results;
            }

            public static class ResultsBean {
                private boolean liked;
                private int seq;
                /**
                 * icon_url : empty
                 * medal_ids : empty
                 * id : 57cd37cfb9a9967eb0367d76
                 * source_uid : 15996198251
                 * name : 15996198251
                 */

                private CreatorBean creator;
                private int tag;
                private String readable_create_time;
                private String id;
                /**
                 * liked : 0
                 * forwards : 0
                 * comments : 0
                 */

                private StatsBean stats;
                private String title;
                private String origin_feed;
                private String custom;
                private String content;
                private String source;
                private String location;
                private int media_type;
                private int type;
                private String rich_text;
                private int status;
                private String is_topic_top;
                private int is_top;
                private String topic_tag;
                private String related_users;
                private boolean has_collected;
                private String create_time;
                private String parent_feed_id;
                private boolean is_recommended;
                private String share_link;
                /**
                 * stats : empty
                 * description : the circle will be beautiful!
                 * icon_url : empty
                 * image_urls : empty
                 * custom : {"virtual_cid":"57c69d67d36ef3151eb80ba9","creator_uid":"123"}
                 * id : 57c69d68d36ef3151eb80bac
                 * name : new circle 983
                 */

                private List<TopicsBean> topics;
                private List<String> image_urls;

                public boolean isLiked() {
                    return liked;
                }

                public void setLiked(boolean liked) {
                    this.liked = liked;
                }

                public int getSeq() {
                    return seq;
                }

                public void setSeq(int seq) {
                    this.seq = seq;
                }

                public CreatorBean getCreator() {
                    return creator;
                }

                public void setCreator(CreatorBean creator) {
                    this.creator = creator;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public String getReadable_create_time() {
                    return readable_create_time;
                }

                public void setReadable_create_time(String readable_create_time) {
                    this.readable_create_time = readable_create_time;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public StatsBean getStats() {
                    return stats;
                }

                public void setStats(StatsBean stats) {
                    this.stats = stats;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getOrigin_feed() {
                    return origin_feed;
                }

                public void setOrigin_feed(String origin_feed) {
                    this.origin_feed = origin_feed;
                }

                public String getCustom() {
                    return custom;
                }

                public void setCustom(String custom) {
                    this.custom = custom;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public int getMedia_type() {
                    return media_type;
                }

                public void setMedia_type(int media_type) {
                    this.media_type = media_type;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getRich_text() {
                    return rich_text;
                }

                public void setRich_text(String rich_text) {
                    this.rich_text = rich_text;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getIs_topic_top() {
                    return is_topic_top;
                }

                public void setIs_topic_top(String is_topic_top) {
                    this.is_topic_top = is_topic_top;
                }

                public int getIs_top() {
                    return is_top;
                }

                public void setIs_top(int is_top) {
                    this.is_top = is_top;
                }

                public String getTopic_tag() {
                    return topic_tag;
                }

                public void setTopic_tag(String topic_tag) {
                    this.topic_tag = topic_tag;
                }

                public String getRelated_users() {
                    return related_users;
                }

                public void setRelated_users(String related_users) {
                    this.related_users = related_users;
                }

                public boolean isHas_collected() {
                    return has_collected;
                }

                public void setHas_collected(boolean has_collected) {
                    this.has_collected = has_collected;
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

                public boolean isIs_recommended() {
                    return is_recommended;
                }

                public void setIs_recommended(boolean is_recommended) {
                    this.is_recommended = is_recommended;
                }

                public String getShare_link() {
                    return share_link;
                }

                public void setShare_link(String share_link) {
                    this.share_link = share_link;
                }

                public List<TopicsBean> getTopics() {
                    return topics;
                }

                public void setTopics(List<TopicsBean> topics) {
                    this.topics = topics;
                }

                public List<String> getImage_urls() {
                    return image_urls;
                }

                public void setImage_urls(List<String> image_urls) {
                    this.image_urls = image_urls;
                }

                public static class CreatorBean {
                    private String icon_url;
                    private String medal_ids;
                    private String id;
                    private String source_uid;
                    private String name;

                    public String getIcon_url() {
                        return icon_url;
                    }

                    public void setIcon_url(String icon_url) {
                        this.icon_url = icon_url;
                    }

                    public String getMedal_ids() {
                        return medal_ids;
                    }

                    public void setMedal_ids(String medal_ids) {
                        this.medal_ids = medal_ids;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getSource_uid() {
                        return source_uid;
                    }

                    public void setSource_uid(String source_uid) {
                        this.source_uid = source_uid;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
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

                public static class TopicsBean {
                    private String stats;
                    private String description;
                    private String icon_url;
                    private String image_urls;
                    /**
                     * virtual_cid : 57c69d67d36ef3151eb80ba9
                     * creator_uid : 123
                     */

                    private CustomBean custom;
                    private String id;
                    private String name;

                    public String getStats() {
                        return stats;
                    }

                    public void setStats(String stats) {
                        this.stats = stats;
                    }

                    public String getDescription() {
                        return description;
                    }

                    public void setDescription(String description) {
                        this.description = description;
                    }

                    public String getIcon_url() {
                        return icon_url;
                    }

                    public void setIcon_url(String icon_url) {
                        this.icon_url = icon_url;
                    }

                    public String getImage_urls() {
                        return image_urls;
                    }

                    public void setImage_urls(String image_urls) {
                        this.image_urls = image_urls;
                    }

                    public CustomBean getCustom() {
                        return custom;
                    }

                    public void setCustom(CustomBean custom) {
                        this.custom = custom;
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

                    public static class CustomBean {
                        private String virtual_cid;
                        private String creator_uid;

                        public String getVirtual_cid() {
                            return virtual_cid;
                        }

                        public void setVirtual_cid(String virtual_cid) {
                            this.virtual_cid = virtual_cid;
                        }

                        public String getCreator_uid() {
                            return creator_uid;
                        }

                        public void setCreator_uid(String creator_uid) {
                            this.creator_uid = creator_uid;
                        }
                    }
                }
            }
        }
    }
}
