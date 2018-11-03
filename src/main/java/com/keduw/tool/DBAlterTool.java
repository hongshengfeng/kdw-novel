package com.keduw.tool;

import java.io.File;
import java.io.FileWriter;

//数据库修改工具
public class DBAlterTool {

    public static void main(String[] args) throws Exception{
        createChapter();
    }

    public static void createChapter() throws Exception{
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(new File("D://chaper.sql"));
            for(int i = 0; i < 100; i++){
                StringBuilder builder = new StringBuilder();
                builder.append("create table chapter_" + i + "(id int not null auto_increment,");
                builder.append("nId int not null,");
                builder.append("name varchar(50) not null,");
                builder.append("content text,");
                builder.append("link varchar(100) not null,");
                builder.append("primary key(id, nId))engine=innodb default charset=utf8;");
                fileWriter.write(builder.toString());
                fileWriter.write("\r\n");
                fileWriter.flush();
            }
        }catch (Exception e){
            if(fileWriter != null){
                fileWriter.close();
            }
        }
    }
}
