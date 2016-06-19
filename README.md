# quweishuxue
一款趣味数学app

更多最新技术干货，加入微信公众号: 代码GG之家

附图：[1]

└─com
    └─example
        ├─db
        │      DBHelper.Java   数据库，存储成绩用
        │
        ├─model
        │      chengji.java  成绩数据结构
        │      chengjiAdapter.java  成绩列表更新器
        │
        └─sx
            │  app.java   app 实例，提供了配置存储功能
            │  cjdActivity.java  成绩单界面
            │  gameActivity.java  主要功能界面
            │  ksmsActivity.java   考试模式设置界面
            │  MainActivity.java   主菜单
            │  MusicService.java   音乐播放后台
            │  ssmsActivity.java   速算模式设置界面
            │  xlmsActivity.java   训练模式
            │
            ├─adapter
            │      GridViewItem.java   等级，设置，时间设置弹出的框
            │      MyAdapter.java  适配器
            │
            ├─utils
            │  │  Helper.java  帮助文件，存储设置值
            │  │  util.java   dx转px的工具
            │  │
            │  └─easyfont
            │          EasyFonts.java   一个字体库接口
            │          FontSourceProcessor.java
            │
            └─view
                    CountdownView.java   一个倒计时
                    CustomCountDownTimer.java
源码设计流程：
MainActivity 主界面，显示四个菜单： 代码里面有详细注释
 
private ImageView tab_2_img; // 速算模式
private ImageView tab_3_img; // 考试模式
private ImageView tab_1_img; // 训练模式
private ImageView tab_4_img;// 成绩单
 
回调进入各个界面
 
 
// 点击进入训练模式
case R.id.tab_1_img:
startActivity(new Intent(this, xlmsActivity.class));
break;
// 点击进入速算模式
case R.id.tab_2_img:
startActivity(new Intent(this, ssmsActivity.class));
break;
// 点击进入考试模式
case R.id.tab_3_img:
startActivity(new Intent(this, ksmsActivity.class));
break;
// 点击进入考试模式
case R.id.tab_4_img:
startActivity(new Intent(this, cjdActivity.class));
break;
case R.id.img_exit:
finish();// 退出
break;
case R.id.img_sound:
// 切换设置
boolean on = helper.getMusiconoff();
if (on) {
img_sound.setImageResource(R.drawable.sound_off);
} else {
img_sound.setImageResource(R.drawable.sound_on);
}
helper.setMusiconoff(!on);
 
训练模式:
 设置点击选择运算方式的时候的回调
lin_setting_oprate = findView(R.id.lin_setting_oprate);
lin_setting_oprate.setOnClickListener(this);
设置选择难度等级的回调
lin_setting_level = findView(R.id.lin_setting_level);
lin_setting_level.setOnClickListener(this);
 
case R.id.lin_setting_oprate:  
 弹出选择操作的框，以及设置回调数据
showPopupWindow(lin_setting_oprate, true);
break;
case R.id.lin_setting_level:
showPopupWindow(lin_setting_level, false);
break;
 
点击开始进行练习模式
taining_confirm = findView(R.id.taining_confirm);
taining_confirm.setOnClickListener(this);
case R.id.taining_confirm:
Intent game = new Intent(this, gameActivity.class);
game.putExtra("nandu", nandu);   难度值
game.putExtra("caozuo", caozuo);  选择的运算
game.putExtra("moshi", 0);// 0训练模式
startActivity(game);
break;
 
选择难度时相对应的显示星星:
img_star1 = findView(R.id.img_star1);
img_star2 = findView(R.id.img_star2);
img_star3 = findView(R.id.img_star3);
 
再说一个关键函数
 
// 加一个逻辑，用来处理是操作还是选择的难易程度
 
private void showPopupWindow(View view, boolean oprate) {
 
// 一个自定义的布局，作为显示的内容
GridView contentView = (GridView) LayoutInflater.from(this).inflate(
R.layout.window, null);
//一个对话框popwindow
final PopupWindow popupWindow = new PopupWindow(contentView,
WindowManager.LayoutParams.MATCH_PARENT, util.dp2px(this, 80),
true);
  //如果选择的是运算 显示运算列表，加减乘除，设置后将界面更新
//img_oprate.setImageResource(intArray[position]); 存储下当前选择的运算符，在进入练习时需要
 
// 难度等级的设计，一样，操作的回调是更新界面的星星数目，存储下来值，后面进入练习需要
 
 
if (oprate) {
 
 
final int[] intArray = { R.drawable.plus_setting,
R.drawable.minus_setting, R.drawable.multi_setting,
R.drawable.division_setting };
String[] strArray = { "加法", "减法", "乘法", "除法" };
List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
// 测试数据
for (int i = 0; i < 4; i++) {
GridViewItem item = new GridViewItem(intArray[i], strArray[i]);
HashMap<String, GridViewItem> tempHashMap = new HashMap<String, GridViewItem>();
tempHashMap.put("item", item);
hashMapList.add(tempHashMap);
}
MyAdapter myAdapter = new MyAdapter(this, hashMapList);
contentView.setAdapter(myAdapter);
contentView.setOnItemClickListener(new OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view,
int position, long id) {
img_oprate.setImageResource(intArray[position]);
caozuo = position;// 存储下来位置，0是+ 以此类推
popupWindow.dismiss();
}
 
});
} else {
String[] strArray = { "初级", "中级", "高级" };
List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
// 测试数据
for (int i = 0; i < 3; i++) {
GridViewItem item = new GridViewItem(R.drawable.star,
strArray[i]);
HashMap<String, GridViewItem> tempHashMap = new HashMap<String, GridViewItem>();
tempHashMap.put("item", item);
hashMapList.add(tempHashMap);
}
MyAdapter myAdapter = new MyAdapter(this, hashMapList);
contentView.setAdapter(myAdapter);
contentView.setOnItemClickListener(new OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view,
int position, long id) {
nandu = position;
if (position == 0) {
img_star1.setVisibility(View.VISIBLE);
img_star2.setVisibility(View.GONE);
img_star3.setVisibility(View.GONE);
} else if (position == 1) {
img_star1.setVisibility(View.VISIBLE);
img_star2.setVisibility(View.VISIBLE);
img_star3.setVisibility(View.GONE);
} else {
img_star1.setVisibility(View.VISIBLE);
img_star2.setVisibility(View.VISIBLE);
img_star3.setVisibility(View.VISIBLE);
}
popupWindow.dismiss();
}
 
});
}
 
popupWindow.setTouchable(true);
 
popupWindow.setTouchInterceptor(new OnTouchListener() {
 
@Override
public boolean onTouch(View v, MotionEvent event) {
 
Log.i("mengdd", "onTouch : ");
 
return false;
// 这里如果返回true的话，touch事件将被拦截
// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
}
});
 
// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
// 我觉得这里是API的一个bug
popupWindow.setBackgroundDrawable(getResources().getDrawable(
R.drawable.bk_ground1));
// 设置好参数之后再show
popupWindow.showAsDropDown(view);
 
}
 
主要界面gameActivity
 
代码里面注释已经够用，这里我说下整体流程
主界面 有三个模式:
训练模式
速算模式
考试模式
点击各个进入自己的设置界面，设置难易程度，设置时间，设置运算符等。
然后带着参数 进入gameActivity界面，
 
参数有：shijian caozuo moshi
 
读取到模式后，根据当前模式进行设置:
 
训练模式：  拿到难易度 和加减乘除操作，开始生成试题即可。
速算模式： 拿到难易程度 和 时间值，这里时间是0-1-2 对应的时间值是int[] time1 = { 3, 5, 7 };// 时间值，然后启动定时器，开始生成试题，开始计算，时间到后提示时间到，可以选择重新开始或者退出。
考试模式：拿到难易度 和时间值，时间对应为final int[] time2 = { 20, 50, 100 };// 时间值 考试模式的值，20分钟 50分钟 100分钟，然后启动定时器，开始生成试题，开始计算，时间到后提示时间到，可以选择重新开始或者退出。
考试模式比速算模式多一个存储成绩的功能，如果时间到，存储下成绩，成绩单那边显示
 
 
关键函数，随机生成计算等式：
 
// 生成随机数
List<Integer> list = random_shuffle(4, 4);
if (moshi != 0) {
caozuo = RANDOM.nextInt(4);
operation.setBackgroundResource(intArray[caozuo]);
}
switch (caozuo) {
case 0:// +
{ // 算出两个值来，计算出结果
int first = RANDOM.nextInt(addArray[nandu]);
int second = RANDOM.nextInt(addArray[nandu]);
int sum = first + second;
// 初始化两个值，显示在界面上
firstQueNo.setText(first + "");
secondQueNo.setText(second + "");
// 生成三个随机答案
HashSet<Integer> s = new HashSet<Integer>();
s.add(sum);
while (s.size() < 4) {
int i = RANDOM.nextInt(sum * 2 + 4);
if (!s.contains(i)) {
s.add(i);
}
}
// 随机显示到答案列表
int index = 0;
for (Integer i : s) {
// 得到界面
TextView item = ViewArray.get(list.get(index));
 
if (i == sum) {
id_ok = item.getId();//
// id，后续判断需要这个值
}
// 设置答案列表数据
item.setText(i + "");
index++;
}
//
}
 
试题生成思路：
 
比如上面的+  根据难易度，选择多少以内的加法
 
生成加数int first = RANDOM.nextInt(addArray[nandu]);
生成被加数 int second = RANDOM.nextInt(addArray[nandu]);
int sum = first + second;  计算结果
然后进行显示第一个数和第二个
后面随机生成4个不同的值，有一个是正确的。
然后将显示四个答案的地方，将正确答案随机生成在一个view上，记录下来正确答案在哪个view
后面判断是否答对了还是错了使用这个判断。

[1]:http://img.blog.csdn.net/20160518161846084
