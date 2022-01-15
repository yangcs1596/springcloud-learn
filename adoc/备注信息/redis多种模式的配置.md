```yaml
spring:
  redis:
    host: 192.168.40.201
    port: 6379
    password: passw0rd
    database: 0 # 数据库索引，默认0
    timeout: 5000  # 连接超时，单位ms
    jedis:  # 或lettuce, 连接池配置，springboot2.0中使用jedis或者lettuce配置连接池，默认为lettuce连接池
      pool:
        max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1 # 连接池分配连接最大阻塞等待时间（阻塞时间到，抛出异常。使用负值表示无限期阻塞）
        max-idle: 8 # 连接池中的最大空闲连接数
        min-idle: 0 # 连接池中的最小空闲连接数
```

```yaml
spring:
  redis:
    password: passw0rd
    timeout: 5000
    sentinel:
      master: mymaster
      nodes: 192.168.40.201:26379,192.168.40.201:36379,192.168.40.201:46379 # 哨兵的IP:Port列表
    jedis: # 或lettuce
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 8
        min-idle: 0
```

```yaml
spring:
  redis:
    password: passw0rd
    timeout: 5000
    database: 0
    cluster:
      nodes: 192.168.40.201:7100,192.168.40.201:7200,192.168.40.201:7300,192.168.40.201:7400,192.168.40.201:7500,192.168.40.201:7600
      max-redirects: 3  # 重定向的最大次数
    jedis:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 8
        min-idle: 0
```