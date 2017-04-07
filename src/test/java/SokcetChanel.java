import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * Created by chaoice3240 on 2017/3/21.
 */
public class SokcetChanel {
    @Test
    public void chanl()
    {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("1.txt", "rw");
        } catch (FileNotFoundException e) {

        }
        FileChannel fc = raf.getChannel();
// 打开一个服务器套接字通道
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
        } catch (IOException e) {

        }
// 打开一个套接字通道，
        try {
            SocketChannel sc = SocketChannel.open();
        } catch (IOException e) {

        }
// 打开一个数据报通道
        try {
            DatagramChannel dc = DatagramChannel.open();
        } catch (IOException e) {

        }
    }
    @Test
    public void testchnels()
    {
        ReadableByteChannel readc=  Channels.newChannel(System.in);
        WritableByteChannel writec=Channels.newChannel(System.out);
        ByteBuffer bb=ByteBuffer.allocate(1000);
        try {
            while(readc.read(bb)!=-1)
            {
                bb.rewind();
               String str= new String(bb.array()).trim();
                if(str.equals("quit"))
                {
                    readc.close();
                    writec.close();
                }
                writec.write(bb);
                bb.clear();
            }
        } catch (IOException e) {

        }

    }

    @Test
    public void test()
    {
        ReadableByteChannel ch=Channels.newChannel(System.in);
        ByteBuffer buf=ByteBuffer.allocate(10);

        try {
            if(ch.isOpen()) {
                int rdSz = ch.read(buf);
            }
        } catch (IOException e) {

        }
    }
}
