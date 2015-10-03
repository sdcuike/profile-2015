跟踪用户行为
==============
 1. web应用配置：pom引入依赖
 
```
            <groupId>com.xhqb.profile</groupId>
            <artifactId>xhqbProfile-common</artifactId>
            <version>1.0.0-SNAPSHOT</version>

```

在web.xml里面配置过滤器（顺序放在其它过滤器前）


```
    <filter>
        <filter-name>AccessFilter</filter-name>
        <filter-class>com.xhqb.profile.request.filter.AccessFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>AccessFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>


```
在logback.xml配置文件中，
```
 <include resource="common-log/acess-log.xml" />

```

所记录的日志文件名称：accessLog.xml.log...



dubbo调用时间
==============
 2. dubbo RPC调用时间过滤器配置:
引入pom配置，（见上）。
在dubbo配置文件中，配置：


```
<dubbo:provider id="xhqbProfileDemoServiceProvider"
                    .......
                    filter="elapsedTimeFilter"
                    >

```

上面是服务提提供方配置，若配置消费方，同。
在logback.xml文件中配置：


```
  <include resource="dubboLog-config/dubbo-elapsedTime.xml" />
  <include resource="dubboLog-config/dubbo-log.xml" />
  
```

日志文件名为：dubbo-elapsedTimeFilter.xml.log...

 