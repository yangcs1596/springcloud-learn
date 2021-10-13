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
