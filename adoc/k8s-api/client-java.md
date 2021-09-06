#
##依赖

```xml
<dependency>
    <groupId>io.kubernetes</groupId>
    <artifactId>client-java</artifactId>
    <version>11.0.00</version>
    <scope>compile</scope>
</dependency>
```

## 获取client
考虑并发情况
```java
//方式一
String url = "https://192.168.88.241:6443";
String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkthWnpkNFVnX1Z5SklCLW9ERVc0ZUV6bzFIc29Ldm81YXZkQ1I2Wk5zUjQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJzYWZlZG9nIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InNhZmVkb2ctdG9rZW4tMncybmciLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoic2FmZWRvZyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjQ2ODYzYTNkLTA4NzYtNGFiNy05ODMyLTk2MTM1NWRjZjRkOCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpzYWZlZG9nOnNhZmVkb2cifQ.NranBEIdREFRGErOWYPejJq5DBtlQxxvP2_PyrtBtjhcX-JsdfAT7w4d5931HivbUM-MpDjc-3WrvRImCCGWsPR8bJQ68A-bDbQFPwJP-4gCOhs9b9V-qmvGTSx_hYx2Yzp9Sc9L0CK6ld05TR22jujqyc6opGxwCONqxoYW5w3T1lJEvke53QNMz-Tq64z-ZtfzZ3ttJe7I7QTCi3axlieSL7GlMVx3rsgrgLJArfolyiPWnbt3OAvGT0zVog1RM6-alSWp5spkk6rTfx0A7TgUjzN5O8WBpcDdwgHWWN1HZiX15KJhO_60sR74Ibjo7QhHlKylcFwmfbALmw0MOA";
ApiClient apiClient =  new ClientBuilder()
        .setBasePath(url)
        .setVerifyingSsl(false)
        .setAuthentication(new AccessTokenAuthentication(token))
        .build();
        
//方式二
ApiClient client = Config.fromToken(url, token);
//方式三
ApiClient apiClient = new ClientBuilder()
.kubeconfig(KubeConfig.loadKubeConfig(new FileReader("C:\\Users\\ASUS\\Desktop\\config"))).build();
//重要一步
Configuration.setDefaultApiClient(apiClient);
```

##其它使用待发现
