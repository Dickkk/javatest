import org.dom4j.*;
import org.dom4j.io.XMLWriter;

import javax.print.attribute.DateTimeSyntax;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by chaoice3240 on 2017/3/20.
 */
public class xmlparse {
    private static Document document;
    private static Connection connection;
    private static Map<String,ResultSet> resulltsets;
    private static Map<String,Map<String,String>> PrdtPool;
    public xmlparse()
    {
        resulltsets=new HashMap<String,ResultSet>();
        PrdtPool=new HashMap<String,Map<String,String>>();
    }
    public static void main(String[] args)
    {
        resulltsets=new HashMap<String,ResultSet>();
        PrdtPool=new HashMap<String,Map<String,String>>();
        //loaddbmap();
      //  loaddb();

        //exportxml();
        while(true) {
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
          //  SearchbyXpath(str);
            SearchbyXpath("1001",str);
            try {
                System.out.print(getPrdtParmCnt("1001",str));
            } catch (SQLException e) {

            }
        }

    }
    public static int getPrdtParmCnt(String prdt_no,String key) throws SQLException {
        if(PrdtPool==null||!PrdtPool.containsKey(prdt_no))
        {
            loadPrdt(prdt_no);
        }
        Iterator iterator= PrdtPool.get(prdt_no).keySet().iterator();
        int iRet=0;
        while(iterator.hasNext())
        {
            if( iterator.next().toString().matches("^"+key+"([1-9]\\d*|0)$"))
            {
                iRet++;
            }
        }
        return iRet;

    }
    public static void loadPrdt(String prdt_no)
    {
        Statement st=null;
        Statement st_seqn=null;
        try {
            st=getConnection().createStatement();
            st_seqn=getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        String sql="select * from prdt_parm where prdt_no='"+prdt_no+"'";
        try {

            ResultSet rs=st.executeQuery(sql);
            String prdt_no_lst=null;
            Map<String,String> rows=null;
            while (rs.next())
            {
                if(!rs.getString("prdt_no").equals(prdt_no_lst))
                {
                    //新增产品节点
                    if(rows!=null)
                    {
                        PrdtPool.put(prdt_no_lst,rows);
                        rows.clear();
                    }
                    else
                    {
                        rows=new HashMap<String,String>();
                    }

                    prdt_no_lst=rs.getString("prdt_no");

                }
                //查询子表并添加到

                sql="select * from "+rs.getString("GROUP_NO")+" where parm_seqn= "+rs.getInt("parm_seqn");

                ResultSet seqnresult=st_seqn.executeQuery(sql);
                int index=0;
                while(seqnresult.next())
                {

                    for(int i=1;i<=seqnresult.getMetaData().getColumnCount();i++) {
                        if(seqnresult.getString(i)!=null&&seqnresult.getString(i).length()>0) {
                            rows.put(seqnresult.getMetaData().getColumnName(i)+index,seqnresult.getString(i));
                        }
                    }
                    index++;
                }
                System.out.println(rs.getRow()+"行:"+rs.getString("prdt_no"));
            }
            PrdtPool.put(prdt_no_lst,rows);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void load()
    {
         document = DocumentHelper.createDocument();
        Element root = document.addElement( "prdts" );


        Element author1 = root.addElement( "author" )
                .addElement( "name", "James" )
                .addElement( "location", "UK" )
                .addElement( "James Strachan" );

//        Element author2 = root.addElement( "author" )
//                .addAttribute( "name", "Bob" )
//                .addAttribute( "location", "US" )
//                .addText( "Bob McWhirter" );





    }
    public static void exportxml()
    {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(
                    new FileWriter("output.xml")
            );

            writer.write(document);
            writer.close();
        }catch (Exception e)
        {

        }
    }
    public static void SearchbyXpath(String path)
    {
        Date dt=new Date();
        DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Node node=null;
        System.out.println(df.format(dt));
        for(int i=0;i<5;i++) {
             node = document.selectSingleNode(path);
        }
        dt=new Date();
        System.out.println(df.format(dt));
    }
    public static void SearchbyXpath(String prdt_no,String key)
    {
        Date dt=new Date();
        DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Node node=null;
        System.out.println(df.format(dt));
        for(int i=0;i<5;i++) {
            if(!PrdtPool.containsKey(prdt_no))
            {
                loadPrdt(prdt_no);
            }
            System.out.println(PrdtPool.get(prdt_no).get(key+0));
        }
        dt=new Date();
        System.out.println(df.format(dt));

    }
    public static Connection getConnection()
    {
        String url="jdbc:oracle:thin:@10.1.5.8:1521/ORCL";
        String user="coredb";
        String pwd="coredb";

        try {
            if(connection==null||connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void loaddb()
    {
        Statement st=null;
        Statement st_seqn=null;
        try {
             st=getConnection().createStatement();
            st_seqn=getConnection().createStatement();
        } catch (SQLException e) {
           e.printStackTrace();
            return;
        }
        String sql="select * from prdt_parm";
        try {
            document = DocumentHelper.createDocument();
            Element root = document.addElement( "prdts" );
            ResultSet rs=st.executeQuery(sql);
            String prdt_no_lst=null;
            Element prdtele=null;
            while (rs.next())
            {
                if(!rs.getString("prdt_no").equals(prdt_no_lst))
                {
                    //新增产品节点
                    prdtele=root.addElement("prdt").addAttribute("prdt_no",rs.getString("prdt_no"));
                    prdt_no_lst=rs.getString("prdt_no");
                }
                //查询子表并添加到

                sql="select * from "+rs.getString("GROUP_NO")+" where parm_seqn= "+rs.getInt("parm_seqn");

                ResultSet seqnresult=st_seqn.executeQuery(sql);
                while(seqnresult.next())
                {
                    Element seqnele=prdtele.addElement(rs.getString("GROUP_NO"));
                    for(int i=1;i<=seqnresult.getMetaData().getColumnCount();i++) {
                        if(seqnresult.getString(i)!=null&&seqnresult.getString(i).length()>0) {
                            seqnele.addElement(seqnresult.getMetaData().getColumnName(i)).addText(seqnresult.getString(i));
                        }
                    }
                }


                System.out.println(rs.getRow()+"行:"+rs.getString("prdt_no"));

            }
        } catch (SQLException e) {
          e.printStackTrace();
            return;
        }
    }
    public static void loaddbmap()
    {
        Statement st=null;
        Statement st_seqn=null;
        try {
            st=getConnection().createStatement();
            st_seqn=getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        String sql="select * from prdt_parm";
        try {

            ResultSet rs=st.executeQuery(sql);
            String prdt_no_lst=null;
            Map<String,String> rows=null;
            while (rs.next())
            {
                if(!rs.getString("prdt_no").equals(prdt_no_lst))
                {
                    //新增产品节点
                    if(rows!=null)
                    {
                        PrdtPool.put(prdt_no_lst,rows);
                        rows.clear();
                    }
                    else
                    {
                        rows=new HashMap<String,String>();
                    }

                    prdt_no_lst=rs.getString("prdt_no");

                }
                //查询子表并添加到

                sql="select * from "+rs.getString("GROUP_NO")+" where parm_seqn= "+rs.getInt("parm_seqn");

                ResultSet seqnresult=st_seqn.executeQuery(sql);
                int index=0;
                while(seqnresult.next())
                {

                    for(int i=1;i<=seqnresult.getMetaData().getColumnCount();i++) {
                        if(seqnresult.getString(i)!=null&&seqnresult.getString(i).length()>0) {
                            rows.put(seqnresult.getMetaData().getColumnName(i)+index,seqnresult.getString(i));
                        }
                    }
                    index++;
                }
                System.out.println(rs.getRow()+"行:"+rs.getString("prdt_no"));
            }
            PrdtPool.put(prdt_no_lst,rows);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
    }
    public static void xpath1(String xpath)
    {
        List list = document.selectNodes( "//foo/bar" );

        Node node = document.selectSingleNode( "//foo/bar/author" );

        String name = node.valueOf( "@name" );
    }
    public static void xpath2()
    {
        List list = document.selectNodes( "//a/@href" );

        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Attribute attribute = (Attribute) iter.next();
            String url = attribute.getValue();
        }
    }



}
