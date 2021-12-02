package com.safedog.cloudnet.k8s;

import com.alibaba.fastjson.JSONObject;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1beta1Eviction;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.credentials.AccessTokenAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
//@RunWith(SpringRunner.class)
public class k8sApiTest {


    /**
     * init
     */
    private ApiClient initApiClient() {
        String url = "https://192.168.88.241:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkthWnpkNFVnX1Z5SklCLW9ERVc0ZUV6bzFIc29Ldm81YXZkQ1I2Wk5zUjQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJzYWZlZG9nIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InNhZmVkb2ctdG9rZW4tMncybmciLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoic2FmZWRvZyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjQ2ODYzYTNkLTA4NzYtNGFiNy05ODMyLTk2MTM1NWRjZjRkOCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpzYWZlZG9nOnNhZmVkb2cifQ.NranBEIdREFRGErOWYPejJq5DBtlQxxvP2_PyrtBtjhcX-JsdfAT7w4d5931HivbUM-MpDjc-3WrvRImCCGWsPR8bJQ68A-bDbQFPwJP-4gCOhs9b9V-qmvGTSx_hYx2Yzp9Sc9L0CK6ld05TR22jujqyc6opGxwCONqxoYW5w3T1lJEvke53QNMz-Tq64z-ZtfzZ3ttJe7I7QTCi3axlieSL7GlMVx3rsgrgLJArfolyiPWnbt3OAvGT0zVog1RM6-alSWp5spkk6rTfx0A7TgUjzN5O8WBpcDdwgHWWN1HZiX15KJhO_60sR74Ibjo7QhHlKylcFwmfbALmw0MOA";
        ApiClient apiClient =  new ClientBuilder()
                .setBasePath(url)
                .setVerifyingSsl(false)
                .setAuthentication(new AccessTokenAuthentication(token))
                .build();
        //config的路径/home/safedog/.kube/config
        //方式二
        // ApiClient client = Config.fromToken(url, token);
        //方式三 config
//        ApiClient apiClient = new ClientBuilder().kubeconfig(KubeConfig.loadKubeConfig(new FileReader("C:\\Users\\ASUS\\Desktop\\config"))).build();
        Configuration.setDefaultApiClient(apiClient);
        return apiClient;
    }

    @Test
    public void test_cluster() throws Exception {
        this.initApiClient();
        CoreV1Api coreV1Api = new CoreV1Api();

    }

    @Test
    public void test_namespace() throws Exception {
        this.initApiClient();
        CoreV1Api coreV1Api = new CoreV1Api();
//        V1NamespaceList v1NamespaceList = coreV1Api.listNamespace(null, null, null,
//                null, null, null, null,
//                null, null, null);
//        System.out.println(JSONObject.toJSONString(v1NamespaceList));
//        V1PodList v1PodList = coreV1Api.listPodForAllNamespaces(false, null, null,
//                null, 10, null, null, null,
//                20000, false);
        System.out.println("------------------------------------------");
        V1Node v1Node = coreV1Api.readNode("k8s-minion-03", null, null, null);


//        System.out.println(v1Node);
        System.out.println("------------------------------------------");
        //节点调度 true停止， false恢复调度
        v1Node.getSpec().setUnschedulable(null);
//        V1Node replaceNode = coreV1Api.replaceNode("k8s-minion-03", v1Node, null, null, null);

//        V1Patch v1Patch = new V1Patch("");
//
//        coreV1Api.patchNode("k8s-minion-03", v1Patch, null, null, null, null);
//        coreV1Api.deleteNode()

    }

    @Test
    public void test_pod() throws Exception{
        this.initApiClient();
        CoreV1Api coreV1Api = new CoreV1Api();

        V1beta1Eviction v1beta1Eviction = new V1beta1Eviction();
        v1beta1Eviction.setApiVersion("V1");
        v1beta1Eviction.setKind("pod");
//        v1beta1Eviction.setMetadata();
//        v1beta1Eviction.setDeleteOptions();
//        netcat-vxln4
        V1Pod v1Pod = coreV1Api.readNamespacedPod("netcat-vxln4", null, null, null, null);
        /**
         * 驱逐节点的pod
         */
        V1beta1Eviction podEviction = coreV1Api.createNamespacedPodEviction("netcat-vxln4", "default",
                v1beta1Eviction,
                null, null, null);
        System.out.println(JSONObject.toJSONString(podEviction));

    }
    @Test
    public void test_node() throws Exception{
        this.initApiClient();
        CoreV1Api coreV1Api = new CoreV1Api();

        V1PodList v1PodList = coreV1Api.listPodForAllNamespaces(null,
                null, null, null,
                null, null, null, null, null, null);
        System.out.println(JSONObject.toJSONString(v1PodList));

    }


}
