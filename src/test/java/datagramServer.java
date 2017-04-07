import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Created by chaoice3240 on 2017/3/31.
 */
public class datagramServer {
    private static DatagramChannel channel;
    public static void main(String[] args)
    {
        try {
            channel=DatagramChannel.open();
            channel.bind(new InetSocketAddress("localhost",9000));
            channel.configureBlocking(false);

            DatagramChannel channel2=DatagramChannel.open();
            channel2.bind(new InetSocketAddress("localhost",9001));
            channel2.configureBlocking(false);

            Selector selector= Selector.open();

            channel2.register(selector, SelectionKey.OP_READ);
            channel.register(selector, SelectionKey.OP_READ);
            while(true)
            {
                int n=selector.select();
                if(n==0)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;

                }
                for(SelectionKey key: selector.selectedKeys())
                {
                    if(key.isReadable())
                    {
                        DatagramChannel channeltmp=(DatagramChannel)key.channel();
                        ByteBuffer bb=ByteBuffer.allocate(100);
                        bb.clear();
                        SocketAddress clientadd=channeltmp.receive(bb);
                        bb.flip();
                        System.out.println("recive from " +clientadd+new String(bb.array()));

                    }
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
        }

    }
}
