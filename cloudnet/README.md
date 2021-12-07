##项目结构
--

banner-txt的配置
```
${AnsiColor.BRIGHT_YELLOW}： 设置控制台中输出内容的颜色
${application.version}： 用来获取MANIFEST.MF文件中的版本号
${application.formatted-version}：格式化后的${application.version}版本信息
${spring-boot.version}：Spring Boot的版本号
${spring-boot.formatted-version}：格式化后的${spring-boot.version}版本信息
```


ik分词器
IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。也可以引入其他的中文分词器，本文使用IK分词器，注意：IK分词器的版本号，要与ES的版本一致，如不一致则无法启动。
如果不引入中文分词器，那么ES会默认将每一个中文都会进行分词，不会智能组词。

下载地址：
https://github.com/medcl/elasticsearch-analysis-ik/releases

源代码:
https://github.com/medcl/elasticsearch-analysis-ik

#cron表达式示例
```
Cron表达式范例：
每隔5秒执行一次：                   */5 * * * * ?
每隔1分钟执行一次：                 0 */1 * * * ?
每天23点执行一次：                  0 0 23 * * ?
每天凌晨1点执行一次：               0 0 1 * * ?
每月1号凌晨1点执行一次：            0 0 1 1 * ?
每月最后一天23点执行一次：          0 0 23 L * ?
每周星期天凌晨1点实行一次：         0 0 1 ? * L
在26分、29分、33分执行一次：        0 26,29,33 * * * ?
每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
```


