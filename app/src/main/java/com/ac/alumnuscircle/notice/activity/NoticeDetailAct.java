package com.ac.alumnuscircle.notice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.MyInfo;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;
import com.ac.alumnuscircle.beans.CircleItem;
import com.ac.alumnuscircle.beans.CommentConfig;
import com.ac.alumnuscircle.beans.CommentItem;
import com.ac.alumnuscircle.beans.CommentList;
import com.ac.alumnuscircle.beans.FavortItem;
import com.ac.alumnuscircle.beans.User;
import com.ac.alumnuscircle.net.CookieUtils;
import com.ac.alumnuscircle.notice.adapter.NoticeDetailAdapter;
import com.ac.alumnuscircle.notice.mvp.contract.CircleContract;
import com.ac.alumnuscircle.notice.mvp.presenter.CirclePresenter;
import com.ac.alumnuscircle.notice.utils.CommonUtils;
import com.ac.alumnuscircle.notice.widgets.CommentListView;
import com.ac.alumnuscircle.notice.widgets.DivItemDecoration;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 白洋.
 */
public class NoticeDetailAct extends Activity implements CircleContract.View, NoticeDetailAdapter.ShowEditTextBody {
    protected static final String TAG = NoticeAct.class.getSimpleName();
    private NoticeDetailAdapter noticeDetailAdapter;//圈子详情适配器
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;//发表评论的按钮

    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;
    private int selectCircleItemH;
    private int selectCommentItemOffset;

    private CirclePresenter presenter;
    private CommentConfig commentConfig;
    private SuperRecyclerView recyclerView;
    private RelativeLayout bodyLayout;
    private LinearLayoutManager layoutManager;
    private Toolbar titleBar;

    private static  OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private final static int SUCCESS = 0X100;

    private final static int FAIL = 0X101;
    private List<CircleItem> circleData;//传入的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticedtlact);
        presenter = new CirclePresenter(NoticeDetailAct.this);

       Init();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case SUCCESS:
                    noticeDetailAdapter = new NoticeDetailAdapter(NoticeDetailAct.this,circleData,NoticeDetailAct.this);
//                    noticeDetailAdapter.setCirclePresenter(presenter);
                    recyclerView.setAdapter(noticeDetailAdapter);
                    noticeDetailAdapter.notifyDataSetChanged();
                    break;
                case FAIL:
                    Toast.makeText(NoticeDetailAct.this, "加载失败,请检查网络", Toast.LENGTH_SHORT).show();
                    break;
                case 0x222:
                    noticeDetailAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    private void Init() {
        InitView();
        InitData();

    }

    private void InitData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                CircleItem circleItem = (CircleItem) getIntent().getSerializableExtra("CircleItem");
                RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)
                        .add("feed_id",circleItem.getId())
                        .add("page","1").add("count","999").build();

                Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/commentlist").post(myCircleBody)
                        .addHeader("Cookie", CookieUtils.cookie).build();
                try {
                    Response response = client.newCall(requestMyCircle).execute();
                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        CommentList commentlist = gson.fromJson(result,CommentList.class);
                       List<CommentList.DataBean.ResponseBean.ResultsBean> results=
                               commentlist.getData().getResponse().getResults();
                         List<CommentItem>commentItems = new ArrayList<CommentItem>();
                       for(CommentList.DataBean.ResponseBean.ResultsBean res:results)
                       {
                           CommentItem commentItem = new CommentItem();

                           commentItem.setUser(new User(res.getCreator().getId(),
                                   res.getCreator().getName().substring(11,
                                           res.getCreator().getName().length()),""));
                           commentItem.setContent(res.getContent());
                           commentItem.setLiked(res.getLiked());
                           commentItems.add(commentItem);

                       }
                        circleItem.setComments(commentItems);
                        circleData.add(circleItem);
                        response.body().close();
                        handler.sendEmptyMessage(SUCCESS);
                    }
                    else{
                      handler.sendEmptyMessage(FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {

                }

            }
        }).start();

    }
    /**
     * 初始化界面
     */
    private void InitView()
    {


        recyclerView = (SuperRecyclerView) findViewById(R.id.noticedtlact_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

   circleData = new ArrayList<>();
		recyclerView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (edittextbody.getVisibility() == View.VISIBLE) {
					updateEditTextBodyVisible(View.GONE, null);
					return true;
				}
				return false;
			}
		});

        //刷新的动作


//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
////                Glide.with(NoticeDetailAct.this).resumeRequests();
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState != RecyclerView.SCROLL_STATE_IDLE){
////                    Glide.with(NoticeDetailAct.this).pauseRequests();
//                }
//
//            }
//        });



        edittextbody = (LinearLayout) findViewById(R.id.notice_editvew_editTextBodyLl);
		 editText = (EditText) findViewById(R.id.notice_editvew_circleEt);
		sendIv = (ImageView) findViewById(R.id.notice_editvew_sendIv);
		sendIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (presenter != null) {
					//发布评论
					final String content =  editText.getText().toString().trim();
					if(TextUtils.isEmpty(content)){
						Toast.makeText(NoticeDetailAct.this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
						return;
					}


                    /**
                     *
                     */

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            CircleItem circleItem = (CircleItem) getIntent().getSerializableExtra("CircleItem");
                            RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)

                                    .add("info_json",String.format("{\"feed_id\":\"%s\",\"content\":\"%s\"}",circleItem.getId()
                                    ,content))
                                    .build();
                            Log.e("_xsrf是：", CookieUtils._xsrf );

                            Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/pubcomment").post(myCircleBody)
                                    .addHeader("Cookie", CookieUtils.cookie).build();
//
                            try {
                                Response response = client.newCall(requestMyCircle).execute();
                                if(response.isSuccessful()) {
                                    CommentItem commentItem = new CommentItem();
                                    commentItem.setContent(content);
                                    commentItem.setUser(new User(Login.UID, MyInfo.myInfo.getName(),""));
                                    circleData.get(0).getComments().add(commentItem);
                                    handler.sendEmptyMessage(0X222);
                                    Log.e("success", response.body().string());
                                    response.body().close();
                                }
                                else{
                                    handler.sendEmptyMessage(FAIL);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            catch (Exception e)
                            {

                            }
                            finally {
                                editText.setText("");
                            }

                        }
                    }).start();

//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            RequestBody myCircleBody = new FormBody.Builder().add("_xsrf", CookieUtils._xsrfKey)
//                                    .add("feed_id",circleData.get(0).getId())
//                                    .add("content",content).build();
//
//                            Request requestMyCircle  = new Request.Builder().url(HttpGet.httpGetUrl+"/pubcomment").post(myCircleBody)
//                                    .addHeader("Cookie", CookieUtils.cookie).build();
//                            Log.e("feed_id", circleData.get(0).getId() );
//                            try {
//                                Response response = client.newCall(requestMyCircle).execute();
//                                if(response.isSuccessful())
//                                {
//                                    CommentItem commentItem = new CommentItem();
//                                    commentItem.setContent(content);
//                                    /**
//                                     * 换成user相关数据
//                                     */
//                                    commentItem.setUser(new User("aa","吴小凡",""));
//                                    circleData.get(0).getComments().add(commentItem);
//                                    noticeDetailAdapter.notifyDataSetChanged();
//
//                                }
//                                else{
//                                 handler.sendEmptyMessage(FAIL);
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            finally {
//                                editText.setText("");
//                            }
//                        }
//                    }).start();
                }
                //隐藏评论按键
				updateEditTextBodyVisible(View.GONE, null);
			}
		});


    }


    @Override
    protected void onDestroy() {
        if(presenter !=null){
            presenter.recycle();
        }
        super.onDestroy();
    }
    @Override
    public void update2DeleteCircle(String circleId) {
//        		List<CircleItem> circleItems = noticeDetailAdapter.getDatas();
//		for(int i=0; i<circleItems.size(); i++){
//			if(circleId.equals(circleItems.get(i).getId())){
//				circleItems.remove(i);
//                noticeDetailAdapter.notifyDataSetChanged();
//				//noticeDetailAdapter.notifyItemRemoved(i+1);
//				return;
//			}
//		}
    }

    /**
     * 点赞
     * @param circlePosition
     * @param addItem
     */
    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
//		if(addItem != null){
//            CircleItem item = (CircleItem) noticeDetailAdapter.getDatas().get(circlePosition);
//            item.getFavorters().add(addItem);
//			noticeDetailAdapter.notifyDataSetChanged();
//            //noticeDetailAdapter.notifyItemChanged(circlePosition+1);
//		}
    }

    /**
     * 取消赞
     * @param circlePosition
     * @param favortId
     */
    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
//        CircleItem item = (CircleItem) noticeDetailAdapter.getDatas().get(circlePosition);
//		List<FavortItem> items = item.getFavorters();
//		for(int i=0; i<items.size(); i++){
//			if(favortId.equals(items.get(i).getId())){
//				items.remove(i);
//                noticeDetailAdapter.notifyDataSetChanged();
//                //noticeDetailAdapter.notifyItemChanged(circlePosition+1);
//				return;
//			}
//		}
    }

    /**
     * 回复信息
     * @param circlePosition
     * @param addItem
     */
    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
//		if(addItem != null){
//            CircleItem item = (CircleItem) noticeDetailAdapter.getDatas().get(circlePosition);
//            item.getComments().add(addItem);
//            noticeDetailAdapter.notifyDataSetChanged();
//            //noticeAdapter.notifyItemChanged(circlePosition+1);
//		}
//		//清空评论文本
//		 editText.setText("");
    }

    /**
     * 删除评论
     * @param circlePosition
     * @param commentId
     */
    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
//        CircleItem item = (CircleItem) noticeDetailAdapter.getDatas().get(circlePosition);
//		List<CommentItem> items = item.getComments();
//		for(int i=0; i<items.size(); i++){
//			if(commentId.equals(items.get(i).getId())){
//				items.remove(i);
//                noticeDetailAdapter.notifyDataSetChanged();
//                //noticeAdapter.notifyItemChanged(circlePosition+1);
//				return;
//			}
//		}
    }

    /**
     * 评论的弹框
     * @param visibility
     * @param commentConfig
     */
    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
		this.commentConfig = commentConfig;
		edittextbody.setVisibility(visibility);

		measureCircleItemHighAndCommentItemOffset(commentConfig);

		if(View.VISIBLE==visibility){
			 editText.requestFocus();
			//弹出键盘
			CommonUtils.showSoftInput( editText.getContext(),  editText);

		}else if(View.GONE==visibility){
			//隐藏键盘
			CommonUtils.hideSoftInput( editText.getContext(),  editText);
		}
    }

    /**
     * 刷新加载数据
     * @param loadType
     * @param datas
     */
    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {

//        if (loadType == TYPE_PULLREFRESH){
//            InitData();
//            noticeDetailAdapter.setDatas(datas);
//        }
////        else if(loadType == TYPE_UPLOADREFRESH){
////            noticeDetailAdapter.getDatas().addAll(datas);
////        }
//
//
//        noticeDetailAdapter.notifyDataSetChanged();
        /**
         * 下拉刷新
         */
//        if(noticeDetailAdapter.getDatas().size()<45 + NoticeAdapter.HEADVIEW_SIZE){
//            recyclerView.setupMoreListener(new OnMoreListener() {
//                @Override
//                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                          presenter.loadData(TYPE_UPLOADREFRESH);
//                        }
//                    }, 2000);
//
//                }
//            }, 1);
//        }else{
//            recyclerView.removeMoreListener();
//            recyclerView.hideMoreProgress();
//        }

    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig){
        if(commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();

        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition  - firstPosition);

        if(selectCircleItem != null){
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if(commentConfig.commentType == CommentConfig.Type.REPLY){
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.noticedtl_commentList);
            if(commentLv!=null){
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if(selectCommentItem != null){
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if(parentView != null){
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
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
