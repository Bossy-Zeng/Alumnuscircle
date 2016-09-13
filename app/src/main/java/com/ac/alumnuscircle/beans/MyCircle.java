package com.ac.alumnuscircle.beans;

import java.util.List;

/**
 * Created by 吴正凡 on 2016/9/9.
 */
public class MyCircle {


    /**
     * message : get umeng api successfully
     * code : 0
     * Data : {"update":{},"response":{"count":300,"total":2,"page":1,"results":[{"stats":{"fans":1,"feeds":0},"description":"汇聚所有android开发大神","tags":[],"icon_url":"http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg","image_urls":[],"custom":{"virtual_cid":"57d421abd36ef3fc508b350c","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 23:07:23","has_followed":"True","id":"57d421abd36ef3fc508b350f","name":"android开发小组2"},{"stats":{"fans":1,"feeds":5},"description":"Go dIe","tags":[],"icon_url":"http://img5.imgtn.bdimg.com/it/u=2856180651,1164389396&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d402d8b9a996112cc6cdf4","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 20:55:52","has_followed":"True","id":"57d402d8b9a9965b02927330","name":"Dead"}]}}
     */

    private String message;
    private int code;
    /**
     * update : {}
     * response : {"count":300,"total":2,"page":1,"results":[{"stats":{"fans":1,"feeds":0},"description":"汇聚所有android开发大神","tags":[],"icon_url":"http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg","image_urls":[],"custom":{"virtual_cid":"57d421abd36ef3fc508b350c","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 23:07:23","has_followed":"True","id":"57d421abd36ef3fc508b350f","name":"android开发小组2"},{"stats":{"fans":1,"feeds":5},"description":"Go dIe","tags":[],"icon_url":"http://img5.imgtn.bdimg.com/it/u=2856180651,1164389396&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d402d8b9a996112cc6cdf4","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 20:55:52","has_followed":"True","id":"57d402d8b9a9965b02927330","name":"Dead"}]}
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
        /**
         * count : 300
         * total : 2
         * page : 1
         * results : [{"stats":{"fans":1,"feeds":0},"description":"汇聚所有android开发大神","tags":[],"icon_url":"http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg","image_urls":[],"custom":{"virtual_cid":"57d421abd36ef3fc508b350c","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 23:07:23","has_followed":"True","id":"57d421abd36ef3fc508b350f","name":"android开发小组2"},{"stats":{"fans":1,"feeds":5},"description":"Go dIe","tags":[],"icon_url":"http://img5.imgtn.bdimg.com/it/u=2856180651,1164389396&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d402d8b9a996112cc6cdf4","creator_uid":"36","creator_name":"吴卓凡"},"secret":"False","create_time":"2016-09-10 20:55:52","has_followed":"True","id":"57d402d8b9a9965b02927330","name":"Dead"}]
         */

        private ResponseBean response;

        public ResponseBean getResponse() {
            return response;
        }

        public void setResponse(ResponseBean response) {
            this.response = response;
        }

        public static class ResponseBean {
            private int count;
            private int total;
            private int page;
            /**
             * stats : {"fans":1,"feeds":0}
             * description : 汇聚所有android开发大神
             * tags : []
             * icon_url : http://tupian.qqjay.com/tou3/2016/0605/222393536f052f6d5c1e293b8e065164.jpg
             * image_urls : []
             * custom : {"virtual_cid":"57d421abd36ef3fc508b350c","creator_uid":"36","creator_name":"吴卓凡"}
             * secret : False
             * create_time : 2016-09-10 23:07:23
             * has_followed : True
             * id : 57d421abd36ef3fc508b350f
             * name : android开发小组2
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
                /**
                 * fans : 1
                 * feeds : 0
                 */

                private StatsBean stats;
                private String description;
                private String icon_url;
                /**
                 * virtual_cid : 57d421abd36ef3fc508b350c
                 * creator_uid : 36
                 * creator_name : 吴卓凡
                 */

                private CustomBean custom;
                private String secret;
                private String create_time;
                private String has_followed;
                private String id;
                private String name;
                private List<?> tags;
                private List<?> image_urls;

                public StatsBean getStats() {
                    return stats;
                }

                public void setStats(StatsBean stats) {
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

                public CustomBean getCustom() {
                    return custom;
                }

                public void setCustom(CustomBean custom) {
                    this.custom = custom;
                }

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getHas_followed() {
                    return has_followed;
                }

                public void setHas_followed(String has_followed) {
                    this.has_followed = has_followed;
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

                public List<?> getTags() {
                    return tags;
                }

                public void setTags(List<?> tags) {
                    this.tags = tags;
                }

                public List<?> getImage_urls() {
                    return image_urls;
                }

                public void setImage_urls(List<?> image_urls) {
                    this.image_urls = image_urls;
                }

                public static class StatsBean {
                    private int fans;
                    private int feeds;

                    public int getFans() {
                        return fans;
                    }

                    public void setFans(int fans) {
                        this.fans = fans;
                    }

                    public int getFeeds() {
                        return feeds;
                    }

                    public void setFeeds(int feeds) {
                        this.feeds = feeds;
                    }
                }

                public static class CustomBean {
                    private String virtual_cid;
                    private String creator_uid;
                    private String creator_name;

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

                    public String getCreator_name() {
                        return creator_name;
                    }

                    public void setCreator_name(String creator_name) {
                        this.creator_name = creator_name;
                    }
                }
            }
        }
    }
}
