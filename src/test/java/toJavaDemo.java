import org.junit.Test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class toJavaDemo {
	public static final String ENCODE = "GBK";
	BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    Pattern pattern = null;
    Matcher matcher = null;
    public static class Regex
	{
    	//	字符串变量 or 对象属性赋值
		public static final String STRING_1 = "(\\w+)(\\.)(\\w+)(\\s*=\\s*)(\\w+)(\\.)(\\w+)";
		public static final String STRING_2 = "(\\w+)(\\.)(\\w+)(\\s*=\\s*)(\\w+)";
		public static final String STRING_3 = "(\\w+)(\\s*=\\s*)(\\w+)(\\.)(\\w+)";
		public static final String STRING_4 = "(strcpy\\(\\s*)(\\w+)(\\s*,\\s*)(\\w+)\\s*\\)";
		public static final String STRING_5 = "(strcpy\\(\\s*)(\\w+)(\\s*,\\s*)(\"[\u4e00-\u9fa5]*\\w*\")\\s*\\)";
		public static final String STRING_6 = "(strcpy\\(\\s*)(\\w+)(\\.)(\\w+)(\\s*,\\s*)(\"[\u4e00-\u9fa5]*\\w*\")\\s*\\)";
		public static final String STRING_7 = "(strcpy\\(\\s*)(\\w+)(\\.)(\\w+)(\\s*,\\s*)(\\w+)\\s*\\)";
		public static final String STRING_8 = "(strcpy\\(\\s*)(\\w+)(\\.)(\\w+)(\\s*,\\s*)(\\w+)(\\.)(\\w+)\\s*\\)";
		public static final String STRING_17 = "(\\w+)->(\\w+)";
		public static final String STRING_22 = "(\\w+)\\.(\\w+)\\[0\\]\\s*=\\s*\'(\\w+)\'";
		public static final String STRING_23 = "(\\w+)\\.(\\w+)\\[0\\]\\s*=\\s*(\\w+)\\s*";
		public static final String STRING_35 = "(\\w+)\\.(\\w+)\\+\\+";
		public static final String STRING_36 = "(\\w+)\\.(\\w+)\\-\\-";
		//	从set or get
		public static final String STRING_9 = "get_zd_data\\(\\s*(\"\\w+\")\\s*,\\s*(\\w+)\\.(\\w+)\\s*\\)";
		public static final String STRING_10 = "get_zd_data\\(\\s*(\"\\w+\")\\s*,\\s*(\\w+)\\s*\\)";
		public static final String STRING_11 = "get_zd_long\\(\\s*(\"\\w+\")\\s*,\\s*\\&(\\w+)\\.(\\w+)\\s*\\)";
		public static final String STRING_12 = "get_zd_long\\(\\s*(\"\\w+\")\\s*,\\s*\\&(\\w+)\\s*\\)";
		public static final String STRING_13 = "get_zd_double\\(\\s*(\"\\w+\")\\s*,\\s*\\&(\\w+)\\.(\\w+)\\s*\\)";
		public static final String STRING_14 = "get_zd_double\\(\\s*(\"\\w+\")\\s*,\\s*\\&(\\w+)\\s*\\)";
		public static final String STRING_15 = "(\\w+)\\.(\\w+)([,|)])";
		public static final String STRING_29 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\"\\w+\")\\s*,\\s*(\\w+)\\.(\\w+)\\s*\\)";
		public static final String STRING_30 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\"\\w+\")\\s*,\\s*(\\w+)\\s*\\)";
		public static final String STRING_31 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\"\\w+\")\\s*,\\s*(\"[\u4e00-\u9fa5]*\\w*\")\\s*\\)";
		public static final String STRING_32 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\.(\\w+)\\s*\\)";
		public static final String STRING_33 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\\w+)\\s*,\\s*(\\w+)\\s*\\)";
		public static final String STRING_34 = "(set_zd_data|set_zd_long|set_zd_double)\\(\\s*(\\w+)\\s*,\\s*(\"[\u4e00-\u9fa5]*\\w*\")\\s*\\)";
		//	%符
		public static final String STRING_16 = "\\%s|\\%d|\\%lf";
		//	strlen
		public static final String STRING_18 = "strlen\\(\\s*(\\w+\\.\\w+\\(\\))\\s*\\)";
		//	goto -> return
		public static final String STRING_19 = "goto(\\s+)ErrExit";
		public static final String STRING_20 = "goto(\\s+)OkExit";
		//	错误码赋值
		public static final String STRING_21 = "g_pub_env.setReply\\(\\s*(\"\\w+\")\\s*\\)";
		// 字符判断
		public static final String STRING_24 = "(\\w+)\\.(\\w+)\\[([0-9]+)\\]\\s*==\\s*\'(\\w+)\'";
		public static final String STRING_37 = "(\\w+)\\.(\\w+)\\[([0-9]+)\\]\\s*!=\\s*\'(\\w+)\'";
		public static final String STRING_38 = "(\\w+)\\[([0-9]+)\\]\\s*==\\s*\'(\\w+)\'";
		public static final String STRING_39 = "(\\w+)\\[([0-9]+)\\]\\s*!=\\s*\'(\\w+)\'";
		// 变量类型
		public static final String STRING_25 = "char\\s*\\*?(\\s*\\w+)\\[[0-9]+\\]";
		public static final String STRING_26 = "double\\s*\\*?\\s*";
		public static final String STRING_27 = "float\\s*\\*?\\s*";
		public static final String STRING_28 = "struct\\s+(\\w+)";
		//	常量相关
		public static final String STRING_40 = "(FLDSEP|DB_NOTFOUND)";
		//  文件相关
		public static final String STRING_41 = "fprintf\\(fp";
		//	条件相关
		public static final String STRING_42 = "\\(\\s*iRet\\s*\\)";
	}
    @Test
    public void toJava() throws IOException
    {
		String filepath="D:\\work\\coresource\\work\\src\\de\\tx\\acct\\"+"spH008.c";
		bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),ENCODE));
		bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(System.getProperty("user.dir")+"\\dest.java"),ENCODE));
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		String string = null;
		String str = null;
		String newString = null;
		while((line = bufferedReader.readLine()) != null)
		{
			stringBuffer.append(line);
			stringBuffer.append("\r\n");
		}
		string = new String(stringBuffer);
		// 实体属性给实体属性赋值
		pattern = Pattern.compile(Regex.STRING_1);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		matcher.group(2).trim()+
            		"set"+
            		matcher.group(3).trim().substring(0,1).toUpperCase()+
            		matcher.group(3).trim().substring(1)+
            		"("+
            		matcher.group(5).trim()+
            		matcher.group(6).trim()+
            		"get"+
            		matcher.group(7).trim().substring(0,1).toUpperCase()+
            		matcher.group(7).trim().substring(1)+
            		"())";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 变量给实体属性赋值
        pattern = Pattern.compile(Regex.STRING_2);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		matcher.group(2).trim()+
            		"set"+
            		matcher.group(3).trim().substring(0,1).toUpperCase()+
            		matcher.group(3).trim().substring(1)+
            		"("+
            		matcher.group(5).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 单字符给实体属性[0]赋值
        pattern = Pattern.compile(Regex.STRING_22);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		".set"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"(\""+
            		matcher.group(3).trim()+
            		"\")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 变量给实体属性[0]赋值
        pattern = Pattern.compile(Regex.STRING_23);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		".set"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"("+
            		matcher.group(3).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 实体属性给变量赋值
        pattern = Pattern.compile(Regex.STRING_3);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		matcher.group(2).trim()+
            		matcher.group(3).trim()+
            		matcher.group(4).trim()+
            		"get"+
            		matcher.group(5).trim().substring(0,1).toUpperCase()+
            		matcher.group(5).trim().substring(1)+
            		"()";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 字符串变量给字符串变量赋值
        pattern = Pattern.compile(Regex.STRING_4);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		" = "+
            		matcher.group(4).trim();
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 将字符串给字符串变量赋值
        pattern = Pattern.compile(Regex.STRING_5);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		" = "+
            		matcher.group(4).trim();
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 将字符串给对象属性赋值
        pattern = Pattern.compile(Regex.STRING_6);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		matcher.group(3).trim()+
            		"set"+
            		matcher.group(4).trim().substring(0,1).toUpperCase()+
            		matcher.group(4).trim().substring(1)+
            		"("+
            		matcher.group(6).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 将字符串变量给对象属性赋值
        pattern = Pattern.compile(Regex.STRING_7);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		matcher.group(3).trim()+
            		"set"+
            		matcher.group(4).trim().substring(0,1).toUpperCase()+
            		matcher.group(4).trim().substring(1)+
            		"("+
            		matcher.group(6).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // 将对象属性给对象属性赋值
        pattern = Pattern.compile(Regex.STRING_8);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		matcher.group(3).trim()+
            		"set"+
            		matcher.group(4).trim().substring(0,1).toUpperCase()+
            		matcher.group(4).trim().substring(1)+
            		"("+
            		matcher.group(6).trim()+
            		matcher.group(7).trim()+
            		"get"+
            		matcher.group(8).trim().substring(0,1).toUpperCase()+
            		matcher.group(8).trim().substring(1)+
            		"())";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_data("域",对象属性)
        pattern = Pattern.compile(Regex.STRING_9);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		".set"+
            		matcher.group(3).trim().substring(0,1).toUpperCase()+
            		matcher.group(3).trim().substring(1)+
            		"("+
            		"getValueString("+
            		matcher.group(1).trim()+
            		"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_data("域",变量名)
        pattern = Pattern.compile(Regex.STRING_10);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		" = "+
            		"getValueString("+
            		matcher.group(1).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_long("域",&对象属性)
        pattern = Pattern.compile(Regex.STRING_11);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		".set"+
            		matcher.group(3).trim().substring(0,1).toUpperCase()+
            		matcher.group(3).trim().substring(1)+
            		"("+
            		"getValueLong("+
            		matcher.group(1).trim()+
            		"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_long("域",&变量名)
        pattern = Pattern.compile(Regex.STRING_12);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		" = "+
            		"getValueLong("+
            		matcher.group(1).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_double("域",&对象属性)
        pattern = Pattern.compile(Regex.STRING_13);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		".set"+
            		matcher.group(3).trim().substring(0,1).toUpperCase()+
            		matcher.group(3).trim().substring(1)+
            		"("+
            		"getValueDouble("+
            		matcher.group(1).trim()+
            		"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	get_zd_double("域",&变量名)
        pattern = Pattern.compile(Regex.STRING_14);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(2).trim()+
            		" = "+
            		"getValueDouble("+
            		matcher.group(1).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	对象->属性 -> 对象.属性
        pattern = Pattern.compile(Regex.STRING_17);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		".get"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"()";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	strlen -> length
        pattern = Pattern.compile(Regex.STRING_18);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		".length()";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	goto -> return
        pattern = Pattern.compile(Regex.STRING_19);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"return"+
        			matcher.group(1)+
        			"1";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	goto -> return
        pattern = Pattern.compile(Regex.STRING_20);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"return"+
        			matcher.group(1)+
        			"0";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	错误码处理
        pattern = Pattern.compile(Regex.STRING_21);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"setErrCodeAndMsg("+
        			matcher.group(1).trim()+
        			", \"\")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	判断单字符跟对象属性[num]是否相等
        pattern = Pattern.compile(Regex.STRING_24);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"\""+
        			matcher.group(4).trim()+
        			"\".equals("+
        			matcher.group(1).trim()+
        			".get"+
        			matcher.group(2).trim().substring(0,1).toUpperCase()+
        			matcher.group(2).trim().substring(1)+
        			"().charAt("+
        			matcher.group(3).trim()+
        			"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	判断单字符跟对象属性[num]是否不相等
        pattern = Pattern.compile(Regex.STRING_37);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"!\""+
        			matcher.group(4).trim()+
        			"\".equals("+
        			matcher.group(1).trim()+
        			".get"+
        			matcher.group(2).trim().substring(0,1).toUpperCase()+
        			matcher.group(2).trim().substring(1)+
        			"().charAt("+
        			matcher.group(3).trim()+
        			"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	判断单字符跟变量[num]是否相等
        pattern = Pattern.compile(Regex.STRING_38);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"\""+
        			matcher.group(3).trim()+
        			"\".equals("+
        			matcher.group(1).trim()+
        			".charAt("+
        			matcher.group(2).trim()+
        			"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	判断单字符跟变量[num]是否不相等
        pattern = Pattern.compile(Regex.STRING_39);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"!\""+
        			matcher.group(3).trim()+
        			"\".equals("+
        			matcher.group(1).trim()+
        			".charAt("+
        			matcher.group(2).trim()+
        			"))";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data("域",对象.属性)
        pattern = Pattern.compile(Regex.STRING_29);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		".get"+
            		matcher.group(4).trim().substring(0,1).toUpperCase()+
            		matcher.group(4).trim().substring(1)+
            		"())";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data("域",变量值)
        pattern = Pattern.compile(Regex.STRING_30);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data("域",字符串)
        pattern = Pattern.compile(Regex.STRING_31);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data(域,对象.属性)
        pattern = Pattern.compile(Regex.STRING_32);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		".get"+
            		matcher.group(4).trim().substring(0,1).toUpperCase()+
            		matcher.group(4).trim().substring(1)+
            		"())";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data(域,变量值)
        pattern = Pattern.compile(Regex.STRING_33);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	set_zd_data(域,字符串)
        pattern = Pattern.compile(Regex.STRING_34);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		"setValue("+
            		matcher.group(2).trim()+
            		", "+
            		matcher.group(3).trim()+
            		")";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	对象.属性++
        pattern = Pattern.compile(Regex.STRING_35);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(1).trim()+
            		".set"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"("+
            		matcher.group(1).trim()+
            		".get"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"()+1)";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	对象.属性--
        pattern = Pattern.compile(Regex.STRING_36);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
            		matcher.group(1).trim()+
            		".set"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"("+
            		matcher.group(1).trim()+
            		".get"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"()-1)";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	对象.属性 -> 对象.get属性
        pattern = Pattern.compile(Regex.STRING_15);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			matcher.group(1).trim()+
            		".get"+
            		matcher.group(2).trim().substring(0,1).toUpperCase()+
            		matcher.group(2).trim().substring(1)+
            		"()"+
            		matcher.group(3).trim();
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	常量替换
        pattern = Pattern.compile(Regex.STRING_40);
        matcher = pattern.matcher(string);
        if(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"Constants."+
        			matcher.group(1).trim();
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	文件写入
        pattern = Pattern.compile(Regex.STRING_41);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"StringUtils.printf(stringBuilder";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	(iRet) -> (iRet!=0)
        pattern = Pattern.compile(Regex.STRING_42);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"(iRet != 0)";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	char -> String
        pattern = Pattern.compile(Regex.STRING_25);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"String "+
        			matcher.group(1).trim()+
        			" = null";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	float -> double
        pattern = Pattern.compile(Regex.STRING_27);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"double ";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	double -> double
        pattern = Pattern.compile(Regex.STRING_26);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"double ";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	struct -> 实体名
        pattern = Pattern.compile(Regex.STRING_28);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	String[] strings = matcher.group(1).split("_");
        	for(int i=0;i<strings.length-1;i++)
        	{
        		if(i==0)
        		{
        			newString = (strings[i].substring(0,1).toUpperCase()+strings[i].substring(1));
        		}
        		else {
        			newString += (strings[i].substring(0,1).toUpperCase()+strings[i].substring(1));
				}
        	}
        	newString+="Item";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        //	%符替换
        pattern = Pattern.compile(Regex.STRING_16);
        matcher = pattern.matcher(string);
        while(matcher.find())
        {
        	str = matcher.group(0);
        	newString = 
        			"\"++\"";
        	System.out.println(str);
        	System.out.println(newString);
        	string = string.replace(str, newString);
        }
        // vtcp_trace -> trace
        str = "vtcp_trace";
        newString = "log.trace";
        string = string.replace(str, newString);
        
        // vtcp_exit -> error
        str = "vtcp_exit";
        newString = "log.error";
        string = string.replace(str, newString);
        
        //	vtcp_info -> info
        str = "vtcp_info";
        newString = "log.info";
        string = string.replace(str, newString);
        
        // 将0.00还原
        str = "0.get00()";
        newString = "0.00";
        string = string.replace(str, newString);
        
        // 将
        
        bufferedWriter.write(string);
        bufferedReader.close();  
        bufferedWriter.close();
    }
}

