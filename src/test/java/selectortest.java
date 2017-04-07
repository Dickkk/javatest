import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by chaoice3240 on 2017/3/22.
 */
public class selectortest {
    public  static  void main(String[] args)
    {
        //创建一个套接字服务器，并注册到选择器
//创建选择器
        Selector selector = null;
        try {
            selector = Selector.open();
            //创建Socket服务器通道
            ServerSocketChannel ssc = ServerSocketChannel.open();
//绑定65535端口
            ssc.socket().bind(new InetSocketAddress(65535));
//设置通道为非阻塞模式
            ssc.configureBlocking(false);
//将通道注册到选择器，指定通道兴趣是等待接收连接
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {

        }
    }
}
