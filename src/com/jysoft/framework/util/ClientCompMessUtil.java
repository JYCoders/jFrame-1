package com.jysoft.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/***
 * 获得客户端机器的配置
 * @author Administrator
 *
 */
@Component
public class ClientCompMessUtil {
	//获取ip地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)){//本机登陆
    		InetAddress ia = null;
			try {
				ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
    		ip = ia.toString().split("/")[1];
    	}
		//System.out.println(ip);
		return ip;
	}

	//获取MAC地址
	public String getMacAddress(String remotePcIP) {
		  	String str = "";
	        String macAddress = "";
	        String cmd="cmd /c nbtstat.exe -a ";
	        try {
	        	if("64".equals(getWindowsVer())){
	        		cmd = "cmd /c C:\\Windows\\sysnative\\nbtstat.exe -a ";
	        	}
	        	Process p = Runtime.getRuntime().exec(cmd + remotePcIP);
	            InputStreamReader ir = new InputStreamReader(p.getInputStream());
	            LineNumberReader input = new LineNumberReader(ir);
	            for (int i = 1; i < 100; i++) {
	                str = input.readLine();
	                if (str != null) {
	                    //if (str.indexOf("MAC Address") > 1) {
	                    if (str.indexOf("MAC") > 1) {
	                        macAddress = str.substring(
	                                str.indexOf("=") + 2, str.length());
	                        System.out.println(macAddress);
	                        break;
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace(System.out);
	        }
	        return macAddress;
	}     
	    /**  
	     * 获取操作系统名称  
	     */  
	    public static String getOsName() {   
	        String os = "";   
	        os = System.getProperty("os.name");   
	        return os;   
	    }   

	    /**
	     * 获取windows操作系统是32位还是64位
	     * @return
	     */
	    public String  getWindowsVer(){
	    	if(getOsName().toLowerCase().startsWith("win")){  
	    		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		    	String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
		    	if((arch !=null && arch.endsWith("64"))|| wow64Arch != null && wow64Arch.endsWith("64")){
		    		return "64";
		    	}else{
		    		return "32";
		    	}
	    	}else{ //linux暂不支持，都认定32位
	    		try {
	    			Process process = Runtime.getRuntime().exec("getconf LONG_BIT");
	    			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    			if(bufferedReader.readLine().contains("64")){
	    			//return "64";实际64位
	    			return "32";
    			}else{
	    			return "32";
	    			}
    			} catch (IOException e) {
	    			return "Unknown";
    			}
	    	}
	    }
	    
}
