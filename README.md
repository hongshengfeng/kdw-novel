<ul>
    <li>进入novel-portal目录，运行 mvn clean compile package</li>
    <li>进入target中获取novel-exec.jar，这里pom.xml默认打包方式是jar</li>
    <li>运行 java -jar novel-exec.jar > novel.log 2>&1 & 项目就在后台运行了</li>
    <li>关闭服务运行 ps -ef | grep novel-exec.jar 得到pid，kill掉</li>
    <li>novel-crawler则是爬虫模块</li>
</ul>
