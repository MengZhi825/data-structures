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
 *         <p>
 *         TODO 感知服务节点宕机和恢复
 *         思路1，客户端通过注册中心感知服务节点。
 *         思路2，客户端根据一定的策略决定服务节点是否不可用
 *         <p>
 *         reference:
 *         对一致性Hash算法，Java代码实现的深入研究
 *         http://www.cnblogs.com/xrq730/p/5186728.html
 */
public class ConsistentHashingWithoutVirtualNode {

    private static final Logger logger = LoggerFactory.getLogger(ConsistentHashingWithoutVirtualNode.class);

    //TODO need to ensure thread-safe
    private SortedMap<Integer, String> hashRing = new TreeMap<Integer, String>();

    public ConsistentHashingWithoutVirtualNode(String... servers) {
        for (String server : servers) {
            int hash = HashUtils.hash(server);
            hashRing.put(hash, server);
            logger.info("{} enter hash ring, and it's hash is {}.", server, hash);
        }
    }

    /**
     * 得到node被路由到的server节点。
     *
     * @param node
     * @return
     */
    public String getServer(String node) {
        int nodeHash = HashUtils.hash(node);
        SortedMap<Integer, String> subServers = hashRing.tailMap(nodeHash);
        //第一个Key就是顺时针过去离node最近的那个结点
        Integer got = subServers.firstKey();
        //返回服务器名称
        return hashRing.get(got);
    }
}
