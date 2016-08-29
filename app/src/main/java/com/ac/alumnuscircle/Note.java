/**
 * @author Zhengfan
 * @date 16.08.27
 * @version 2
 * 功能：包含一些规范说明，没有实际作用，方便程序员查看。
 *
 * *************************************************************************************************
 * 注释规范：
 *
 * 在每个实体文件（.java、.xml）文件最开始部分，使用javadoc文档注释形式，
 * 说明该文件的创建作者，时间，版本（@author、@date、@version），功能。
 * 如果需要有特别说明，请在功能之后附加上注释1、注释2等。
 *
 *
 * 包命名规范：
 * 包名尽量短小，长单词按照缩小规范使用缩写的单词，不能确定的缩写请找CTO商量。
 *
 *
 * 类命名规范：
 * 类名尽量使用全称，应避免缩写，如果名称实在太长，请使用家喻户晓的缩小单词作为前缀，最后的单词还是使用全称
 * 不要使用缩小规范之外的缩小单词，不能确定时，找CTO商讨。
 *
 *
 * 对象命名规范：
 * 对象名以说明本对象作用为主，以类名作为基准，如Intent intent；尽量避免Intent i。
 *
 *
 * 图片资源命名规范：
 * 图片资源如果是可以大量复用的，比如返回按钮的图片，则不应该使用作用域前缀，应该直接使用一个通熟易懂的名字作为名称，简单为主。
 * 图片资源如果是具有明显作用域限制，比如只能在某个Activity使用的，应该用该Activity的所属(包名_类名_图片名)。
 *
 *
 * 布局文件命名规范：
 * 布局文件命名应该以使用的Activity、Fragment或者其他类的类名小写作为基准，
 * 不要附加activity、fragment、layout这些意义不大却占空间的字段。
 *
 *
 * 布局里面的控件id命名规范：
 * 控件id的命名应该明确说明该控件的作用域，务必要考虑到和其他id的命名冲突问题，标准是：布局所属类的（包名_类名_控件名_控件属性缩写)。
 * 如：msg_message_back_btn、msg_message_msg_tv。
 *
 *
 * 缩写规范：
 * 缩写之间以下划线连接，如果需要增加新的缩写词汇，请小组讨论并集中修改。
 * User name: username
 * User password: userpwd
 * Alumnus Circle: ac
 * Message: msg
 * Circle: cc
 * Circle Detail: ccdtl
 * Contact Detail: ctcdtl
 * Change: chg
 * Head Image: hdimg
 * EditText: et
 * TextView: tv
 * ImageButton: imgbtn
 * Button: btn
 * ImageView: iv
 * RecyclerView: rv
 * Comment: cmt
 * Layout: lyt
 * LinearLayout: llyt
 * FrameLayout: flyt
 * Toolbar: tlb
 * TextField: tf
 * Constant: cstt
 * Highly Filter: hlyflt
 * setting: set
 * Administrator: admin
 * SimpleDraweeView: sdv
 */

package com.ac.alumnuscircle;

public class Note {
}
