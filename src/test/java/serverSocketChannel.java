import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by chaoice3240 on 2017/3/22.
 */
public class serverSocketChannel {

    public static void main(String[] args)
    {
        try {
            ServerSocketChannel ssc= ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(9000));
            ssc.configureBlocking(false);
            ByteBuffer bb=ByteBuffer.wrap("hello world".getBytes());
            Selector selector= Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int n= selector.select();
                if(n==0)
                {
                    continue;
                }
                Iterator iterator= selector.selectedKeys().iterator();
                while(iterator.hasNext())
                {
                   SelectionKey key=  (SelectionKey)iterator.next();
                    if(key.isAcceptable())
                    {
                        ServerSocketChannel channel=(ServerSocketChannel)key.channel();
                        SocketChannel socketChanneltmp=channel.accept();
                        socketChanneltmp.configureBlocking(false);
                        socketChanneltmp.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable())
                    {
                        SocketChannel readChannel=(SocketChannel)key.channel();
                        bb.clear();
                        while(bb.remaining()>0) {
                            readChannel.read(bb);
                        }
                        bb.flip();
                        System.out.println(new String(bb.array())+readChannel.socket().getRemoteSocketAddress());
                    }
                    iterator.remove();
                }
//                SocketChannel sc = ssc.accept();
//                if(sc!=null)
//                {
//                    System.out.println("connect from "+sc.getRemoteAddress());
//                    bb.rewind();
//                    sc.write(bb);
//                    sc.close();
//                }
//                else
//                {
//                    System.out.println("wait for connection");
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//
//                    }
//                }
            }



        } catch (IOException e) {

        }

    }



}
