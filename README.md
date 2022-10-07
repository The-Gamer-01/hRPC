# hRPC
一个简单的RPC框架项目，其主要目的是为了帮助初学者能够了解RPC框架的基本原理。

# 计划安排

- [x] 支持Netty
- [x] 支持SPI机制加载
- [x] 自定义以bit为单位的通信协议
- [x] 支持异步 
- [x] 支持接口幂等性
- [ ] 支持性能测试
- [ ] 支持跨语言
- [x] 支持通信报文压缩
- [x] 支持多种序列化（暂时只支持Kryo）
最后两个特性在本地已经实现,暂未commit,需要测试性能完成后再进行commit.

## 可优化点
根据Dubbo的文档以及与大佬们的讨论,可得出以下可优化点:
- 通信协议需要抽象为SPI,便于更换
- 通信协议方面需要考虑多语言支持与Service Mesh
- Zookeeper是CP强一致性的,然而服务治理领域更加需要AP(最终一致性)
- 重新设计路由模型与Router模型
- 结合火焰图与JMH测试方法性能
