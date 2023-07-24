package com.ztesoft.mobile.v2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

public class FTPUtils {

	private FTPClient ftpClient;

	private String ip; // 服务器IP地址
	private String userName; // 用户名
	private String userPwd; // 密码
	private int port; // 端口号
	private String path; // 读取文件的存放目录
	private boolean cmdFlag = false;
	
	public FTPUtils(Map<String, String> map) {
		ip = String.valueOf(map.get("UP_IP_ADDR"));
		userName = String.valueOf(map.get("UP_FTP_USER"));
		userPwd = String.valueOf(map.get("UP_FTP_PASSWORD"));
		port = Integer.parseInt(String.valueOf(map.get("UP_PORT")));
		path = String.valueOf(map.get("UP_PATH"));
		this.connectServer(ip, port, userName, userPwd, path);
	}
	
	/**
	 * @param ip
	 * @param port
	 * @param userName
	 * @param userPwd
	 * @param path
	 * @throws SocketException
	 * @throws IOException
	 *             function:连接到服务器
	 */
	public void connectServer(String ip, int port, String userName,
			String userPwd, String path) {
		ftpClient = new FTPClient();
		boolean flag = false;
		try {
			// 连接
			ftpClient.connect(ip, port);

			// 登录
			flag = ftpClient.login(userName, userPwd);
			if (path != null && path.length() > 0) {
				// 跳转到指定目录
				flag = ftpClient.changeWorkingDirectory(path);
				if(!flag)
				{
					//跳转指定目录失败则创建目录之后再跳转
					String[] mkDirs = path.split("/");
					boolean mkDirFlag = false;
					String tempPath="/";
					for(int i=0;i<mkDirs.length;i++)
					{
						if(mkDirs[i]!=null && mkDirs[i]!="")
						{
							mkDirFlag = ftpClient.makeDirectory(mkDirs[i]);
							if(mkDirFlag)
							{
								tempPath+=mkDirs[i]+"/";
								ftpClient.changeWorkingDirectory(tempPath);
								System.out.println("目录："+mkDirs[i]+"创建成功!");
							}else
							{
								if(i>0)
								{
									tempPath+=mkDirs[i]+"/";
									ftpClient.changeWorkingDirectory(tempPath);
								}
								System.out.println("目录："+mkDirs[i]+"已经存在!");
							}
						}
					}
					flag = ftpClient.changeWorkingDirectory(path);
				}
			}
		} catch (SocketException e) {
			flag = false;
			e.printStackTrace();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		this.setCmdFlag(flag);
	}
	
	/**
	 * 文件上传
	 * */
	public boolean storeFile(String remote, InputStream in) {
		boolean bl = false;
		try {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			//ftpClient.setControlEncoding("UTF-8");
			bl = ftpClient.storeFile(new String(remote.getBytes("UTF-8"),"iso-8859-1"), in);
			in.close();
		} catch (IOException e) {
			bl = false;
			e.printStackTrace();
		}
		return bl;
	}
	
	/**
	  * @throws IOException function:关闭连接
	  */
	 public void closeServer() {
	  if (ftpClient.isConnected()) {
	   try {
	    ftpClient.logout();
	    ftpClient.disconnect();
	   } catch (IOException e) {
	    e.printStackTrace();
	   }
	  }
	 }

	public boolean isCmdFlag() {
		return cmdFlag;
	}

	public void setCmdFlag(boolean cmdFlag) {
		this.cmdFlag = cmdFlag;
	}
}
