1. datagramClient.java : udp socket channel 客户端。
2. datagramServer.java ：udp socket channel 服务端。

过程：

1.  服务端 调用channel=DatagramChannel.open()，创建通道，关联创建datagramsocket。
2.  服务端 调用channel.bind（new IntSocketAddress（9000）），将服务关联的datagramesocket绑定到端口9000上。
3.  客户端 调用channel=DatagramChannel.open();创建通道，关联创建datagramsocket。
4.  客户端 调用channel.send(bb,host); 向服务器发送报文。
5.  服务端 SocketAddress socketAddress= channel.receive(bb); 接收来自客户端的请求，并返回socket地址。



使用了selector进行选择。

