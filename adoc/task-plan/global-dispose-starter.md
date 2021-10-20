# 全局处理封装starter
global-unified-dispose-starter

### 1、引入依赖
```xml
<dependency>
    <groupId>com.safedog.cloudnet</groupId>
    <artifactId>global-unified-dispose-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

###2、配置示例

```yaml
dispose:
  advice-filter-class: com.safedog.cloudnet.controller.TestController
  advice-filter-package: com.safedog
```

# 全局登录权限拦截starter
描述：统一用户权限，登录校验等
根据 authorization 登录返回token给前端，（或者api调用根据appId和 secret的数字签名加密）
再根据token查询 用户角色的权限

# 系统Redisson自动装配starter
https://gitee.com/tumao2/hdw-dubbo/tree/master/hdw-common

# 系统redis自动装配 starter

# 系统rabbitmq自动装配starter

