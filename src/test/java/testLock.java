import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.SQLException;

/**
 * Created by chaoice3240 on 2017/3/24.
 */
public class testLock {
    public static void process()  {

        try {
                int a=10/0;

        } catch ( Exception ex) {
            throw new java.lang.ArithmeticException("paochuyichang");
        }
        System.out.println("continue");

    }
    //测试异常
    public static void comp()
    {
        process();
        System.out.println("continue");
    }
    //文件锁
    public  static  void main(String[] args)
    {
        try {
            comp();
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        System.out.println("hello");
        ByteBuffer bb=ByteBuffer.allocate(10);
        try {
            RandomAccessFile raf=new RandomAccessFile("1.txt",args[0].equals("w")?"rw":"r");
            FileChannel fc=raf.getChannel();
            FileLock fl=null;
            if(!args[0].equals("w"))
            {
                while(true) {
                    try {
                        fl = fc.lock(0,10,true);
                        bb.clear();
                        fc.position(0);
                        fc.read(bb, 0);
                        bb.flip();
                        System.out.println(new String(bb.array()));

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    finally {
                        try {
                            fl.release();
                        } catch (IOException e) {

                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
            else
            {
                while(true) {
                    try {
                        fl = fc.lock(0,10,false);
                        bb.clear();
                        fc.position(0);
                        fc.read(bb, 0);
                        bb.flip();
                        ByteBuffer bk=ByteBuffer.allocate(10);
                        bk.clear();
                        for(int i=1;i<10;i++)
                        {
                            bk.put(bb.get(i));
                        }
                        bk.put(bb.get(0));
                        bk.flip();
                        fc.write(bk);

                        System.out.println(new String(bb.array()));

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        try {
                            fl.release();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
