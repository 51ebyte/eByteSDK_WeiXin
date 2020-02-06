
## 微信公众号
```
static String appid = "wx5ddb95e1baae0d7f";
static String secret = "c5e6aeb09061d2b83e6959dfc9fb7e50";
public static com.ebyte.officialAccount.OfficialAccount officialAccount = com.ebyte.weixin.Weixin.officialAccount(appid, secret);
public static void main(String[] args) throws Exception {}
```

### 开始开发
#### 接入指南
```
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    String token = "wxToken";
    officialAccount.join(response, request, token);
}
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    String token = "wxToken";
    String signature = request.getParameter("signature");
    String timestamp = request.getParameter("timestamp");
    String nonce = request.getParameter("nonce");
    String echostr = request.getParameter("echostr");
    officialAccount.join(response, token, timestamp, nonce, signature, echostr);
}
```

#### 获取Access Token
```    
try {
    officialAccount.getAccessToken();
} catch (Exception e) {
    e.printStackTrace();
}
```
#### 获取微信服务器 IP (或IP段)
```
officialAccount.getValidIps();
```

#### 清理接口调用次数
```    
ofoffficialAccount.clearQuota();
```

#### 网络检测
```
officialAccount.checkNetwork(String action,String operator);
```
#### 账号迁移openid转换
```
officialAccount.changeOpenid(String appid,ArrayList<String> openid_list);
ArrayList<String> list=new ArrayList<String>();
list.add("o0nOW04PREpZm9kPI3DW4xyQ3KNU");
例：
officialAccount.changeOpenid("wx5ddb95e1baae0d7f",list);
```


### 自定义菜单
#### 创建菜单
```
try {
    
    JSONObject sub_button_item = new JSONObject();
    sub_button_item.put("type", "view");
    sub_button_item.put("name", "搜索");
    sub_button_item.put("url", "http://www.soso.com/");
    JSONObject sub_button_item1 = new JSONObject();
    sub_button_item1.put("type", "miniprogram");
    sub_button_item1.put("name", "wxa");
    sub_button_item1.put("url", "http://mp.weixin.qq.com");
    sub_button_item1.put("appid", "wx286b93c14bbf93aa");
    sub_button_item1.put("pagepath", "pages/lunar/index");
    
    JSONObject sub_button_item2 = new JSONObject();
    sub_button_item2.put("type", "click");
    sub_button_item2.put("name", "赞一下我们");
    sub_button_item2.put("key", "V1001_GOOD");
    
    JSONArray sub_button = new JSONArray();
    sub_button.add(sub_button_item);
    sub_button.add(sub_button_item1);
    sub_button.add(sub_button_item2);

    JSONObject button = new JSONObject();
    button.put("type", "click");
    button.put("name", "今日歌曲");
    button.put("key", "V1001_TODAY_MUSIC");
    
    JSONObject button1 = new JSONObject();
    button1.put("name", "菜单");
    button1.put("sub_button", sub_button);
    
    JSONArray menu = new JSONArray();
    menu.add(button);
    menu.add(button1);
    officialAccount.menu.create(menu);
} catch (Exception e) {
    e.printStackTrace();
}
```

#### 查询菜单 
```    
officialAccount.menu.query();
```

#### 删除菜单 
```    
officialAccount.menu.delete();
```

### 消息管理
#### 接收普通消息
```
response.setHeader("Content-type", "text/html;charset=UTF-8");
response.setCharacterEncoding("UTF-8");
try {
    officialAccount.server.push(request, response, new ServerCallback() {
        @Override
        public String push(Map<String, String> message, Message reply) {
            switch (message.get("MsgType")) {
                case "text":
                    System.out.println("收到文本消息");
                    break;
                case "image":
                    System.out.println("收到图片消息");
                    break;
                case "voice":
                    System.out.println("收到语音消息");
                    break;
                case "video":
                    System.out.println("收到视频消息");
                    break;
                case"shortvideo":
                    System.out.println("收到小视频消息");
                    break;
                case "location":
                    System.out.println("收到坐标消息");
                    break;
                case "link":
                    System.out.println("收到链接消息");
                    break;
                case "event":
                    System.out.println("收到事件消息");
                    break;
                default:
                    System.out.println("收到其它消息");
                    break;
            }
            return null;
        }
    });
} catch (Exception e) {
    e.printStackTrace();
}
```

#### 接收事件推送
#### 被动回复用户消息
```
response.setHeader("Content-type", "text/html;charset=UTF-8");
response.setCharacterEncoding("UTF-8");
try {
    officialAccount.server.push(request, response, new ServerCallback() {
        @Override
        public String push(Map<String, String> message, Message reply) {
            try {
                //回复文本消息
                return reply.text(String text);
                return reply.text().setContent(String text);
                return reply.setText(String text);
                //回复图片消息
                return reply.image(String MediaId);
                return reply.image.setMediaId(String MediaId);
                return reply.setImage(String MediaId);
                //回复语音消息
                return reply.voice(String MediaId);
                return reply.voice.setMediaId(String MediaId);
                return reply.setVoice(String MediaId);
                //回复视频消息
                return reply.video(String MediaId);
                return reply.video(String MediaId, String Title);
                return reply.video(String MediaId, String Title, String Description);
                return reply.video.setMediaId(String MediaId);
                return reply.video.setVideo(String MediaId, String Title);
                return reply.video.setVideo(String MediaId, String Title, String Description);
                return reply.setVideo(String MediaId);
                return reply.setVideo(String MediaId, String Title);
                return reply.setVideo(String MediaId, String Title, String Description);
                //回复音乐消息
                return reply.music(String MediaId);
                return reply.music(String MediaId, String Title);
                return reply.music(String MediaId, String Title, String Description);
                return reply.music.setMediaId(String ThumbMediaId);
                return reply.music.setMusic(String ThumbMediaId, String Title);
                return reply.music.setMusic(String ThumbMediaId, String Title, String Description);
                return reply.music.setMusic(String ThumbMediaId, String Title, String Description, String MusicURL);
                return reply.music.setMusic(String ThumbMediaId, String Title, String Description, String MusicURL, String HQMusicUrl);
                return reply.setMusic(String ThumbMediaId);
                return reply.setMusic(String ThumbMediaId, String Title);
                return reply.setMusic(String ThumbMediaId, String Title, String Description);
                return reply.setMusic(String ThumbMediaId, String Title, String Description, String MusicURL);
                return reply.setMusic(String ThumbMediaId, String Title, String Description, String MusicURL, String HQMusicUrl);
                //回复图文消息
                Map<String, Object> newsItem=new HashMap<String, Object>();
                newsItem.put("Title","Title1");
                newsItem.put("Description","Description1");
                newsItem.put("PicUrl","PicUrl1");
                newsItem.put("Url","Url1");
                Map<String, Object> newsItems=new HashMap<String, Object>();
                newsItems.put("Title","Title2");
                newsItems.put("Description","Description2");
                newsItems.put("PicUrl","PicUrl2");
                newsItems.put("Url","Url2");
                ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                list.add(newsItem);
                list.add(newsItems);
                return reply.news(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    });
} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```

#### 客服消息
##### 客服管理
###### 添加客服
```
try {
    officialAccount.customerServiceMessage.add(String account, String nickname, String password);
} catch (Exception e) {
    e.printStackTrace();
}
```

###### 修改客服
```
try {
    officialAccount.customerServiceMessage.update(String account, String nickname, String password);
} catch (Exception e) {
    e.printStackTrace();
}
```

###### 删除客服
```
try {
    officialAccount.customerServiceMessage.delete(String account, String nickname, String password);
} catch (Exception e) {
    e.printStackTrace();
}
```

###### 获取客服列表
```
try {
    officialAccount.customerServiceMessage.getlist();
} catch (Exception e) {
    e.printStackTrace();
}
```

##### 消息转发（将接收到的消息转发给客服）
```
response.setHeader("Content-type", "text/html;charset=UTF-8");
response.setCharacterEncoding("UTF-8");
try {
    officialAccount.server.push(request, response, new ServerCallback() {
        @Override
        public String push(Map<String, String> message, Message reply) {
            try {
                // 转发收到的消息给客服
                new Transfer();
                // 指定转发给某一个客服
                return new Transfer(String openid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    });
} catch (Exception e) {
    e.printStackTrace();
}
```

##### 作为客服消息发送（把消息单发给用户）
```
try {
    officialAccount.customerServiceMessage.text(String text).to(String openid).send();
    officialAccount.customerServiceMessage.image(String media_id).to(String openid).send();
    officialAccount.customerServiceMessage.voice(String media_id).to(String openid).send();
    officialAccount.customerServiceMessage.video(String media_id, String thumb_media_id, String title, String description).to(String openid).send();
    officialAccount.customerServiceMessage.music(String title, String description, String musicurl, String hqmusicurl,
        String thumb_media_id).to(String openid).send();
    officialAccount.customerServiceMessage.news(String title, String description, String url, String picurl).to(String openid).send();
    officialAccount.customerServiceMessage.mpnews(String media_id).to(String openid).send();
    officialAccount.customerServiceMessage.msgmenu(String head_content,String tail_content).to(String openid).send();
} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```

##### 作为客服消息发送（把消息群发给用户）
```    
try {
    JSONArray user = new JSONArray();
    user.add("o0nOW04PREpZm9kPI3DW4xyQ3KNU1");
    user.add("o0nOW04PREpZm9kPI3DW4xyQ3KNU2");
    officialAccount.customerServiceMessage.text(String text).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.image(String media_id).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.voice(String media_id).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.video(String media_id, String thumb_media_id, String title, String description).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.music(String title, String description, String musicurl, String hqmusicurl,
        String thumb_media_id).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.news(String title, String description, String url, String picurl).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.mpnews(String media_id).to(JSONArray openids).send();
    officialAccount.customerServiceMessage.msgmenu(String head_content,String tail_content).to(JSONArray openids).send();
} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
```

##### 客服输入状态
```    
try {
    //对用户下发“正在输入"状态
    officialAccount.customerServiceMessage.typing(String openid);
    officialAccount.customerServiceMessage.typing(String openid, boolean cancel);
    //例：
    officialAccount.customerServiceMessage.typing("o0nOW04PREpZm9kPI3DW4xyQ3KNU");
    officialAccount.customerServiceMessage.typing("o0nOW04PREpZm9kPI3DW4xyQ3KNU", false);
    //取消对用户下发“正在输入"状态
    officialAccount.customerServiceMessage.typing(String openid, boolean cancel);
    //例：
    officialAccount.customerServiceMessage.typing("o0nOW04PREpZm9kPI3DW4xyQ3KNU", true);
    
} catch (Exception e) {
    e.printStackTrace();
}
```

#### 其他说明
##### 请求消息的属性
* 当你接收到用户发来的消息时，可能会提取消息中的相关属性，参考：<br/>
请求消息基本属性(以下所有消息都有的基本属性)：

```
ToUserName 接收方帐号（该公众号 ID）
FromUserName 发送方帐号（OpenID, 代表用户的唯一标识）
CreateTime 消息创建时间（时间戳）
MsgId 消息 ID（64位整型）
```

* 文本

```
MsgType text
Content 文本消息内容
```
    
* 图片

```
MsgType image
MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
PicUrl 图片链接
```
    
* 语音

```
MsgType voice
MediaId 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
Format 语音格式，如 amr，speex 等
Recognition * 开通语音识别后才有
请注意，开通语音识别后，用户每次发送语音给公众号时，微信会在推送的语音消息XML数据包中，增加一个 Recongnition 字段
```
    
* 视频

```
MsgType video
MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
ThumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
```
    
* 小视频

```
MsgType shortvideo
MediaId 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
ThumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
```
    
* 事件

```
MsgType event
Event 事件类型 （如：subscribe(订阅)、unsubscribe(取消订阅) ...， CLICK 等）
```
        
* 扫描带参数二维码事件

```
EventKey 事件KEY值，比如：qrscene_123123，qrscene_为前缀，后面为二维码的参数值
Ticket 二维码的 ticket，可用来换取二维码图片    
```
    
* 地理位置

```
Latitude 23.137466 地理位置纬度
Longitude 113.352425 地理位置经度
Precision 119.385040 地理位置精度
```

* 自定义菜单事件

```
EventKey 事件KEY值，与自定义菜单接口中KEY值对应，如：CUSTOM_KEY_001, www.qq.com
```

* 地理位置

```
MsgType location
Location_X 地理位置纬度
Location_Y 地理位置经度
Scale 地图缩放大小
Label 地理位置信息
```

* 链接

```
MsgType link
Title 消息标题
Description 消息描述
Url 消息链接
```

##### 消息类型
* 文本消息

```
content 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）

new Text().setContent(String content);
```
    
* 图片消息

```
MediaId 通过素材管理中的接口上传多媒体文件，得到的id

new Image().setMediaId(String MediaId);
```
* 语音消息

```
MediaId 通过素材管理中的接口上传多媒体文件，得到的id

new Voice().setMediaId(String MediaId);
```
* 视频消息

```
MediaId 通过素材管理中的接口上传多媒体文件，得到的id
Title  视频消息的标题
Description  视频消息的描述

new Video().setMediaId(String MediaId);        
new Video().setVideo(String MediaId, String Title);        
new Video().setVideo(String MediaId, String Title, String Description);        
```

* 音乐消息

```
ThumbMediaId 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
Title 音乐标题
Description 音乐描述
MusicURL 音乐链接
HQMusicUrl 高质量音乐链接，WIFI环境优先使用该链接播放音乐

new Music().setMediaId(String ThumbMediaId);
new Music().setMediaId(String ThumbMediaId, String Title);
new Music().setMediaId(String ThumbMediaId, String Title, String Description);
new Music().setMediaId(String ThumbMediaId, String Title, String Description, String MusicURL);
new Music().setMediaId(String ThumbMediaId, String Title, String Description, String MusicURL, String HQMusicUrl);
```

* 图文消息

```
//注：当用户发送文本、图片、视频、图文、地理位置这五种消息时，开只能回复1条图文消息；其余场景最多可回复8条图文消息

Title 图文消息标题
Description 图文消息描述
PicUrl 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
Url 点击图文消息跳转链接

Map<String, Object> newsItem = new HashMap<String, Object>();
    newsItem.put("Title","Title1");
    newsItem.put("Description","Description1");
    newsItem.put("PicUrl","PicUrl1");
    newsItem.put("Url","Url1");
Map<String, Object> newsItem1=new HashMap<String, Object>();
    newsItem1.put("Title","Title2");
    newsItem1.put("Description","Description2");
    newsItem1.put("PicUrl","PicUrl2");
    newsItem1.put("Url","Url2");
ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    list.add(newsItem);
    list.add(newsItem1);
new News().set(ArrayList<Map<String, Object>> list);

Map<String, Object> Item = new HashMap<String, Object>();
    Item.put("Title","Title");
    Item.put("Description","Description");
    Item.put("PicUrl","PicUrl");
    Item.put("Url","Url");
new News().set(Map<String, Object> Item);
```

### 网页开发授权
#### 网页授权
##### 发起授权
```
URL redirect_url = new URL("http://www.sunlue.com"); //授权后重定向的回调链接地址
Weixin.oauth
    .scopes("snsapi_userinfo") //应用授权作用域，默认静默授权（snsapi_base）
    .state("1234") //重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
    .redirect(HttpServletResponse response, redirect_url);
```

##### 拉取用户信息
```
    JSONObject userInfo = Weixin.oauth.user(HttpServletRequest request);
    userInfo.get("openid");
```
#### JS-SDK

### 素材管理
### 图文消息留言管理

### 用户管理
#### 标签管理
##### 创建标签
```
officialAccount.tags.create(String name);
```
##### 修改标签
```
officialAccount.tags.update(int id, String name);
```
##### 删除标签
```
officialAccount.tags.delete(int id);
```
##### 获取标签
```
officialAccount.tags.get();
```
##### 获取标签下粉丝列表
```
officialAccount.user.gettag(int tagid);
officialAccount.user.gettag(int tagid,String next_openid)
```
##### 批量为用户打标签
```
officialAccount.tags.members_batchtagging(int tagid, JSONArray openid_list);
officialAccount.tags.members_batchtagging(int tagid, ArrayList<String> list);
```
##### 批量为用户取消标签
```
officialAccount.tags.members_batchuntagging(int tagid, JSONArray openid_list);
officialAccount.tags.members_batchuntagging(int tagid, ArrayList<String> list);
```
##### 获取用户身上的标签列表
```
officialAccount.tags.getidlist(String openid);
```
#### 设置用户备注名
```
officialAccount.user.updateremark(String openid, String remark)
```
#### 获取用户信息
##### 获取用户基本信息(UnionID机制)
```
officialAccount.user.info(String openid)
```
##### 批量获取用户基本信息
```
officialAccount.user.infos(ArrayList<String> openids)
```
#### 获取用户列表
```
officialAccount.user.list();
officialAccount.user.list(String next_openid);
```
#### 获取用户地理位置
```
protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    try {
        officialAccount.server.push(request, response, new ServerCallback() {
            @Override
            public String push(Map<String, String> message, Message reply) {
                System.out.println(message);
                return null;
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```
#### 黑名单管理
##### 获取黑名单
```
officialAccount.user.blacklist()
```
##### 拉黑用户
```
officialAccount.user.black(ArrayList<String> openid_list);
例：
ArrayList<String> list=new ArrayList<String>();
list.add("o0nOW04PREpZm9kPI3DW4xyQ3KNU");
officialAccount.user.black(list);
```
##### 取消拉黑用户
```
officialAccount.user.unblack(ArrayList<String> openid_list);
例：
ArrayList<String> list=new ArrayList<String>();
list.add("o0nOW04PREpZm9kPI3DW4xyQ3KNU");
officialAccount.user.unblack(list)
```
### 账号管理
#### 二维码
##### 创建临时二维码
```
/**
 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
 * @param expire    二维码有效期
 * @param type      输出参数（可选值：url|ticket|image）
 */
officialAccount.qrcode.temporary(int scene_id, int expire);
officialAccount.qrcode.temporary(int scene_id, int expire, String type);
officialAccount.qrcode.temporary(String scene_str, int expire);
officialAccount.qrcode.temporary(String scene_str, int expire, String type);
```
##### 创建永久二维码
```
/**
 * @param scene_id 二维码详细信息（场景值ID,32位非0整型）
 * @param scene_str 二维码详细信息（场景值ID,字符串形式的ID，长度限制为1到64）
 * @param type      输出参数（可选值：url|ticket|image）
 */
officialAccount.qrcode.forever(int scene_id);
officialAccount.qrcode.forever(int scene_id, String type);
officialAccount.qrcode.forever(String scene_str);
officialAccount.qrcode.forever(String scene_str, String type);
```
#### 短链接
```
officialAccount.shorturl(URL url);
```

----------------------------------------


## 微信支付
```
static String appid = "wxc0ae6e64361f2df5";
static String secret = "ead02f9b44240da485d71cb286f564ed";
static String mchid = "1239809802";
static String mchkey = "FC51C09DA4607844D6723B817E25822B";
static String notify_url = "http://www.sunlue.com/weixin/pay/notify";

public static Payment payment = Weixin.payment(appid, secret, mchid, mchkey, notify_url);

public static void main(String[] args) throws Exception {
    payment.setSANDBOX();//设置支付环境为沙箱环境
    payment.setSANDBOX(false);//解除支付沙箱环境
}
```
### 支付订单
#### 统一下单

```
Map<String, String> data = new HashMap<String, String>();
data.put("body", "支付网站服务费");//商品描述
data.put("out_trade_no", "SN2020010100000000000000000001");//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
data.put("total_fee", "1000");//订单总金额，单位为分
data.put("notify_url", "http://www.sunlue.com/weixin/pay/notify");//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
data.put("trade_type", "JSAPI");//支付类型
data.put("openid", "ok9y9jlbo_zxAQLhkF6flbbJzUvo");
try {
    Map<String, String> resp = payment.order.unify(data);
} catch (Exception e) {
    e.printStackTrace();
}
```
    
#### 提交公众号、小程序支付
```
Map<String, String> data = new HashMap<String, String>();
data.put("body", "支付网站服务费");//商品描述
data.put("out_trade_no", "SN2020010100000000000000000001");//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
data.put("total_fee", "1000");//订单总金额，单位为分
data.put("notify_url", "http://www.sunlue.com/weixin/pay/notify");//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
data.put("openid", "ok9y9jlbo_zxAQLhkF6flbbJzUvo");
try {
    Map<String, String> resp = payment.pay.JSAPI(data);
} catch (Exception e) {
    e.printStackTrace();
}
```
    
#### 查询订单
```
payment.order.queryByTransactionId(transaction_id); //根据微信订单号查询
payment.order.queryByOutTradeNumber(out_trade_no); //根据商户订单号查询
```

#### 关闭订单
```
payment.order.close(out_trade_no);
```

### 通知
* 支付结果通知(在用户成功支付后，微信服务器会向该 订单中设置的回调URL 发起一个 POST 请求，请求的内容为一个 XML。里面包含了所有的详细信息)

```
Map<String, String> notify_data = payment.notify.unify(HttpServletRequest request, HttpServletResponse response);
```
    