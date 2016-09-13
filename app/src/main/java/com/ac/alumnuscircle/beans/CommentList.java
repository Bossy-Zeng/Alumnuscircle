package com.ac.alumnuscircle.beans;

import java.util.List;

/**
 *
 * Created by 白洋 2016年9月13日15:37:01
 */
public class CommentList {

    /**
     * message : get umeng api successfully
     * code : 4100
     * Data : {"update":{},"response":{"count":999,"total":4,"page":1,"results":[{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}}]}}
     */

    private String message;
    private int code;
    /**
     * update : {}
     * response : {"count":999,"total":4,"page":1,"results":[{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}}]}
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
         * total : 4
         * page : 1
         * results : [{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}},{"content":"[很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! ","liked":"False","creator":{"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}}]
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
             * content : [很长]我评论了这条动态! 我评论了这条动态!我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态! 我评论了这条动态!
             * liked : False
             * creator : {"id":"57d2c60fd36ef3ede3236ebb","name":"15888888888大神"}
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
                private String content;
                private String liked;
                /**
                 * id : 57d2c60fd36ef3ede3236ebb
                 * name : 15888888888大神
                 */

                private CreatorBean creator;

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getLiked() {
                    return liked;
                }

                public void setLiked(String liked) {
                    this.liked = liked;
                }

                public CreatorBean getCreator() {
                    return creator;
                }

                public void setCreator(CreatorBean creator) {
                    this.creator = creator;
                }

                public static class CreatorBean {
                    private String id;
                    private String name;

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
