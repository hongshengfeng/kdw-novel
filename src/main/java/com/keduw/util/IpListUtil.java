package com.keduw.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: novelSpider
 * @Package: com.keduw.util
 * @ClassName: IpListUtil
 * @Description: java类作用描述
 * @Author: 林浩东
 * @CreateDate: 2018/10/7/007 23:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2018/10/7/007 23:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class IpListUtil {
    /**
     * 从Request对象中获得客户端IP，处理了HTTP代理服务器和Nginx的反向代理截取了ip
     * @param request
     * @return ip
     */
    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
