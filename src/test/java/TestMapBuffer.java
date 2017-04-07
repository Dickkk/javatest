import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by chaoice3240 on 2017/3/25.
 */
public class TestMapBuffer {
    //测试mappedbuffer的读功能，以及gatherbufferchanl输出到文件。
    public static void main1(String [] args)
    {
        FileChannel fc=null;
        FileChannel fc_w=null;
        try {
            RandomAccessFile raf=  new RandomAccessFile("1.txt","r");
             fc=raf.getChannel();
            MappedByteBuffer mbb=fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size());
            RandomAccessFile raf_w=new RandomAccessFile("2.txt","rw");
            fc_w=raf_w.getChannel();
            ByteBuffer[] bufs=new ByteBuffer[2];
            bufs[0]=ByteBuffer.wrap("helloworld\n".getBytes());
            bufs[1]=mbb;
            fc_w.write(bufs);
            fc.close();
            fc_w.close();
        } catch (java.io.IOException e) {

        }
        finally {
            if(fc!=null&&fc.isOpen()) {
                try {
                    fc.close();
                } catch (IOException e) {

                }
            }
        }


    }
    public static void printmap()
    {
        FileChannel fc1=null;
        FileChannel fc2=null;
        try {
            RandomAccessFile raf = new RandomAccessFile("1.txt", "r");
            fc1=raf.getChannel();
            MappedByteBuffer mbb=fc1.map(FileChannel.MapMode.PRIVATE,0,fc1.size());
            while(true)
            {
                mbb.rewind();
                byte [] bytes=new byte[((int) fc1.size())];
                mbb.get(bytes);
                String str=new String(bytes, Charset.defaultCharset()).trim();
                System.out.println(str.trim());
                mbb.position(str.length());
                mbb.putInt(str.length());
                Thread.sleep(1000);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            try {
                if(fc1!=null&&fc1.isOpen()) {
                    fc1.close();
                }
                if(fc2!=null&&fc2.isOpen()) {
                    fc2.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void modifymap()
    {
        FileChannel fc1=null;
        try {
            RandomAccessFile raf = new RandomAccessFile("1.txt", "rw");
            fc1=raf.getChannel();
            MappedByteBuffer mbb=fc1.map(FileChannel.MapMode.PRIVATE,0,fc1.size());
            while(true)
            {
                mbb.rewind();
                byte [] bytes=new byte[((int) fc1.size())];
                mbb.get(bytes);
                String str=new String(bytes, Charset.defaultCharset());
                System.out.println(str.trim());
                Thread.sleep(1000);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            try {
                if(fc1!=null&&fc1.isOpen()) {

                    fc1.close();

                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args)
    {


    }

}
