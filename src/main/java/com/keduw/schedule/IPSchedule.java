package com.keduw.schedule;

import com.keduw.jedis.JedisClient;
import com.keduw.model.Ipinfo;
import com.keduw.service.SeoService;
import com.keduw.util.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class IPSchedule {

    private static Logger Log = (Logger) LoggerFactory.getLogger(IPSchedule.class);
    @Value("user_ip")
    private String user_ip;
    @Autowired
    private SeoService seoService;
    @Autowired
    private JedisClient jedisClient;

    //每天凌晨统计访客人数
    @Scheduled(cron = "0 0 1 * * ?")
    public void ipInfoCollect() throws Exception{
        Map<String, String> info = jedisClient.hgetAll(user_ip);
        if(info != null){
            Ipinfo ipinfo = new Ipinfo();
            ipinfo.setTime(DateFormat.addAndSubtractDaysByGetTime(new Date(), -1));
            ipinfo.setNum(info.size());
            int result = seoService.insertInfo(ipinfo);
            if(result > 0){
                jedisClient.del(user_ip);
            }else{
                Log.info("数据已存在" + new Date());
            }
        }else{
            Log.info("统计IP数据为空" + new Date());
        }
    }
}
