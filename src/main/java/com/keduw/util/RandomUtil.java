package com.keduw.util;

import java.util.Random;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.util
 * @ClassName: RandomUtil
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/11/19/019 23:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/11/19/019 23:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RandomUtil {


        //生成随机数字和字母,
        public static String getStringRandom(int length) {

            String val = "";
            Random random = new Random();

            //参数length，表示生成几位随机数
            for(int i = 0; i < length; i++) {

                String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
                //输出字母还是数字
                if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                    int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    val += (char)(random.nextInt(26) + temp);
                } else if( "num".equalsIgnoreCase(charOrNum) ) {
                    val += String.valueOf(random.nextInt(10));
                }
            }
            return val;
        }

}
