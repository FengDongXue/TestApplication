package com.lanwei.governmentstar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/3.
 */

public class TestActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_common_proceed);
    }
   /**
  *
  *
  *
  *
  *
  *
  *
  *  {"data":
  *      [{"flowTitle":"1.公文起草，已完成 ",
  *      "flowContent":"张浩宁已起草公文！",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170205024056495.jpg",
  *      "flowTime":"",
  *      "flowStatus":"1",
  *      "opCreateTime":"2017-04-06 11:26",
  *      "flowBut":hardwork},
  *
  *      {"flowTitle":"2.审核人核稿校对，已完成",
  *      "flowContent":"吴晓军已审核公文！",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
  *      "flowTime":"用时：0小时 0分",
  *      "flowStatus":"2",
  *      "opCreateTime":"2017-04-06 11:26",
  *      "flowBut":"查看核稿意见"},
  *
  *      {"flowTitle":"3.审阅人审阅，已完成",
  *      "flowContent":"李春华已审阅公文！",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/main/default.jpg",
  *      "flowTime":"用时：0小时 0分",
  *      "flowStatus":"3",
  *      "opCreateTime":"2017-04-06 11:27",
  *      "flowBut":"查看审阅意见"},
  *
  *      {"flowTitle":"4.校对人校对，已完成",
  *      "flowContent":"吴晓军已校对公文（默认处理）！",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
  *      "flowTime":"用时：0小时 0分",
  *      "flowStatus":"4",
  *      "opCreateTime":"2017-04-06 11:27",
  *      "flowBut":"查看校对意见"},
  *
  *      {"flowTitle":"5.公文签发，已完成 ",
  *      "flowContent":"王洪军已签发公文！",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/9745ab96-552c-4f04-85e9-93968be6d577_20170320182505218.jpg",
  *      "flowTime":"用时：0小时 0分",
  *      "flowStatus":"5",
  *      "opCreateTime":"2017-04-06 11:28",
  *      "flowBut":"查看签发意见"},
  *
  *      {"flowTitle":"6.公文联合会签，已全部完成 ",
  *      "flowContent":hardwork,
  *      "flowStatus":"6",
  *      "flowBut":"查看会签意见"
  *      "manyPeople":[
  *      {"flowContent":"吴晓军已核发公文！",
  *      "flowTime":"用时：0小时 0分",
  *      "opCreateTime":"2017-04-06 11:31",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",}
  *
  *      {"flowContent":"吴晓军已核发公文！",
  *      "flowTime":"用时：0小时 0分",
  *      "opCreateTime":"2017-04-06 11:31",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",}
  *
  *      ]
  *      },
  *
  *      {"flowTitle":"7.公文核发，已完成 ",
  *      "flowStatus":"7",
  *      "flowBut":"查看核发意见"
  *      "manyPeople":[
  *      {"flowContent":"吴晓军已核发公文！",
  *      "flowTime":"用时：0小时 0分",
  *      "opCreateTime":"2017-04-06 11:31",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",}
  *      ]},
  *
  *      {"flowTitle":"8.正在等待张浩宁归档公文 ",
  *      "flowContent":"",
  *      "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
  *      "flowTime":"",
  *      "flowStatus":"8",
  *      "opCreateTime":"2017-04-06 11:31",
  *      "flowBut":hardwork}],
  *
  *
  *
  *
  *                     "code":"1000D010020170406/0001","isCb":false}

  *
  *
  *{"data":
    *   [{"flowTitle":"1.公文起草，已完成 ",
    *   "flowContent":"张浩宁已起草公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170205024056495.jpg",
    *   "flowTime":"",
    *   "flowStatus":"1",
    *   "opCreateTime":"2017-04-06 11:26",
    *   "flowBut":hardwork,
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"2.审核人核稿校对，已完成",
    *   "flowContent":"吴晓军已审核公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
    *   "flowTime":"用时：0小时 0分",
    *   "flowStatus":"2",
    *   "opCreateTime":"2017-04-06 11:26",
    *   "flowBut":"查看核稿意见",
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"3.审阅人审阅，已完成",
    *   "flowContent":"李春华已审阅公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/main/default.jpg",
    *   "flowTime":"用时：0小时 0分",
    *   "flowStatus":"3",
    *   "opCreateTime":"2017-04-06 11:27",
    *   "flowBut":"查看审阅意见",
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"4.校对人校对，已完成",
    *   "flowContent":"吴晓军已校对公文（默认处理）！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
    *   "flowTime":"用时：0小时 0分",
    *   "flowStatus":"4",
    *   "opCreateTime":"2017-04-06 11:27",
    *   "flowBut":"查看校对意见",
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"5.公文签发，已完成 ",
    *   "flowContent":"王洪军已签发公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/9745ab96-552c-4f04-85e9-93968be6d577_20170320182505218.jpg",
    *   "flowTime":"用时：0小时 0分",
    *   "flowStatus":"5",
    *   "opCreateTime":"2017-04-06 11:28",
    *   "flowBut":"查看签发意见",
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"6.公文联合会签，已全部完成 ",
    *   "flowContent":hardwork,
    *   "flowImgUrl":hardwork,
    *   "flowTime":hardwork,
    *   "flowStatus":"6",
    *   "opCreateTime":hardwork,
    *   "flowBut":hardwork,
    *   "manyPeople":[
    *   {"flowTitle":hardwork,
    *   "flowContent":"闻君孝已会签公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/main/default.jpg",
    *   "flowTime":hardwork,"flowStatus":"6",
    *   "opCreateTime":"2017-04-06 11:31",
    *   "flowBut":"查看会签意见","manyPeople":hardwork},
    *
    *   {"flowTitle":hardwork,
    *   "flowContent":"吴晓军已会签公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
    *   "flowTime":hardwork,
    *   "flowStatus":hardwork,
    *   "opCreateTime":"2017-04-06 11:31",
    *   "flowBut":"查看会签意见",
    *   "manyPeople":hardwork}]},
    *
    *   {"flowTitle":"7.公文核发，已完成 ",
    *   "flowContent":"吴晓军已核发公文！",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
    *   "flowTime":"用时：0小时 0分",
    *   "flowStatus":"7",
    *   "opCreateTime":"2017-04-06 11:31",
    *   "flowBut":"查看核发意见",
    *   "manyPeople":hardwork},
    *
    *   {"flowTitle":"8.正在等待张浩宁归档公文 ",
    *   "flowContent":"",
    *   "flowImgUrl":"http://127.0.0.1:188/resources/images/head/7a57752e-1000-401d-a6b0-c3a0015d10bb_20170307164623798.jpg",
    *   "flowTime":"",
    *   "flowStatus":"8",
    *   "opCreateTime":"2017-04-06 11:31",
    *   "flowBut":hardwork,
    *   "manyPeople":hardwork}],
    *
    *   "code":"1000D010020170406/0001",
    *   "isCb":false}

    *
    *
    *
    *
    * {"data":
    * [{"flowTitle":"1.公文起草，已完成 ",
    * "flowContent":"张浩宁已起草公文！",
    * "flowImgUrl":"http://121.42.29.226:80/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170205024056495.jpg",
    * "flowTime":"",
    * "flowStatus":"1",
    * "opCreateTime":"2017-04-06 17:14"},
    *
    * {"flowTitle":"2.正在等待吴晓军审核 ",
    * "flowContent":"",
    * "flowImgUrl":"http://121.42.29.226:80/resources/images/head/18537d2f-5466-4279-b4be-ab66e2c96d7b_20170205024056495.jpg",
    * "flowTime":"",
    * "flowStatus":"2",
    * "opCreateTime":"2017-04-06 17:14"}],
    *
    * "code":"1000D020020170406/0001",
    * "isCb":false}
  *


*/


}
