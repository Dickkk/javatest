import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chaoice3240 on 2017/3/21.
 */

public class test {
    @Test
    public void testByteBuffer()
    {
        ByteBuffer bb=ByteBuffer.allocate(10);
        bb.putChar('a');
        bb.putInt(1);
        bb.rewind();
        System.out.print(bb.getChar());
        System.out.print(bb.getInt());
    }
    @Test
    public void testByteBuffer2()
    {
        ByteBuffer bb=ByteBuffer.wrap("中国强".getBytes());
       bb.rewind();
        System.out.print(Charset.defaultCharset().decode(bb));

    }
    //system.in
    public static void main1(String args[]) {
        ReadableByteChannel ch= Channels.newChannel(System.in);
        WritableByteChannel co= Channels.newChannel(System.out);
        ByteBuffer buf=ByteBuffer.allocate(10);

        try {
            if(ch.isOpen()) {
                while(ch.read(buf)!=-1) {
                    String str = new String(buf.array());
                    if(str.equals("quit"))
                    {
                        ch.close();
                        co.close();
                        break;
                    }
                    buf.flip();

                    co.write(buf);
                    buf.clear();
//                    while(buf.hasRemaining())
//                    {
//                        co.write(buf);
//                    }
                    //buf.clear();
                }

            }
        } catch (IOException e) {

        }

        }

    public static void main2(String args[]) {

        FileInputStream fis= null;
        try {
            fis = new FileInputStream("1.txt");
        } catch (FileNotFoundException e) {

        }

        FileChannel fc=fis.getChannel();
        ByteBuffer buf=ByteBuffer.allocate(10);
        ByteBuffer buf2=ByteBuffer.allocate(10);
        ByteBuffer [] bufs={buf,buf2};
        try {

                fc.read(bufs);
                System.out.println(buf.remaining());
                buf.flip();
                System.out.println(new String(buf.array()));
                System.out.println(buf2.remaining());
                buf2.flip();
                System.out.println(new String(buf2.array()));


                return;



        } catch (IOException e) {

        }

    }

    public static void main(String args[])
    {

        try {
            FileInputStream raf=new FileInputStream("1.txt");

            FileChannel fc=raf.getChannel();
            try {
                ByteBuffer bb=ByteBuffer.allocate(1000);
                MappedByteBuffer mbf=fc.map(FileChannel.MapMode.READ_ONLY,0,10000);
               mbf= mbf.load();
               // fc.read(bb);
                //bb.flip();
                //mbf.load();
                //mbf.flip();

                System.out.println(Charset.defaultCharset().decode(mbf));
               // System.out.println(Charset.defaultCharset().decode(bb));

            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {

        }

    }
@Test
    public  void testtransfer()
    {
        //通道间传输
        try {
            RandomAccessFile raf1=new  RandomAccessFile("1.txt","rw");
            FileChannel fc1=raf1.getChannel();
            RandomAccessFile raf2=new RandomAccessFile("2.txt","rw");
            FileChannel fc2=raf2.getChannel();
            try {
                fc1.transferTo(0,fc1.size(),fc2);
                fc1.force(true);
                fc1.close();
                fc2.close();
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {

        }
    }

    @Test
    public void setEntity()
    {
        List<userentity> list=new ArrayList<userentity>();
    userentity um=new userentity();
        list.add(um);
        Testentity(list);
        System.out.print(list.get(0).getTets());

    }
    @Test
    public void testStringBuildermain()
    {
        StringBuilder sb=new StringBuilder("hello ");
        String sk=new String("hello");
        testStringBuilder(sb,sk);
        System.out.print(sb);
        System.out.print(sk);
    }
    public void testStringBuilder(StringBuilder sb,String sk)
    {
        sb.append("123123");
        sk=sk+"world";
    }
    public void Testentity(List<userentity> list)
    {

        list.get(0).setTets("hello");

    }
    @Test
    public void testread()
    {

            Date dt1=new Date();

            for(int i=0;i<10;i++) {
                try {
                    FileInputStream fis = new FileInputStream("output.xml");
                    InputStreamReader isr = new InputStreamReader(fis);
                    char[] buf = new char[10000];
                    try {
                        while (isr.read(buf) != -1) {
                            System.out.println(buf);
                        }
                    } catch (IOException e) {

                    }
                } catch (FileNotFoundException e) {

                }
            }
        Date dt2=new Date();

        for(int i=0;i<10;i++) {
            try {
                FileInputStream fis = new FileInputStream("output.xml");
                InputStreamReader isr = new InputStreamReader(fis);
                CharBuffer cb = CharBuffer.allocate(10000);
                try {
                    while (isr.read(cb) != -1) {
                        System.out.println(cb.array());
                        cb.clear();
                    }
                } catch (IOException e) {

                }
            } catch (FileNotFoundException e) {

            }
        }
        Date dt3=new Date();
        System.out.println(dt1);
        System.out.println(dt2);
        System.out.println(dt3);




    }
@Test
    public  void testRetain()
    {
        try{
            int a=10/0;
        }
        catch(Exception ex)
        {
            return;
        }
        finally {
            System.out.print("fjkdj");
        }
    }
    @Test
    public void testcahr()
    {
        System.out.print('a'<'9');
        StringBuilder sb=new StringBuilder("");
        sb.append("ffff");
        System.out.println(sb.toString());
        sb.delete(0,sb.length());
        System.out.println("kong"+sb.toString());
        sb.append("aaaa");
        System.out.println(sb.toString());
        String in="1234";
        System.out.print(Integer.valueOf(String.valueOf(in.charAt(1)))*2);
        System.out.print('9'-'8');

    }
    @Test
    public void TestIntLen()
    {
        System.out.println(Integer.MAX_VALUE);
    }

    @Test
    public void test()
    {
        String a="test";
        String b="test";
        String c=new String("test");
        String d=new String("test");
        System.out.println(a==b);
        System.out.println(a==c);
        System.out.println(c==d);

    }
    @Test
    public void testformat()
    {
        Double dd=12.333;
        System.out.println(String.format("value=%.2f",dd));
    }
    @Test
    public void testfor()
    {
        List<UserItem> users=new ArrayList<UserItem>();
        UserItem user=new UserItem(){
            {
                setColumn_name("zhangchao");
            }
        };
        users.add(user);
        for(UserItem u:users)
        {
            u.setColumn_name("hello");
        }
//        Iterator item=users.iterator();
//        while(item.hasNext())
//        {
//            UserItem ui=(UserItem) item.next();
//            ui.setColumn_name("wana");
//        }
        System.out.print(users.get(0).getColumn_name());
        List<String> strings=new ArrayList<String>();
        strings.add("cat");
        for(String y:strings)
        {
            y="dog";
        }
        System.out.println(strings.get(0));


    }
    @Test
    public  void testPath()
    {
        print(System.getProperty("user.dir"));
        //Collections.sort();

    }
    private   void print(String str)
    {
        System.out.println(str);
    }
    @Test
    public  void testlonglen()
    {
        long a=1000;
        System.out.println((int)a);
    }


    @Test
    public void testdate()
    {
        pub_base_deadlineM("20150131",1);
    }
    public int pub_base_deadlineM(String date, int num) {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cd = Calendar.getInstance();
        String dqdate = null;
        try {
            cd.setTime(df.parse(date));
            cd.add(Calendar.MONTH, num);
            dqdate = df.format(cd.getTime());
        } catch (ParseException e) {
            return -1;
        }
        System.out.println(dqdate);
        return 0;
    }

    public static void testsort()
    {

    }
    @Test
    public  void testEnv()
    {
        System.out.println(System.getProperties());
    }
    @Test
    public String getEnv(String key)
    {
        Properties props=new Properties();
        try {
            String savePath = Thread.currentThread().getContextClassLoader().getResource("env.properties").getPath().replace("%20", " ");
            FileReader fr=new FileReader(savePath);
            props.load(fr);
            return props.get("jdbc.url").toString();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }





}

