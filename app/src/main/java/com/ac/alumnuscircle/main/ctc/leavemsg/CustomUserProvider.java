/**
 * Created by 曾博晖 on 2016/8/31.
 *
 * @author 曾博晖
 * @date 2016年8月31日
 * @verson 1
 * 功能
 * 将自己的用户体系集成到LeanCloud服务器端（这里只有一些用户数据）
 */
package com.ac.alumnuscircle.main.ctc.leavemsg;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;


public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    /**
     * * 2016年9月1日00:14:22
     * 曾博晖
     * 添加注释：
     * partUsers是本地存贮的用户数据，
     * 因为LeanCloud只是支持传递一个ClientId上去，
     * 所以用户名和用户头像的URL地址只能自己本地添加，
     * 在使用时直接使用————
     * CustomUserProvider.partUsers.add(
     * new LCChatKitUser(用户ID(String),
     *                   用户名(String),
     *                   用户头像地址(String))
     *                   );
     *                   即可

     * */
    public static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为模拟数据，仅供参考
    static {
        /**
         * 此处在本地只是添加一些的数据
         * 作为测试
         * 之后可以任意删改
         * */

        partUsers.add(new LCChatKitUser("陈雄辉", "陈雄辉",
                "http://g.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=b2a7a7d7a2c27d1ea57333c02ee58158/e61190ef76c6a7efa883bd55fbfaaf51f3de661f.jpg"));
        partUsers.add(new LCChatKitUser("董莹莹", "董莹莹",
                "http://img1.imgtn.bdimg.com/it/u=2385199661,1509060230&fm=21&gp=0.jpg"));
        partUsers.add(new LCChatKitUser("李崇", "李崇",
                "http://v1.qzone.cc/avatar/201508/30/00/39/55e1e026dc781749.jpg%21200x200.jpg"));
        partUsers.add(new LCChatKitUser("苏小陌", "苏小陌",
                "http://img2.imgtn.bdimg.com/it/u=3529368069,13239119&fm=21&gp=0.jpg"));
        partUsers.add(new LCChatKitUser("李梦雅", "李梦雅",
                "http://img5.imgtn.bdimg.com/it/u=146486684,2713066059&fm=11&gp=0.jpg"));
        partUsers.add(new LCChatKitUser("崔浩宇", "崔皓宇",
                "http://www.th7.cn/d/file/p/2016/07/26/b18e716fdfa5e890c4c9ebcb5f7e1afe.jpg"));
        partUsers.add(new LCChatKitUser("陈小辉", "陈小辉",
                "http://v1.qzone.cc/avatar/201501/17/14/52/54ba06b65074b350.jpg%21200x200.jpg"));
        partUsers.add(new LCChatKitUser("吴小宝", "吴小宝",
                "http://img4.imgtn.bdimg.com/it/u=3868407632,2636498616&fm=206&gp=0.jpg"));
        partUsers.add(new LCChatKitUser("欧阳盼盼", "欧阳盼盼",
                "http://img5.imgtn.bdimg.com/it/u=2030615142,3525420243&fm=21&gp=0.jpg"));
        partUsers.add(new LCChatKitUser("于轩", "于轩",
                "http://img0.imgtn.bdimg.com/it/u=581732747,2670419869&fm=21&gp=0.jpg"));


    }

//    }

    /**
     * 查询本地有无
     * 聊天对象的信息，若是没有，就添加进去
     * 2016年9月1日00:17:34
     * 曾博晖
     * 个人理解
     * */
    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
