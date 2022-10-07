# Zookeeper实现注册中心的原理
Zookeeper可以充当一个**服务注册表**,让多个服务提供者形成一个集群,使得服务消费者通过服务注册表获取具体的服务访问地址去访问具体服务提供者;
服务提供者与注册中心之间通过心跳进行连接;
例子如下:
![image](https://user-images.githubusercontent.com/58755323/188494602-c9d0bcd8-bbe5-4a67-bb4e-c6b4d50a7d01.png)

Zookeeper中使用服务注册其实就是在Zookeeper中创建一个Znode节点,存储该服务的IP、端口、调用方式(协议、序列话方式)等.
由服务提供者发布服务时创建,用以给服务消费者获取节点中信息,定位到服务提供者真正网络拓扑位置以及根据路由了解调用方式;

# Zookeeper使用场景
1. 可靠存储: 配置管理、名字服务
2. 集群管理: 利用ZK的通讯与回调机制完成分布式集群机器状态监视,
3. 服务注册发现管理
4. 选主服务
5. 分布式锁
6. 负载均衡

# Zookeeper羊群效应
问题描述:
羊群效应经常出现在Zookeeper实现分布式锁场景;
客户端创建节点,序号最小的获取锁;其他客户端监控最小节点,最小节点完成任务,发出通知并释放;其他客户端获取通知后,获取所有节点,序号最小的获取锁,依次类推;
问题原因:
需要通知的客户端很多,导致Zookeeper性能急剧下降,影响Zookeeper使用
解决方案:
客户端创建节点,序号最小的获取锁;
客户端只监控比自己小的节点;
最小节点完成任务发出通知并释放;
客户端获取通知获取所有节点,若自己序号最小则获取锁,若不是则监视比自己小的节点;
总结:
使用Zookeeper需要尽量避免大量节点监控一个节点的行为;

# 心跳的实现
//TODO 待完善

# Zookeeper优缺点
优点:
1. 通讯机制与状态的实现: 基于jute进行编解码处理保证通用性, 服务端使用NIO或Netty
2. 分布式一致性协议使用ZAB协议,并且在其选主阶段使用"fast leader election"算法实现

缺点:
1. Zookeeper对于网络隔离极端敏感,导致Zookeeper的不可用时间较多: 由于网络中偶尔出现的半秒一秒的网络隔离使得Zookeeper同意会进行选举机制,但是Zookeeper的选举流程通常耗时30~120秒,期间Zookeeper没有master,导致整个系统不可用,其在服务发现场景下不合适;
2. Zookeeper性能有限: Zookeeper的TPS大概为1w多,无法覆盖系统内几十亿次调用,需要Client进行本地缓存;
3. Zookeeper没有强有力的权限控制: 大型的复杂系统中,使用Zookeeper需要自己额外开发一套权限控制系统,通过权限控制系统再访问Zookeeper;

# 参考资料来源
[Zookeeper系统设计的优点](https://zhuanlan.zhihu.com/p/37746516)
[微服务注册中心技术选型：5种主流注册中心，哪个最香？](https://blog.csdn.net/m0_69305074/article/details/124695721)
[zookeeper羊群效应](https://blog.csdn.net/wk022/article/details/88129479?utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-1.control)
