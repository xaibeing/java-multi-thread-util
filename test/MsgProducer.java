package com.util.thread.producer_consumer.test;

import com.util.thread.producer_consumer.AbstractProducer;
import com.util.thread.producer_consumer.IProductMgr;

import java.util.concurrent.ThreadLocalRandom;

public class MsgProducer extends AbstractProducer {

    private int nCount = 0;

    public MsgProducer(IProductMgr iProductMgr, Object objLock) {
        super(iProductMgr, objLock);
    }

    @Override
    public Object createProduct() {
        System.out.println("    MsgProducer  createProduct begin");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        nCount++;
        String strProduct = "msg" + nCount;
        System.out.println("    MsgProducer  createProduct end：" + strProduct);
        return strProduct;
    }
}
