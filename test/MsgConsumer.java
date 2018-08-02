package com.util.thread.producer_consumer.test;

import com.util.thread.producer_consumer.AbstractConsumer;
import com.util.thread.producer_consumer.IProductMgr;

import java.util.concurrent.ThreadLocalRandom;

public class MsgConsumer extends AbstractConsumer {

    public MsgConsumer(IProductMgr iProductMgr, Object objLock) {
        super(iProductMgr, objLock);
    }

    @Override
    public Object consumeProduct(Object objProduct) {
        System.out.println("  MsgConsumer  consumeProduct " + objProduct + " begin");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("  MsgConsumer  consumeProduct " + objProduct + " end");
        return null;
    }
}
