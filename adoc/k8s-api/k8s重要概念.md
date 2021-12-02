1. cluster
```
cluster是 计算、存储和网络资源的集合，k8s利用这些资源运行各种基于容器的应用。
```
 

2.master
```
master是cluster的大脑，他的主要职责是调度，即决定将应用放在那里运行。master运行linux操作系统，可以是物理机或者虚拟机。为了实现高可用，可以运行多个master。
```
 

3.node
```
node的职责是运行容器应用。node由master管理，node负责监控并汇报容器的状态，同时根据master的要求管理容器的生命周期。node运行在linux的操作系统上，可以是物理机或者是虚拟机。
```
 

4.pod
```
pod是k8s的最小工作单元。每个pod包含一个或者多个容器。pod中的容器会作为一个整体被master调度到一个node上运行。
```
 

5.controller
```
k8s通常不会直接创建pod,而是通过controller来管理pod的。controller中定义了pod的部署特性，比如有几个剧本，在什么样的node上运行等。为了满足不同的业务场景，k8s提供了多种controller，包括deployment、replicaset、daemonset、statefulset、job等。
```
 

6.deployment
```
是最常用的controller。deployment可以管理pod的多个副本，并确保pod按照期望的状态运行。
```
 

7.replicaset
```
实现了pod的多副本管理。使用deployment时会自动创建replicaset，也就是说deployment是通过replicaset来管理pod的多个副本的，我们通常不需要直接使用replicaset。
```
 

8.daemonset
```
用于每个node最多只运行一个pod副本的场景。正如其名称所示的，daemonset通常用于运行daemon。
```
 

9.statefuleset
```
能够保证pod的每个副本在整个生命周期中名称是不变的，而其他controller不提供这个功能。当某个pod发生故障需要删除并重新启动时，pod的名称会发生变化，同时statefulset会保证副本按照固定的顺序启动、更新或者删除。、
```
 

10.job
```
用于运行结束就删除的应用，而其他controller中的pod通常是长期持续运行的。
```
 

11.service
```
deployment可以部署多个副本，每个pod 都有自己的IP，外界如何访问这些副本那？

答案是service


k8s的 service定义了外界访问一组特定pod的方式。service有自己的IP和端口，service为pod提供了负载均衡。

k8s运行容器pod与访问容器这两项任务分别由controller和service执行。
```