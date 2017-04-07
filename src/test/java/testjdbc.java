import org.junit.Test;

import java.sql.*;

/**
 * Created by chaoice3240 on 2017/4/5.
 */
public class testjdbc {
    Connection connection;
    public  Connection getConnection()
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
    @Test
    public  void testPS()
    {
        String sql="select * from mdm_ac_rel where mdm_sts in (?,?)";

            PreparedStatement ps = null;
            try {
                ps = getConnection().prepareStatement(sql);
                ps.setObject(1, "0");
                ps.setObject(2, "1");
                ResultSet rs = ps.executeQuery();
                int iCount=0;
                while (rs.next()) {
                    System.out.println(rs.getObject(1));
                    iCount++;
                }
                System.out.println(iCount+"got");
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }
}
