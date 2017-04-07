import org.apache.commons.dbcp.BasicDataSource;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;

public class toCompDemo {
	public static void main(String[] args) throws Exception {
		toTrade("prdt_cur_rel");
	}
	
	public static <T> ArrayList<T> bindDataToDTO(ResultSet rs, Class<T> T) throws Exception {
		ArrayList<T> list=new ArrayList<T>();
		//取得Method方法   
        Method[] methods = T.getMethods();
        //取得ResultSet的列名   
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnsCount = rsmd.getColumnCount();   
        String[] columnNames = new String[columnsCount];
        for (int i = 0; i < columnsCount; i++) {   
            columnNames[i] = rsmd.getColumnLabel(i + 1);   
        }
        //遍历ResultSet   
        while (rs.next()) {
        	T t=T.newInstance();
            //反射, 从ResultSet绑定到JavaBean   
            for (int i = 0; i < columnNames.length; i++) {   
                //取得Set方法   
                String setMethodName = "set" + columnNames[i];
                //遍历Method   
                for (int j = 0; j < methods.length; j++) {   
                    if (methods[j].getName().equalsIgnoreCase(setMethodName)) {   
                        setMethodName = methods[j].getName();   
                        Object value = rs.getObject(columnNames[i]);
                        //实行Set方法   
                        try {   
                            //JavaBean内部属性和ResultSet中一致时候   
                            if(value != null) {
                                Method setMethod = T.getMethod(
                                        setMethodName, value.getClass());   
                                setMethod.invoke(t, value);
                            }
                        } catch (Exception e) {
                            //JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。   
                            Method setMethod = T.getMethod(
                                    setMethodName, String.class);
                            setMethod.invoke(t, value.toString());
                        }   
                    }   
                }   
            } 
            list.add(t);
        }  
        return list;
    }
	
	public static void toTrade(String tableString) throws Exception {
		BasicDataSource baseDataSource=new BasicDataSource();
		baseDataSource.setUrl("jdbc:oracle:thin:@10.1.5.8:1521:orcl");
		baseDataSource.setUsername("coredb");
		baseDataSource.setPassword("coredb");
		baseDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		baseDataSource.setInitialSize(30);
		baseDataSource.setMaxActive(300);
		baseDataSource.setMaxIdle(3000);
		Connection connection= baseDataSource.getConnection();
		ResultSet rs=null;
		String sql = null;
		BufferedWriter bufferedWriter = null;
		ArrayList<UserItem> indexNames;
		UserItem indexName;
		ArrayList<UserItem> uniqueNames;
		UserItem uniqueName;
		ArrayList<UserItem> columnNames;
		UserItem columnName;
		String stringComp = "";
		String stringItem = "";
		String stringitem = "";
		String[] columnStrings;
		String[] tableStrings;
		PreparedStatement preparedStatementindex;
		PreparedStatement preparedStatementunique;
		PreparedStatement preparedStatementcolumn;
			
		tableString = tableString.toLowerCase();
		tableStrings = tableString.split("_");
		stringComp="";
		stringItem="";
		for(int k=0;k<tableStrings.length;k++)
		{
			stringComp+=tableStrings[k].substring(0,1).toUpperCase();
			stringComp+=tableStrings[k].substring(1);
			stringItem+=tableStrings[k].substring(0,1).toUpperCase();
			stringItem+=tableStrings[k].substring(1);
		}
		stringComp+="Comp";
		stringItem+="Item";
		stringitem=stringItem.substring(0,1).toLowerCase()+stringItem.substring(1);
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir")+"\\"+stringComp+".java"),"utf-8"));
		System.out.print(System.getProperty("user.dir")+"\\"+stringComp+".java");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("package com.dhcc.business.de.comp.bus.dao;\n\n");
		stringBuffer.append("import java.sql.SQLException;\n");
		stringBuffer.append("import java.util.List;\n");
		stringBuffer.append("import com.dhcc.business.component.tec.DatabaseComponent;\n");
		stringBuffer.append("import com.dhcc.common.logger.Logger;\n");
		stringBuffer.append("import com.dhcc.common.logger.LoggerFactory;\n");
		stringBuffer.append("import com.dhcc.icore.common.Constants;\n");
		stringBuffer.append("import com.dhcc.tradeflow.component.BaseComponent;\n");
		stringBuffer.append("import com.dhcc.business.de.api.trade.info."+stringitem.substring(0,stringitem.length()-4)+"."+stringItem+";\n");
		stringBuffer.append("import com.dhcc.tradeflow.entity.api.TradeContext;\n\n");
		stringBuffer.append("public class "+stringComp+" extends BaseComponent{\n");
		stringBuffer.append("\tprivate final static Logger log = LoggerFactory.getLogger("+stringComp+".class);\n");
		stringBuffer.append("\tprivate String sql = null;\n");
		stringBuffer.append("\tprivate int iRet = 0;\n");
		stringBuffer.append("\tprivate DatabaseComponent databaseComponent;\n");
		stringBuffer.append("\tpublic "+stringComp+"(TradeContext context) {\n");
		stringBuffer.append("\t\tsuper(context);\n");
		stringBuffer.append("\t\tdatabaseComponent = new DatabaseComponent(context);\n");
		stringBuffer.append("\t}\n");
		int count = 0;
		sql = "select index_name from USER_INDEXES where table_name = '"+tableString.toUpperCase()+"'";
		preparedStatementindex=connection.prepareStatement(sql);
		rs = preparedStatementindex.executeQuery();
		indexNames = bindDataToDTO(rs,UserItem.class);
		preparedStatementindex.close();
		
		Iterator<UserItem> iteratorIndex = indexNames.iterator();
		while(iteratorIndex.hasNext()){
			indexName=iteratorIndex.next();
			if(indexName==null){
				break;
			}
			count++;
			sql = "select uniqueness from USER_INDEXES where table_name = '"+tableString.toUpperCase()+"' and index_name = '"+indexName.getIndex_name()+"'";
			preparedStatementunique=connection.prepareStatement(sql);
			rs = preparedStatementunique.executeQuery();
			uniqueNames = bindDataToDTO(rs,UserItem.class);
			uniqueName=uniqueNames.get(0);
			preparedStatementunique.close();
			
			sql = "select column_name from user_ind_columns where table_name = '"+tableString.toUpperCase()+"' and index_name = '"+indexName.getIndex_name()+"'";
			preparedStatementcolumn=connection.prepareStatement(sql);
			rs = preparedStatementcolumn.executeQuery();
			columnNames = bindDataToDTO(rs,UserItem.class);
			preparedStatementcolumn.close();
			
			int i=0;
			Iterator<UserItem> iteratorColumn = columnNames.iterator();
			columnStrings = new String[columnNames.size()];
			while(iteratorColumn.hasNext()){
				columnName=iteratorColumn.next();
				if(columnName==null){
					break;
				}
				columnStrings[i++]=columnName.getColumn_name().toLowerCase();
			}
			
			if("NONUNIQUE".equals(uniqueName.getUniqueness())){
				stringBuffer.append("\t/**********************************************************************\n");
				stringBuffer.append("\t* "+tableString+" 按照索引多条查询\n");
				stringBuffer.append("\t* \n");
				for(int j=0;j<columnStrings.length;j++){
					stringBuffer.append("\t* @param	"+columnStrings[j]+"\n");
				}
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @return	\n");
				stringBuffer.append("\t* 		0:成功\n");
				stringBuffer.append("\t* 		1:失败\n");
				stringBuffer.append("\t* 	  100:为空\n");
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @author WangRui\n");
				stringBuffer.append("\t***********************************************************************/\n");
				stringBuffer.append("\tpublic int list"+count+"("+stringItem+" "+stringitem+", List<"+stringItem+"> "+stringitem+"s)\n");
				stringBuffer.append("\t{\n");
				stringBuffer.append("\t\tsql = \"select * from "+tableString+" where ");
				for(int j=0;j<columnStrings.length;j++){
					if(j==0){
						stringBuffer.append(columnStrings[j]+" = ?");
					}
					else{
						stringBuffer.append(" and "+columnStrings[j]+" = ?");
					}
				}
				stringBuffer.append("\";\n");
				stringBuffer.append("\t\ttry {\n");
				stringBuffer.append("\t\t\t"+stringitem+"s = databaseComponent.queryMulti(sql, "+stringItem+".class");
				for (int j = 0; j < columnStrings.length; j++) {
					stringBuffer.append(", "+stringitem+".get"+columnStrings[j].substring(0,1).toUpperCase()+columnStrings[j].substring(1)+"()");
				}
				stringBuffer.append(");\n");
				stringBuffer.append("\t\t} catch (SQLException e) {\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询出错!\");\n");
				stringBuffer.append("\t\t\treturn 1;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\tif("+stringitem+"s == null)\n");
				stringBuffer.append("\t\t{\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询为空!\");\n");
				stringBuffer.append("\t\t\treturn Constants.DB_NOTFOUND;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\treturn 0;\n");
				stringBuffer.append("\t}\n");
				
				stringBuffer.append("\t/**********************************************************************\n");
				stringBuffer.append("\t* "+tableString+" 按照索引多条锁表查询\n");
				stringBuffer.append("\t* \n");
				for(int j=0;j<columnStrings.length;j++){
					stringBuffer.append("\t* @param	"+columnStrings[j]+"\n");
				}
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @return	\n");
				stringBuffer.append("\t* 		0:成功\n");
				stringBuffer.append("\t* 		1:失败\n");
				stringBuffer.append("\t* 	  100:为空\n");
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @author WangRui\n");
				stringBuffer.append("\t***********************************************************************/\n");
				stringBuffer.append("\tpublic int listForUpdate"+count+"("+stringItem+" "+stringitem+", List<"+stringItem+"> "+stringitem+"s)\n");
				stringBuffer.append("\t{\n");
				stringBuffer.append("\t\tsql = \"select * from "+tableString+" where ");
				for(int j=0;j<columnStrings.length;j++){
					if(j==0){
						stringBuffer.append(columnStrings[j]+" = ?");
					}
					else{
						stringBuffer.append(" and "+columnStrings[j]+" = ?");
					}
				}
				stringBuffer.append("\";\n");
				stringBuffer.append("\t\ttry {\n");
				stringBuffer.append("\t\t\t"+stringitem+"s = databaseComponent.queryMultiForUpdate(sql, "+stringItem+".class");
				for (int j = 0; j < columnStrings.length; j++) {
					stringBuffer.append(", "+stringitem+".get"+columnStrings[j].substring(0,1).toUpperCase()+columnStrings[j].substring(1)+"()");
				}
				stringBuffer.append(");\n");
				stringBuffer.append("\t\t} catch (SQLException e) {\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询出错!\");\n");
				stringBuffer.append("\t\t\treturn 1;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\tif("+stringitem+"s == null)\n");
				stringBuffer.append("\t\t{\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询为空!\");\n");
				stringBuffer.append("\t\t\treturn Constants.DB_NOTFOUND;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\treturn 0;\n");
				stringBuffer.append("\t}\n");
			}
			if("UNIQUE".equals(uniqueName.getUniqueness()))
			{
				stringBuffer.append("\t/**********************************************************************\n");
				stringBuffer.append("\t* "+tableString+" 按照唯一索引单条查询\n");
				stringBuffer.append("\t* \n");
				for(int j=0;j<columnStrings.length;j++){
					stringBuffer.append("\t* @param	"+columnStrings[j]+"\n");
				}
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @return	\n");
				stringBuffer.append("\t* 		0:成功\n");
				stringBuffer.append("\t* 		1:失败\n");
				stringBuffer.append("\t* 	  100:为空\n");
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @author WangRui\n");
				stringBuffer.append("\t***********************************************************************/\n");
				stringBuffer.append("\tpublic int select"+count+"("+stringItem+" "+stringitem+")\n");
				stringBuffer.append("\t{\n");
				stringBuffer.append("\t\tsql = \"select * from "+tableString+" where ");
				for(int j=0;j<columnStrings.length;j++){
					if(j==0){
						stringBuffer.append(columnStrings[j]+" = ?");
					}
					else{
						stringBuffer.append(" and "+columnStrings[j]+" = ?");
					}
				}
				stringBuffer.append("\";\n");
				stringBuffer.append("\t\ttry {\n");
				stringBuffer.append("\t\t\t"+stringitem+" = databaseComponent.querySingle(sql, "+stringItem+".class");
				for (int j = 0; j < columnStrings.length; j++) {
					stringBuffer.append(", "+stringitem+".get"+columnStrings[j].substring(0,1).toUpperCase()+columnStrings[j].substring(1)+"()");
				}
				stringBuffer.append(");\n");
				stringBuffer.append("\t\t} catch (SQLException e) {\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询出错!\");\n");
				stringBuffer.append("\t\t\treturn 1;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\tif("+stringitem+" == null)\n");
				stringBuffer.append("\t\t{\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询为空!\");\n");
				stringBuffer.append("\t\t\treturn Constants.DB_NOTFOUND;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\treturn 0;\n");
				stringBuffer.append("\t}\n");
				
				stringBuffer.append("\t/**********************************************************************\n");
				stringBuffer.append("\t* "+tableString+" 按照唯一索引单条锁表查询\n");
				stringBuffer.append("\t* \n");
				for(int j=0;j<columnStrings.length;j++){
					stringBuffer.append("\t* @param	"+columnStrings[j]+"\n");
				}
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @return	\n");
				stringBuffer.append("\t* 		0:成功\n");
				stringBuffer.append("\t* 		1:失败\n");
				stringBuffer.append("\t* 	  100:为空\n");
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @author WangRui\n");
				stringBuffer.append("\t***********************************************************************/\n");
				stringBuffer.append("\tpublic int selectForUpdate"+count+"("+stringItem+" "+stringitem+")\n");
				stringBuffer.append("\t{\n");
				stringBuffer.append("\t\tsql = \"select * from "+tableString+" where ");
				for(int j=0;j<columnStrings.length;j++){
					if(j==0){
						stringBuffer.append(columnStrings[j]+" = ?");
					}
					else{
						stringBuffer.append(" and "+columnStrings[j]+" = ?");
					}
				}
				stringBuffer.append("\";\n");
				stringBuffer.append("\t\ttry {\n");
				stringBuffer.append("\t\t\t"+stringitem+" = databaseComponent.querySingleForUpdate(sql, "+stringItem+".class");
				for (int j = 0; j < columnStrings.length; j++) {
					stringBuffer.append(", "+stringitem+".get"+columnStrings[j].substring(0,1).toUpperCase()+columnStrings[j].substring(1)+"()");
				}
				stringBuffer.append(");\n");
				stringBuffer.append("\t\t} catch (SQLException e) {\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询出错!\");\n");
				stringBuffer.append("\t\t\treturn 1;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\tif("+stringitem+" == null)\n");
				stringBuffer.append("\t\t{\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"查询为空!\");\n");
				stringBuffer.append("\t\t\treturn Constants.DB_NOTFOUND;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\treturn 0;\n");
				stringBuffer.append("\t}\n");
				
				stringBuffer.append("\t/**********************************************************************\n");
				stringBuffer.append("\t* "+tableString+" 按照唯一索引更新\n");
				stringBuffer.append("\t* \n");
				for(int j=0;j<columnStrings.length;j++){
					stringBuffer.append("\t* @param	"+columnStrings[j]+"\n");
				}
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @return	\n");
				stringBuffer.append("\t* 		0:成功\n");
				stringBuffer.append("\t* 		1:失败\n");
				stringBuffer.append("\t* \n");
				stringBuffer.append("\t* @author WangRui\n");
				stringBuffer.append("\t***********************************************************************/\n");
				stringBuffer.append("\tpublic int update"+count+"("+stringItem+" "+stringitem+"_src, "+stringItem+" "+stringitem+"_dst)\n");
				stringBuffer.append("\t{\n");
				stringBuffer.append("\t\tiRet = databaseComponent.updateByEntity(\""+tableString+"\", "+stringitem+"_dst, ");
				stringBuffer.append("\"");
				for(int j=0;j<columnStrings.length;j++){
					if(j==0){
						stringBuffer.append(columnStrings[j]+" = ?");
					}
					else{
						stringBuffer.append(" and "+columnStrings[j]+" = ?");
					}
				}
				stringBuffer.append("\"");
				for (int j = 0; j < columnStrings.length; j++) {
					stringBuffer.append(", "+stringitem+"_src.get"+columnStrings[j].substring(0,1).toUpperCase()+columnStrings[j].substring(1)+"()");
				}
				stringBuffer.append(");\n");
				stringBuffer.append("\t\tif(iRet != 0)\n");
				stringBuffer.append("\t\t{\n");
				stringBuffer.append("\t\t\tlog.error(\""+tableString+"更新出错!\");\n");
				stringBuffer.append("\t\t\treturn 1;\n");
				stringBuffer.append("\t\t}\n");
				stringBuffer.append("\t\treturn 0;\n");
				stringBuffer.append("\t}\n");
			}
		}
		stringBuffer.append("}\n");
		bufferedWriter.write(stringBuffer.toString());
        bufferedWriter.close();
        System.out.println(tableString+"组件生成成功!");
	}
}
