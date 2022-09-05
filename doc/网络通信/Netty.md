# Netty如何实现高性能
- IO线程模型: 同步非阻塞,用最少的资源做更多的事情
- 内存零拷贝: 尽量减少不必要的内存拷贝,实现更高效率的传输
- 内存池设计: 申请的内存可以重用, 底层使用二叉查找树管理内存分配情况
- 串行化处理读写: 避免使用锁带来性能开销

# Netty的ByteBuf优化
1. ByteBuf支持动态扩容和缩容,ByteBuffer不支持;
2. ByteBuf使用readIndex和writeIndex进行读写操作,NIO中ByteBuffer读写需要翻转等操作进行读写切换;
3. ByteBuf支持池化技术
