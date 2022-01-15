package com.safedog.common.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author ycs
 * @description
 * @date 2021/12/9 14:53
 */
@RequiredArgsConstructor
public class TransactionalEventListenerTest {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void finishOrder(){
        //....一系列事物操作
        // 发布 Spring Event 事件
        applicationEventPublisher.publishEvent(new MyAfterTransactionEvent("object"));
    }

    @Slf4j
    @Component
    private static class MyTransactionListener {

        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
        private void onHelloEvent(MyAfterTransactionEvent event) {
            //...事物提交之后的操作
        }
    }

    // 定一个事件，继承自ApplicationEvent
    private static class MyAfterTransactionEvent extends ApplicationEvent {
        public MyAfterTransactionEvent(Object source) {
            super(source);
        }
    }

/**
    //无Event的例子Demo
    @Service
    public class UserEventRegister {
        @Autowired
        private ApplicationEventPublisher publisher;

        public void register() throws Exception {
            UserDto user=new UserDto();
            user.setName("电脑");
            user.setSex("未知");
            publisher.publishEvent(user);
        }
    }

    @Component
    public class UserEventListener {
        @EventListener(condition = "#user.name!=null")
        public void handleEvent(UserDto user) throws Exception{
            System.out.println(user.getName());
            System.out.println(user.getSex());
        }
    }
**/
}
