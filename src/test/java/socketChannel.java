import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by chaoice3240 on 2017/3/22.
 */
public class socketChannel {
    public static  void main(String [] args)
    {
        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress(9000));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                ByteBuffer bb = ByteBuffer.wrap("hello world a".getBytes());
                while (!sc.finishConnect()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }

                while(bb.remaining()>0) {
                    sc.write(bb);
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {

            }
        }




    }
}
