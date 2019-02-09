package com.keduw.schedule;

import com.keduw.util.JedisClient;
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
import java.util.UUID;

@Component
public class IPSchedule {

    private static Logger Log = (Logger) LoggerFactory.getLogger(IPSchedule.class);
    private static final Integer Lock_Timeout = 5; //锁超时
    @Value("user_ip")
    private String user_ip;
    @Value("ip_lock")
    private String ip_lock;
    @Autowired
    private SeoService seoService;
    @Autowired
    private JedisClient jedisClient;

    //每天凌晨统计访客人数
    @Scheduled(cron = "3 0 1 * * ?")
    public void ipInfoCollect() throws Exception{
        String value = UUID.randomUUID().toString();
        Long status = jedisClient.setnx(ip_lock, value);
        if(status == 0){
            return;
        }
        Map<String, String> info = jedisClient.hgetAll(user_ip);
        if(info != null){
            Ipinfo ipinfo = new Ipinfo();
            ipinfo.setTime(DateFormat.addAndSubtractDaysByGetTime(new Date(), -1));
            ipinfo.setNum(info.size());
            int result = seoService.insertInfo(ipinfo);
            if(result > 0){
                jedisClient.del(user_ip);
                jedisClient.del(ip_lock);
            }else{
                Log.info("数据已存在" + new Date());
            }
        }
    }

}
