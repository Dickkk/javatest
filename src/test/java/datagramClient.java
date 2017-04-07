import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by chaoice3240 on 2017/3/31.
 */
public class datagramClient {
    static  DatagramChannel channel;
            public static  void main(String[] args)
            {
                try {
                    channel=DatagramChannel.open();
                    ByteBuffer bb=ByteBuffer.wrap("hello".getBytes());
                    InetSocketAddress host=new InetSocketAddress("localhost",9001);

                    channel.send(bb,host);
                    while(true)
                    {
                         ByteBuffer BF=ByteBuffer.allocate(10);
                        SocketAddress socketAddress= channel.receive(BF);

                        BF.flip();
                        System.out.print("recive"+ new String(BF.array()));
                        if(socketAddress==null)
                        {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {

                            }
                            continue;
                        }

                    }
                } catch (IOException e) {
e.printStackTrace();
                }
            }
}
