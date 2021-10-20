# springcloud-learn
springcloud项目学习实例

特殊符号树 ├─
```
├──项目结构说明
├──cloudnet 后台项目admin
      ├──config      配置层（处理部分配置
      ├──controller  接口层（对外提供接口、第三方封装新服务，耦合功能
      ├──entity      实体层（与数据库表一一对应
      ├──mapper      数据接口层（与数据库处理
      ├──phoenix     phoenix的业务处理层
      ├──service     数据逻辑层transaction（不希望处理业务，分开，提供普通查询插入
      ├──biz         业务处理层
      ├──template    服务模板
├──cloud-common 公共层
      ├──common-core  核心
```


| 节点        | 角色说明                               |
| ----------- | -------------------------------------- |
| `Provider`  | 暴露服务的服务提供方                   |
| `Consumer`  | 调用远程服务的服务消费方               |
| `Registry`  | 服务注册与发现的注册中心               |
| `Monitor`   | 统计服务的调用次数和调用时间的监控中心 |
| `Container` | 服务运行容器                           |

|1  |  2 |
|---|----|
|   |    |