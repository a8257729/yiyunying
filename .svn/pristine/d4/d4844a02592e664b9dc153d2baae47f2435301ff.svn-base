package com.ztesoft.mobile.v2.dao.common;

public class testNong {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(countLevel("0.45.4"));
	}
	
	private static int countLevel(String code){
		if(code!=null&&!"".equals(code)){
			int le=0;
			System.out.println(code);
			String[] arr=code.split("\\.");
			System.out.println(arr.length);
			//如果树是以0.*.**开头，则认为level是1
			for(int i=0;i<arr.length;i++){
				if(!"0".equals(arr[i])){
					le++;
				}
			}
			return le;
		}else{
			return 0;
		}
		
	}

}
