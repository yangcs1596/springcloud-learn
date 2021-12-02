修改nacos密码
```java
生成nacos密码
需要先引入依赖
<dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
</dependency>
然后执行下边这个代码
 new BCryptPasswordEncoder().encode("123456");

使用的数据库为 nacos， 用户表 users
修改用户账号密码就是替换users表的账户密码信息

```

待解决mysql的自动填充问题