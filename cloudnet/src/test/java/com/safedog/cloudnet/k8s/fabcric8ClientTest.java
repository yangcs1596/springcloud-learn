package com.safedog.cloudnet.k8s;


import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonNamespaceOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

@SpringBootTest
@Slf4j
//@RunWith(SpringRunner.class)
public class fabcric8ClientTest {

    /**
     * Pod pod = new PodBuilder()
     *   .withNewMetadata()
     *   .withName("nginx")
     *   .addToLabels("app", "nginx")
     *   .endMetadata()
     *   .withNewSpec()
     *   .addNewContainer()
     *   .withName("nginx")
     *   .withImage("nginx:1.19.5")
     *   .endContainer()
     *   .endSpec()
     *   .build();
     *
     */
    @Test
    public void test_fabcri(){
        String url = "https://192.168.88.241:6443";
        String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkthWnpkNFVnX1Z5SklCLW9ERVc0ZUV6bzFIc29Ldm81YXZkQ1I2Wk5zUjQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJzYWZlZG9nIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6InNhZmVkb2ctdG9rZW4tMncybmciLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoic2FmZWRvZyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjQ2ODYzYTNkLTA4NzYtNGFiNy05ODMyLTk2MTM1NWRjZjRkOCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpzYWZlZG9nOnNhZmVkb2cifQ.NranBEIdREFRGErOWYPejJq5DBtlQxxvP2_PyrtBtjhcX-JsdfAT7w4d5931HivbUM-MpDjc-3WrvRImCCGWsPR8bJQ68A-bDbQFPwJP-4gCOhs9b9V-qmvGTSx_hYx2Yzp9Sc9L0CK6ld05TR22jujqyc6opGxwCONqxoYW5w3T1lJEvke53QNMz-Tq64z-ZtfzZ3ttJe7I7QTCi3axlieSL7GlMVx3rsgrgLJArfolyiPWnbt3OAvGT0zVog1RM6-alSWp5spkk6rTfx0A7TgUjzN5O8WBpcDdwgHWWN1HZiX15KJhO_60sR74Ibjo7QhHlKylcFwmfbALmw0MOA";

        Config config = new ConfigBuilder()
                .withMasterUrl(url)
                .withOauthToken(token)
                .withTrustCerts(true)
                .build();
//        Config config1 = Config.fromKubeconfig();
        DefaultKubernetesClient client = new DefaultKubernetesClient(config);

        System.out.println("==============查看资源namespace===============");
//        NonNamespaceOperation<Namespace, NamespaceList, Resource<Namespace>> namespaces = client.namespaces();
//        NamespaceList list = namespaces.list();
//        System.out.println(JSONObject.toJSONString(list));

        System.out.println("==============修改node===============");
        // 修改Pod
//        client.pods()
//                .inNamespace("pkslow").withName("nginx")
//                .edit(p -> new PodBuilder(p)
//                        .editMetadata()
//                        .addToLabels("app-version", "1.0.1")
//                        .endMetadata()
//                        .build()
//                );
//        Node node = client.nodes().withName("k8s-minion-03")
//                .edit(p -> new NodeBuilder(p)
//                        .editSpec()
//                        .withUnschedulable(false)
//                        .endSpec()
//                        .build()
//                );
        Node node = client.nodes().withName("k8s-minion-03").get();
        System.out.println(node);


    }


}
