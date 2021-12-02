#说明
Log4j有三个主要的组件：
 * Loggers(记录器)
 * Appenders (输出源)
 * Layouts(布局)
 
## Loggers
```
分为五个级别：DEBUG、INFO、WARN、ERROR和FATAL。
这五个级别是有顺序的，DEBUG < INFO < WARN < ERROR < FATAL
```
## Appenders
 ```java
org.apache.log4j.ConsoleAppender（控制台）
org.apache.log4j.FileAppender（文件）
org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
```

## 布局
```
org.apache.log4j.HTMLLayout（以HTML表格形式布局）
org.apache.log4j.PatternLayout（可以灵活地指定布局模式）
org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串）
org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等信息）
```


## 格式化符号说明：

|符号  |  说明 |
|---|----|
|%p|输出日志信息的优先级，即DEBUG，INFO，WARN，ERROR，FATAL。|
|%d|输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，如：%d{yyyy/MM/dd HH:mm:ss,SSS}。|
|%r|输出自应用程序启动到输出该log信息耗费的毫秒数。|
|%t|输出产生该日志事件的线程名。|
|%l|输出日志事件的发生位置，相当于%c.%M(%F:%L)的组合，包括类全名、方法、文件名以及在代码中的行数。例如：test.TestLog4j.main(TestLog4j.java:10)。|
|%c|输出日志信息所属的类目，通常就是所在类的全名。|
|%M|输出产生日志信息的方法名。|
|%F|输出日志消息产生时所在的文件名称。|
|%L|：输出代码中的行号。|
|%m|：输出代码中指定的具体日志信息。|
|%n|输出一个回车换行符，Windows平台为"rn"，Unix平台为"n"。|
|%x|输出和当前线程相关联的NDC(嵌套诊断环境)，尤其用到像java servlets这样的多客户多线程的应用中。|
|%%|输出一个"%"字符。|

```
另外，还可以在%与格式字符之间加上修饰符来控制其最小长度、最大长度、和文本的对齐方式。如：
1) c：     指定输出category的名称，最小的长度是20，如果category的名称长度小于20的话，默认的情况下右对齐。
2) %-20c： "-"号表示左对齐。
3) %.30c： 指定输出category的名称，最大的长度是30，如果category的名称长度大于30的话，就会将左边多出的字符截掉，但小于30的话也不会补空格。
```

## 示例 log4j.properties文件
```
log4j.rootLogger=CONSOLE,stdout,logfile

#stdout控制器
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout

#输出格式
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] %m%n

#文件路径输出
log4j.appender.logfile=org.apache.log4j.RollingFileAppender
log4j.appender.logfile.File=D:/keyservice.log
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] %m%n
```