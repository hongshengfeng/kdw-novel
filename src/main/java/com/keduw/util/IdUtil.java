package com.keduw.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 生产随机数Id
 * @Author: 林浩东
 */
public class IdUtil {

    public synchronized static long getId(){
        return Math.abs(ThreadLocalRandom.current().nextLong());
    }
}
