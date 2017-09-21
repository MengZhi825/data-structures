package com.zhi.ds;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhimeng
 *         email: zhimeng@douyu.tv
 *         weichat: mengzhi825
 *         date: 2017/9/21.
 */
public class ConsistentHashingTest {

    private static final Logger logger = LoggerFactory.getLogger(ConsistentHashingTest.class);

    /**
     * 待测试的服务节点
     */
    private String[] servers = {
            "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
            "192.168.0.3:111", "192.168.0.4:111"
    };

    /**
     * 待被路由到服务节点的节点
     */
    private String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};

    @Test
    public void testWithoutVirtualNode() {
        ConsistentHashingWithoutVirtualNode chVN = new ConsistentHashingWithoutVirtualNode(servers);
        for (String node : nodes) {
            //just print result.
            logger.info(chVN.getServer(node));
        }
    }

    @Test
    public void testWithVirtualNode() {
        ConsistentHashingWithVirtualNode chVN = new ConsistentHashingWithVirtualNode(servers);
        for (String node : nodes) {
            //just print result.
            logger.info(chVN.getServer(node));
        }
    }


}
