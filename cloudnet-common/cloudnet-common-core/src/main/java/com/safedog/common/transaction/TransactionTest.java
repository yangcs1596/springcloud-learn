package com.safedog.common.transaction;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author ycs
 * @description
 * @date 2021/12/9 14:42
 */
public class TransactionTest {

    /**
     * TransactionSynchronizationManager是事务同步管理器。
     * 一般而言，我们在TransactionSynchronization使用最多的是afterCommit和afterCompletion方法。
     * 可以在事务执行完毕之后，直接调用afterCommit()方法进行异步通知。
     *
     */
    public void TransactionSynchronizationManagerTest(){
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    //...
                }
            });
        }
        else {
            //...
        }
    }

    /**
     * 在 Spring framework 4.2 之后还可以使用@TransactionalEventListener处理数据库事务提交成功后再执行操作。这种方式比 TransactionSynchronization 更加优雅。
     */
}
