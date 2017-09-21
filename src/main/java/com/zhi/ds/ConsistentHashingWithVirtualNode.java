package com.zhi.ds;

import java.util.SortedMap;
import java.util.TreeMap;

import com.zhi.ds.util.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhimeng
 *         email: zhimeng@douyu.tv
 *         weichat: mengzhi825
 *         date: 2017/9/21.
 *         <p>
 *         TODO 感知服务节点宕机和恢复
 *         思路1，客户端通过注册中心感知服务节点。
 *         思路2，客户端根据一定的策略决定服务节点是否不可用
 *         <p>
 *         reference:
 *         对一致性Hash算法，Java代码实现的深入研究
 *         http://www.cnblogs.com/xrq730/p/5186728.html
 */
public class ConsistentHashingWithVirtualNode {

    private static final Logger logger = LoggerFactory.getLogger(ConsistentHashingWithVirtualNode.class);

    private int virtualNodesPerServer;
    private SortedMap<Integer, String> hashRing = new TreeMap<Integer, String>();

    public ConsistentHashingWithVirtualNode(String... servers) {
        this(5, servers);
    }

    public ConsistentHashingWithVirtualNode(int virtualNodesPerServer, String... servers) {
        if (virtualNodesPerServer <= 0) {
            throw new IllegalArgumentException("virtual nodes per server can't less then 0.");
        }
        for (String server : servers) {
            for (int i = 0; i < virtualNodesPerServer; i++) {
                String virtualNodeName = server + "&&VN" + String.valueOf(i);
                int serverHash = HashUtils.hash(virtualNodeName);
                logger.info("virtual node {} enter hash ring, and it's hash is {}.", virtualNodeName, serverHash);
                hashRing.put(serverHash, virtualNodeName);
            }
        }
    }

    public String getServer(String node) {
        int hash = HashUtils.hash(node);
        SortedMap<Integer, String> subServers = hashRing.tailMap(hash);
        Integer got = subServers.firstKey();
        String virtualNode = subServers.get(got);
        return virtualNode.substring(0, virtualNode.indexOf("&&"));
    }
}
