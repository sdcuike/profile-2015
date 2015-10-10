### 1. 配置日志环境变量目录：

 --  设置应用日志根目录系统环境变量：
 环境变量名：  APP_LOG_HOME
     
--   日志路径项目名称设置：
 logback.xml配置的最前面：
 
 ```
 
 <variable name="project_home" value="demoService" />
 
 ``` 
 
 value为其当期应用名称。
 

### 2.引入依赖包（pom）

```

            <groupId>com.xhqb.profile</groupId>
            <artifactId>xhqbProfile-common</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            
 ```

### 3.web应用配置过滤器

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
在logback.xml配置文件中，加入一行：

```
 <include resource="common-log/acess-log.xml" />

```

所记录的日志文件名称：accessLog.xml.log...




### 4. dubbo调用时间/系统调用跟踪
  
在dubbo配置文件中，配置：


```

--  服务提供方：
<dubbo:provider id="xhqbProfileDemoServiceProvider"
                    .......
                    filter="providerElapsedTimeFilter"
                    >
  
 --  消费方：
 <dubbo:consumer id="xhqbProfileDemoClientConsumer"
                    filter="consumerElapsedTimeFilter"                  
                         ........../>

```


在logback.xml文件中配置：


```
  
  <include resource="dubboLog-config/dubbo-elapsedTime.xml" />
  <include resource="dubboLog-config/dubbo-log.xml" />
  
```

日志文件名为：dubbo-elapsedTimeFilter.xml.log...


dubbo的标准配置见例子： xhqbProfile-demoService 项目（见上项目）
配置的每个<dubbo:service> 都默认引用本地的一个  <dubbo:provider>  配置。
配置的每个<dubbo:reference>都默认引用本地的一个 <dubbo:consumer/> 配置。
（详细：[http://dubbo.io/](http://dubbo.io/))
 
 

###5.spring aop切面配置：

--  .在自己的应用配置文件中配置spring aop配置：
例如：

```

    	<aop:aspectj-autoproxy proxy-target-class="true" />
	<bean id="methodInvokeTimeAspect" class="com.xhqb.profile.spring.aop.method.MethodInvokeTimeAspect" />

	<aop:config>
		<aop:aspect ref="methodInvokeTimeAspect">
			<aop:pointcut id="methodInvokeTimePC"
				expression=" (execution(public * com.xhqb.business.service.client.impl.*.*(..)) ) or  (execution(public * com.xhqb.business.service.impl.*.*(..)) )  " />
			<aop:around pointcut-ref="methodInvokeTimePC" method="profile" />
		</aop:aspect>
	</aop:config>


```

expression 换成自己的业务包名。

-- logback.xml引入配置：

   ```
   
  <include resource="spring-aop/aop-elapsedTime.xml" />

```


###6.logback.xml自己定义appender

如果自己在logback.xml中配置了appender，请在pattern 格式处添加：[%X{uuid}] 。
例如：(加粗字体)

```

<appender name="defaultAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${APP_LOG_HOME}/logs/${project_home}/clientDeault.xml.log</file>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<FileNamePattern>${APP_LOG_HOME}/logs/${project_home}/clientDeault.xml.log.out.%d{yyyy-MM-dd}
			</FileNamePattern>
		</rollingPolicy>
		<encoder>
			<pattern> ***[%X{uuid}]***   %-16(%d{MM-dd HH:mm:ss}) %-5thread %-5level %logger -%msg%n</pattern>
		</encoder>
	</appender>
	
```

### 附：
日志格式摘要：dubbo-elapsedTimeFilter.xml 文件部分内容：

```

消费者：
10-09 19:52:15   main  INFO  com.xhqb.profile.dubbo.extend.filter.ConsumerElapsedTimeFilter -[a963b1b6-2b4d-43e4-8334-02d424aaa2e8] , [interface com.xhqb.profile.dubbo.service.DemoService], [getName], [ss, man], [返回值], [null], [10102ms]   


生产者：
10-09 19:52:15   DubboServerHandler-127.0.0.1:20880-thread-1 INFO  com.xhqb.profile.dubbo.extend.filter.ProviderElapsedTimeFilter -[a963b1b6-2b4d-43e4-8334-02d424aaa2e8|5193c0d3-212e-45dc-80dd-0e9775f5d21a] , [interface com.xhqb.profile.dubbo.service.DemoService], [getName], [ss, man], [返回值], [null], [10020ms]   

```

 