package com.ztesoft.android.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;

public class MobileMuneServlet extends HttpServlet{
    	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String jsonPara = req.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		String resultCode = null;

		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String jobId = jsonObject.get("jobId")==null?"-1":jsonObject.getString("jobId");
		String defaultJobId = jsonObject.get("defaultJobId")==null?"-1":jsonObject.getString("defaultJobId");
		String staffId = jsonObject.get("staffId")==null?"-1":jsonObject.getString("staffId");
		String orgId = jsonObject.get("orgId")==null?"-1":jsonObject.getString("orgId");
		String leaf = jsonObject.get("leaf")==null?"-1":jsonObject.getString("leaf");
		String muneId = jsonObject.get("muneId")==null?"-1":jsonObject.getString("muneId");
		System.out.println("jobId "+jobId);
		resultCode = "000";
		String jsonmune = null;
		try {
			if(leaf.equals("0")){    // 如果是有子节点，则查二级菜单
			   //jsonmune = getServiceDAO().getSubMuneData(muneId,jobId,defaultJobId);
				jsonmune= JsonUtil.getJsonByList(getServiceDAO().getSubMuneData(muneId,jobId,defaultJobId, 0));
			}else if(leaf.equals("-1")){
				jsonmune=this.getInitMuneData(jobId,defaultJobId);
			}else{
				jsonmune = getServiceDAO().getMuneData(jobId,defaultJobId);
			}
		} catch (Exception e) {
			resultCode = "001";         //服务器查询异常
			e.printStackTrace();
		}
		
		String resultValue = "{\"result\": \""+resultCode+"\", \"body\": {\"muneList\": "+jsonmune+"}}";
		System.out.println("原字符串="+resultValue);   
	    System.out.println("原长="+resultValue.length());   
	    String newstr = ZipUtil.compress(resultValue);   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
		resp.setContentType("text/plain;charset=ISO-8859-1");
		resp.getWriter().write(newstr);
	}
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	public String getInitMuneData(String jobId,String defaultJobId){
		String jsonmune = null;
		try {
			List parentList=getServiceDAO().getSubMuneData("0",jobId,defaultJobId, 0);
			String praentIds="";
			for(int i=0;i<parentList.size();i++){
				HashMap parentMap=(HashMap)parentList.get(i);
				if(i==0){
					praentIds=parentMap.get("muneid").toString();
				}else{
					praentIds=praentIds+","+parentMap.get("muneid").toString();
				}
			}
			List subMuneList=getServiceDAO().getSubMuneData(praentIds,jobId,defaultJobId, 0);
			jsonmune= JsonUtil.getJsonByList(subMuneList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonmune;
	}
}
