#操作流程
开源地址： https://github.com/fabric8io/kubernetes-client
官网： https://fabric8.io/

##使用过程
### 引入依赖
```pom
              
<!--k8s-fabric8-java-->
<dependency>
    <groupId>io.fabric8</groupId>
    <artifactId>kubernetes-client</artifactId>
    <version>5.7.0</version>
</dependency>
<dependency>
    <groupId>io.fabric8</groupId>
    <artifactId>kubernetes-api</artifactId>
    <version>3.0.12</version>
</dependency>
```
###1、初始化client
```java
/**
* 并发思考： 考虑到该方法要用封装对象，放进spring容器管理，故不考虑并发。@Service
*/
public KubernetesClient initClient() throws Exception{
    String url = "https://192.168.88.241:6443";
    String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkthWnpkNFVnX1Z5SklCLW9ERVc0ZUV6bzFIc29Ldm81YXZkQ1I2Wk5zUjQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJzYWZlZG9nIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InNhZmVkb2ctdG9rZW4tMncybmciLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoic2FmZWRvZyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjQ2ODYzYTNkLTA4NzYtNGFiNy05ODMyLTk2MTM1NWRjZjRkOCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpzYWZlZG9nOnNhZmVkb2cifQ.NranBEIdREFRGErOWYPejJq5DBtlQxxvP2_PyrtBtjhcX-JsdfAT7w4d5931HivbUM-MpDjc-3WrvRImCCGWsPR8bJQ68A-bDbQFPwJP-4gCOhs9b9V-qmvGTSx_hYx2Yzp9Sc9L0CK6ld05TR22jujqyc6opGxwCONqxoYW5w3T1lJEvke53QNMz-Tq64z-ZtfzZ3ttJe7I7QTCi3axlieSL7GlMVx3rsgrgLJArfolyiPWnbt3OAvGT0zVog1RM6-alSWp5spkk6rTfx0A7TgUjzN5O8WBpcDdwgHWWN1HZiX15KJhO_60sR74Ibjo7QhHlKylcFwmfbALmw0MOA";
    
    Config config = new ConfigBuilder()
            .withMasterUrl(url)
            .withOauthToken(token)
            .withTrustCerts(true)
            .build();
    DefaultKubernetesClient client = new DefaultKubernetesClient(config);
}

```
* config文件方式
```java
String adminConfData = IOUtils.toString(k8sProperties.getAdminConf().getInputStream());
Config config = Config.fromKubeconfig(adminConfData);
DefaultKubernetesClient client = new DefaultKubernetesClient(config);
```

* 还有证书生成规则
```java
Config config = new ConfigBuilder().withMasterUrl(k8sUrl)
                        .withTrustCerts(true)
                        .withCaCertData(caCrtData)
                        .withClientCertData(clientCrtData)
                        .withClientKeyData(clientKeyData)
                        .withNameSpace(null)
                        .builder();
//具体看自己的生成方式
```


###2、资源获取
// 通过指定的namespace和podName直接获取
```
client.pods().inNamespace(nsName).withName(podName).get()
这样的方式意义不大，因为pod重启后podName字符串会改变，pod的name虽然会改变，但是deployment的name不会改变
//通过 
deploymentName、namespace 获取matchLabels 
Map<String, String> matchLabels = client.apps()
        .deployments()
        .inNamespace(nsName)
        .withName(deploymentName)
        .get()
        .getSpec()
        .getSelector()
        .getMatchLabels();
 // 获取PodList
List<Pod> items = client.pods().inNamespace(nsName).withLabels(matchLabels).list().getItems();
```

```java
//查看Pod
MixedOperation> operation = client.pods();

//创建Pod，获取资源处理类，在传入组装号的Pod类
NonNamespaceOperation pods =client.pods().inNamespace("default");
//配置Pod，还可以通过 pod 类组装，想要运行 这里的参数是不够的，仅作演示
Pod pod1 =new PodBuilder().withNewMetadata().withName("pod1").withNamespace("default").and().build();
pods.create(pod1);
//删除同上
pods.delete(pod1);

```

#高级使用
##监听事件
我们还可以通过监听资源的事件，来进行对应的反应，比如有人删除了Pod就记录日志到数据库等，这个功能还是非常有用的。示例代码如下：
```java
client.pods().inAnyNamespace().watch(new Watcher<Pod>() {
  @Override
  public void eventReceived(Action action, Pod pod) {
    System.out.println("event " + action.name() + " " + pod.toString());
  }
/** 日志打印
  event ADDED Pod(apiVersion=v1, kind=Pod, metadata=ObjectMeta(
  event MODIFIED Pod(apiVersion=v1, kind=Pod, metadata=ObjectMeta(
  event DELETED Pod(apiVersion=v1, kind=Pod, metadata=ObjectMeta(
  event MODIFIED Pod(apiVersion=v1, kind=Pod, metadata=ObjectMeta(
*/

  @Override
  public void onClose(WatcherException e) {
    System.out.println("Watcher close due to " + e);

  }
});
```


## client可以通过deployment
Deployment通过控制ReplicaSet对象的pod数量来达到滚动升级的效果
一般情况下，我们并不直接创建 Pod，而是通过 Deployment 来创建 Pod，由 Deployment 来负责创建、更新、维护其所管理的所有 Pods。
### Deployment的示例
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
```

```java
public class K8sDeploymentConf {
    public static Deployment getDepandDeployment(String appName,String image,String nodeName){
        //参数传递
        String appGroup = "appGroup";
        //参数
        Map<String,String> labels = new HashMap<String,String>();
        labels.put("app",appGroup);
        Map<String,String> nodeSelector = new HashMap<String,String>();
        nodeSelector.put("name",nodeName);
        //mataData 数据组装
        ObjectMeta mataData = new ObjectMeta();
        mataData.setName(appName);
        mataData.setLabels(labels);
        //镜像设置
        Container container = new Container();
        container.setName(appName);
        container.setImage(image);
        container.setImagePullPolicy("IfNotPresent");
        SecurityContext sc = new SecurityContext();
        sc.setPrivileged(true);
        container.setSecurityContext(sc);
        List<Container> containers = new ArrayList<>();
        containers.add(container);
        //Spec 数据组装
        //1.selector
        LabelSelector ls =new LabelSelector();
        ls.setMatchLabels(labels);
        //2.template
        ObjectMeta empMataData = new ObjectMeta();
        empMataData.setLabels(labels);
        PodSpec pods = new PodSpec();
        pods.setHostNetwork(true);
        pods.setNodeSelector(nodeSelector);
        pods.setContainers(containers);
        //2.2 组装
        PodTemplateSpec pt = new PodTemplateSpec();
        pt.setMetadata(empMataData);
        pt.setSpec(pods);
        //3.spec 组合
        DeploymentSpec ds = new DeploymentSpec();
        ds.setReplicas(1);
        ds.setSelector(ls);
        ds.setTemplate(pt);
        //Deployment 设置
        Deployment deployment =new Deployment();
        deployment.setApiVersion("apps/v1");
        deployment.setKind("Deployment");
        deployment.setMetadata(mataData);
        deployment.setSpec(ds);
        return deployment;
    }

}
```


Deployment操作：
方式一
```java
//将基础Client转换为AppsAPIGroupClient，用于操作deployment
AppsAPIGroupClient oclient =client.adapt(AppsAPIGroupClient.class);
MixedOperation<Deployment, DeploymentList, DoneableDeployment, RollableScalableResource<Deployment, DoneableDeployment>> operation1
                =oclient.deployments();
//将资源转换为JSON 查看
DeploymentList deploymentList =operation1.list();
List<Deployment> deployments = deploymentList.getItems();
JSONArray jsonArray=JSON.parseArray(JSON.toJSONString(deployments));
//创建Deployment，返回创建好的Deployment文件
oclient.deployments().create(K8sDeploymentConf.getDepandDeployment("appName","image","nodeName"));
//删除同上，返回结果为boolean类型数据
oclient.deployments().delete(K8sDeploymentConf.getDepandDeployment("appName","image","nodeName"));
```
方式二
```java
Deployment deployment = client.apps().deployments()
  .load(Fabric8KubernetesClientSamples.class.getResourceAsStream("/deployment.yaml"))
  .get();
client.apps().deployments().inNamespace("pkslow")
  .createOrReplace(deployment);
```