package com.ac.alumnuscircle.beans;

import java.util.List;

/**
 * 发现圈子详情数据
 * Created by 白洋
 * 2016年9月13日15:37:20
 */
public class FindCircleDetail {


    /**
     * message : get umeng api successfully
     * code : 0
     * Data : {"update":{},"response":{"count":999,"total":2,"page":1,"results":[{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"empty","image_urls":[],"custom":{"virtual_cid":"57d2ca3eb9a9967859f14965","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-09 22:42:06","has_followed":"False","id":"57d2ca3eb9a9960c9cb47a56","name":"带你装逼，带你飞"},{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"http://img1.imgtn.bdimg.com/it/u=1372134302,958716461&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d2dd11b9a9967859f14f8","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-10 00:02:25","has_followed":"False","id":"57d2dd11d36ef3fc508aee94","name":"带你装逼，带你飞 二号"}]}}
     */

    private String message;
    private int code;
    /**
     * update : {}
     * response : {"count":999,"total":2,"page":1,"results":[{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"empty","image_urls":[],"custom":{"virtual_cid":"57d2ca3eb9a9967859f14965","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-09 22:42:06","has_followed":"False","id":"57d2ca3eb9a9960c9cb47a56","name":"带你装逼，带你飞"},{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"http://img1.imgtn.bdimg.com/it/u=1372134302,958716461&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d2dd11b9a9967859f14f8","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-10 00:02:25","has_followed":"False","id":"57d2dd11d36ef3fc508aee94","name":"带你装逼，带你飞 二号"}]}
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
         * count : 999
         * total : 2
         * page : 1
         * results : [{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"empty","image_urls":[],"custom":{"virtual_cid":"57d2ca3eb9a9967859f14965","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-09 22:42:06","has_followed":"False","id":"57d2ca3eb9a9960c9cb47a56","name":"带你装逼，带你飞"},{"stats":"empty","description":"大神再此，带你装逼，带你飞","tags":[],"icon_url":"http://img1.imgtn.bdimg.com/it/u=1372134302,958716461&fm=206&gp=0.jpg","image_urls":[],"custom":{"virtual_cid":"57d2dd11b9a9967859f14f8","creator_uid":"30","creator_name":"大神"},"secret":"False","create_time":"2016-09-10 00:02:25","has_followed":"False","id":"57d2dd11d36ef3fc508aee94","name":"带你装逼，带你飞 二号"}]
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
             * stats : empty
             * description : 大神再此，带你装逼，带你飞
             * tags : []
             * icon_url : empty
             * image_urls : []
             * custom : {"virtual_cid":"57d2ca3eb9a9967859f14965","creator_uid":"30","creator_name":"大神"}
             * secret : False
             * create_time : 2016-09-09 22:42:06
             * has_followed : False
             * id : 57d2ca3eb9a9960c9cb47a56
             * name : 带你装逼，带你飞
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
                private String stats;
                private String description;
                private String icon_url;
                /**
                 * virtual_cid : 57d2ca3eb9a9967859f14965
                 * creator_uid : 30
                 * creator_name : 大神
                 */

                private CustomBean custom;
                private String secret;
                private String create_time;
                private String has_followed;
                private String id;
                private String name;
                private List<?> tags;
                private List<?> image_urls;

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
