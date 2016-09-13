package com.ac.alumnuscircle.notice.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.CircleItem;
import com.ac.alumnuscircle.beans.CommentConfig;
import com.ac.alumnuscircle.beans.CommentItem;
import com.ac.alumnuscircle.beans.FavortItem;
import com.ac.alumnuscircle.beans.NoticeList;
import com.ac.alumnuscircle.beans.User;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.module.divider_item_decoration.DividerItemDecoration;
import com.ac.alumnuscircle.net.CookieUtils;
import com.ac.alumnuscircle.notice.adapter.NoticeAdapter;
import com.ac.alumnuscircle.notice.mvp.contract.CircleContract;
import com.ac.alumnuscircle.notice.mvp.presenter.CirclePresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import com.yiw.qupai.QPManager;
//import com.yiw.qupai.listener.IUploadListener;
//import com.yiw.qupai.result.RecordResult;

/**
 * @author 白洋
 * @ClassName: NoticeAct
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-12-28 下午4:21:18
 */
public class NoticeAct extends Activity implements CircleContract.View {


    private NoticeAdapter noticeAdapter;//圈子详情适配器

    private ImageView float_button;
    private Button edit, chat, share, exit, setting, invite;
    private LinearLayout back;
    private Toolbar toolbar;
    private LinearLayout redBg;
    private TextView circleName;
    private TextView circleMemeberNum;
    private SimpleDraweeView circleImg;

    private static int page = 1;

    //	private int selectCircleItemH;
//	private int selectCommentItemOffset;
    private static Gson gson = new Gson();
    private CirclePresenter presenter;

    private SuperRecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private static OkHttpClient client = new OkHttpClient();

    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private final static int SUCCESS = 0X101;
    private final static int FAIL = 0X100;


    private List<String> liked;
    private static boolean finishList = false;//列表加载完
    //	private List<String > id;
//	private List<NoticeList.DataBean.ResponseBean.ResultsBean.StatsBean> stats;
//    private List<String > content;
//	private List<NoticeList.DataBean.ResponseBean.ResultsBean.CreatorBean> creator;
    private List<CircleItem> circleItems;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    noticeAdapter.setDatas(circleItems);
                    noticeAdapter.notifyDataSetChanged();
                    Log.d("a", "handleMessage: ");
                    break;
                case FAIL:
                    noticeAdapter.notifyDataSetChanged();
                    Toast.makeText(NoticeAct.this, "失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticeact);
        presenter = new CirclePresenter(this);
        Init();
        presenter.loadData(TYPE_PULLREFRESH, circleItems);
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.recycle();
        }
        super.onDestroy();
    }


    private void Init() {
        initView();

    }


    /**
     * 隐藏toolbar图标
     */
    private void HideToolbar() {
//		TextView tv = (TextView) findViewById(R.id.ccdtl_circledetail_tvback);
//		tv.setVisibility(View.GONE);

        setting.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
    }

    /**
     * 显示toolbar图标
     */
    private void ShowToolbar() {
//		TextView tv = (TextView) findViewById(R.id.ccdtl_circledetail_tvback);
//		tv.setVisibility(View.VISIBLE);

        setting.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
    }


    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initView() {


        //初始化

        toolbar = (Toolbar) findViewById(R.id.ccdtl_circledetail_tlb);
        circleMemeberNum = (TextView) findViewById(R.id.ccdtl_circledetail_memNum);
        circleName = (TextView) findViewById(R.id.ccdtl_circledetail_ccname_tv);
        circleImg = (SimpleDraweeView) findViewById(R.id.ccdtl_circledetail_img);
        float_button = (ImageView) findViewById(R.id.ccdtl_circledetail_float);
//        invite = (Button) findViewById(R.id.ccdtl_circledetail_invite);
//        search = (Button) findViewById(R.id.detail_search);
        setting = (Button) findViewById(R.id.ccdtl_circledetail_setting);
        edit = (Button) findViewById(R.id.ccdtl_circledetail_edit);
        chat = (Button) findViewById(R.id.ccdtl_circledetail_chat);
        share = (Button) findViewById(R.id.ccdtl_circledetail_share);
        exit = (Button) findViewById(R.id.ccdtl_circledetail_exit);
        back = (LinearLayout) findViewById(R.id.ccdtl_circledetail_back);
        redBg = (LinearLayout) findViewById(R.id.ccdtl_circledetail_redBg);


        circleName.setText(getIntent().getStringExtra("Title"));
        String imageurl = getIntent().getStringExtra("ImageUrl");
        circleImg.setImageURI(Uri.parse(imageurl));
        circleMemeberNum.setText(getIntent().getStringExtra("Memeber"));

//		id= new ArrayList<>();
//		content = new ArrayList<>();
//		stats = new ArrayList<>();
//		creator = new ArrayList<>();
        liked = new ArrayList<>();
        circleItems = new ArrayList<>();

        //监听
        float_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                redBg.setFocusable(true);
                redBg.setVisibility(View.VISIBLE);
                //隐藏浮动按钮
                float_button.setVisibility(View.GONE);
                float_button.setFocusable(false);
                //隐藏toolbar
                HideToolbar();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redBg.setFocusable(false);
                redBg.setVisibility(View.GONE);
                //显示浮动按钮
                float_button.setVisibility(View.VISIBLE);
                float_button.setFocusable(true);
                //显示toolbar
                ShowToolbar();
            }


        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent issueNotice = new Intent(ActivityName.notice_IssueNoticeAct);

                issueNotice.putExtra("Id", getIntent().getStringExtra("Id"));
                startActivity(issueNotice);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoticeAct.this, "这是聊天", Toast.LENGTH_SHORT).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (SuperRecyclerView) findViewById(R.id.notice_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(NoticeAct.this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//

        //刷新的动作
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadData(TYPE_PULLREFRESH, circleItems);
                        recyclerView.setRefreshing(false);
                        finishList = false;
                    }
                }, 2000);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//				Glide.with(NoticeAct.this).resumeRequests();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
//					Glide.with(NoticeAct.this).pauseRequests();
                }

            }
        });


        noticeAdapter = new NoticeAdapter(this);
        noticeAdapter.setCirclePresenter(presenter);
        recyclerView.setAdapter(noticeAdapter);


    }

    private void InitData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                page = 1;
                finishList = false;
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)
                        .add("info_json", "{\"count\":10, \"order\":0,\"page\":" + page + "," + "\"topic_id\":\"" +
                                getIntent().getStringExtra("Id") + "\"}").build();

                Request requestMyCircle = new Request.Builder().url(HttpGet.httpGetUrl + "/circle_feed").post(myCircleBody)
                        .addHeader("Cookie", CookieUtils.cookie).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if (response.isSuccessful()) {

                        String result = response.body().string();
                        Log.e("result", result);
                        NoticeList noticeList = gson.fromJson(result, NoticeList.class);
                        List<NoticeList.DataBean.ResponseBean.ResultsBean> results =
                                noticeList.getData().getResponse().getResults();
                        circleItems.clear();
                        for (NoticeList.DataBean.ResponseBean.ResultsBean res : results) {
//							id.add(res.getId());
//							stats.add(res.getStats());
                            liked.add(res.getLiked());
//							content.add(res.getContent());
//							creator.add(res.getCreator());
                            CircleItem citem = new CircleItem();

                            citem.setCreateTime(res.getCreate_time());
                            citem.setContent(res.getContent());
                            citem.setId(res.getId());
                            citem.setPhotos(res.getImage_urls());
                            Log.e("图片url", citem.getPhotos().get(0));
                            citem.setUser(new User(res.getCreator().getId(), res.getCreator().getName(), res.getCreator().getIcon_url()));
                            citem.setLiked(res.getLiked());
                            citem.setLikeNumber(res.getStats().getLiked());
                            citem.setCommentNumber(res.getStats().getComments());
                            circleItems.add(citem);
                        }
//						handler.sendEmptyMessage(SUCCESS);
//						page++;
//						noticeAdapter.setDatas(circleItems);
//						noticeAdapter.notifyDataSetChanged();
                        handler.sendEmptyMessage(SUCCESS);
                        response.body().close();

                    } else {
                        handler.sendEmptyMessage(FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
//					finishList  = true;
                    e.printStackTrace();
                }


            }
        }).start();

    }

    private void UploadData(final List<CircleItem> datas) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", HttpGet.loginKey)
                        .add("info_json", "{\"count\":10, \"order\":0,\"page\":" + page + "," + "\"topic_id\":\"" +
                                getIntent().getStringExtra("Id") + "\"}").build();

                Request requestMyCircle = new Request.Builder().url(HttpGet.httpGetUrl + "/circle_feed").post(myCircleBody)
                        .addHeader("Cookie", HttpGet.loginHeader).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if (response.isSuccessful()) {

                        String result = response.body().string();
                        Log.e("result", result);
                        NoticeList noticeList = gson.fromJson(result, NoticeList.class);
                        List<NoticeList.DataBean.ResponseBean.ResultsBean> results =
                                noticeList.getData().getResponse().getResults();
                        for (NoticeList.DataBean.ResponseBean.ResultsBean res : results) {
//							id.add(res.getId());
//							stats.add(res.getStats());
                            liked.add(res.getLiked());
//							content.add(res.getContent());
//							creator.add(res.getCreator());
                            CircleItem citem = new CircleItem();
                            citem.setCreateTime(res.getCreate_time());
                            citem.setContent(res.getContent());
                            citem.setId(res.getId());
                            citem.setPhotos(res.getImage_urls());
                            citem.setUser(new User(res.getCreator().getId(), res.getCreator().getName(), res.getCreator().getIcon_url()));

                            datas.add(citem);
                        }
//						handler.sendEmptyMessage(SUCCESS);
                        page++;
                        response.body().close();
                        noticeAdapter.getDatas().addAll(datas);
                    } else {
                        finishList = true;
                        handler.sendEmptyMessage(FAIL);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    finishList = true;
                }


            }
        }).start();


    }

    /**
     * 刷新加载数据
     *
     * @param loadType
     * @param datas
     */
    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {

        if (loadType == TYPE_PULLREFRESH) {
            InitData();


        } else if (loadType == TYPE_UPLOADREFRESH) {
            UploadData(datas);
            noticeAdapter.notifyDataSetChanged();
        }
        //noticeAdapter.notifyDataSetChanged();

        if (!finishList) {
            //上拉加载
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.loadData(TYPE_UPLOADREFRESH, circleItems);
                        }
                    }, 2000);

                }
            }, 1);
        } else {
            recyclerView.removeMoreListener();
            recyclerView.hideMoreProgress();
        }

    }


    /**
     * 删除自己的评论
     *
     * @param circleId
     */
    @Override
    public void update2DeleteCircle(String circleId) {
//		List<CircleItem> circleItems = noticeAdapter.getDatas();
//		for(int i=0; i<circleItems.size(); i++){
//			if(circleId.equals(circleItems.get(i).getId())){
//				circleItems.remove(i);
//				noticeAdapter.notifyDataSetChanged();
//				//noticeAdapter.notifyItemRemoved(i+1);
//				return;
//			}
//		}
    }

    /**
     * 点赞
     *
     * @param circlePosition
     * @param addItem
     */
    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
//		if(addItem != null){
//            CircleItem item = (CircleItem) noticeAdapter.getDatas().get(circlePosition);
//            item.getFavorters().add(addItem);
//			noticeAdapter.notifyDataSetChanged();
//            //noticeAdapter.notifyItemChanged(circlePosition+1);
//		}
    }

    /**
     * 取消赞
     *
     * @param circlePosition
     * @param favortId
     */
    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
//        CircleItem item = (CircleItem) noticeAdapter.getDatas().get(circlePosition);
//		List<FavortItem> items = item.getFavorters();
//		for(int i=0; i<items.size(); i++){
//			if(favortId.equals(items.get(i).getId())){
//				items.remove(i);
//				noticeAdapter.notifyDataSetChanged();
//                //noticeAdapter.notifyItemChanged(circlePosition+1);
//				return;
//			}
//		}
    }

    /**
     * 回复信息
     *
     * @param circlePosition
     * @param addItem
     */
    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
//		if(addItem != null){
//            CircleItem item = (CircleItem) noticeAdapter.getDatas().get(circlePosition);
//            item.getComments().add(addItem);
//			noticeAdapter.notifyDataSetChanged();
//            //noticeAdapter.notifyItemChanged(circlePosition+1);
//		}
//		//清空评论文本
//		 editText.setText("");
    }

    /**
     * 删除评论
     *
     * @param circlePosition
     * @param commentId
     */
    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
//        CircleItem item = (CircleItem) noticeAdapter.getDatas().get(circlePosition);
//		List<CommentItem> items = item.getComments();
//		for(int i=0; i<items.size(); i++){
//			if(commentId.equals(items.get(i).getId())){
//				items.remove(i);
//				noticeAdapter.notifyDataSetChanged();
//                //noticeAdapter.notifyItemChanged(circlePosition+1);
//				return;
//			}
//		}
    }

    /**
     * 评论的弹框
     *
     * @param visibility
     * @param commentConfig
     */
    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {

    }


    //    String videoFile;
//    String [] thum;
    //获得返回码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//		if (resultCode == RESULT_OK) {
//			RecordResult result =new RecordResult(data);
//			//得到视频地址，和缩略图地址的数组，返回十张缩略图
//			videoFile = result.getPath();
//			thum = result.getThumbnail();
//			result.getDuration();
//
//            Log.e(TAG, "视频路径:" + videoFile + "图片路径:" + thum[0]);
//
//			QPManager.getInstance(getApplicationContext()).startUpload(videoFile, thum[0], new IUploadListener() {
//                @Override
//                public void preUpload() {
//                    uploadDialog.show();
//                }
//
//                @Override
//                public void uploadComplet(String videoUrl, String imageUrl, String message) {
//                    uploadDialog.hide();
//                    Toast.makeText(NoticeAct.this, "上传成功...", Toast.LENGTH_LONG).show();
//
//                    //将新拍摄的video刷新到列表中
//                    noticeAdapter.getDatas().add(0, DatasUtil.createVideoItem(videoFile, thum[0]));
//                    noticeAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void uploadError(int errorCode, final String message) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            uploadDialog.hide();
//                            Toast.makeText(NoticeAct.this, message, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void uploadProgress(int percentsProgress) {
//                    uploadDialog.setPercentsProgress(percentsProgress);
//                }
//            });
//
//			/**
//			 * 清除草稿,草稿文件将会删除。所以在这之前我们执行拷贝move操作。
//			 * 上面的拷贝操作请自行实现，第一版本的copyVideoFile接口不再使用
//			 */
//            /*QupaiService qupaiService = QupaiManager
//                    .getQupaiService(NoticeAct.this);
//            qupaiService.deleteDraft(getApplicationContext(),data);*/
//
//		} else {
//			if (resultCode == RESULT_CANCELED) {
//				Toast.makeText(NoticeAct.this, "RESULT_CANCELED", Toast.LENGTH_LONG).show();
//			}
//		}
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
