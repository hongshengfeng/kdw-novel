package com.novel.jdbcThread;

public class NovelUpdateThread implements Runnable{
    // 正在执行的任务数
    private int num;


    public NovelUpdateThread( int num) {
        this.num = num;
    }

    @Override
    public void run() {

        System.out.println("MyRunnable " + num + "执行完毕");


    }
}
