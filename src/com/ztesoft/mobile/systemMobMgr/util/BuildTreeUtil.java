package com.ztesoft.mobile.systemMobMgr.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBean;

public class BuildTreeUtil {

	/**
	 * @param ParentId
	 * @param ls
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private static boolean isRoot(String getIdMethodName, String ParentId,List ls) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method getIdMethod = null;
		Method getParentIdMethod = null;


		for (Iterator iter = ls.iterator(); iter.hasNext();) {
			Object bean = iter.next();
			if (getIdMethod == null ){
				getIdMethod = bean.getClass().getMethod(getIdMethodName, null);
			}

			//System.out.println(ParentId	+ ":"+getIdMethod.invoke(bean, null));
			if ( getIdMethod.invoke(bean, null).equals( ParentId)){
				return false;
			}
		}
		return true;
	}
	/**
	 * @param InfoList 待建立的列表
	 * @param getIdMethodName 获取id的get方法的名称
	 * @param getParentIdMethodName 获取父对象id的方法的名称
	 * @return 建立好树的列表
	 * 传入的List 里的bean 必须包含一个名为Children的List， 同时也需要有getChildren 和 setChildren的方法
	 */
	public static List buildTreeData(List InfoList, String getIdMethodName,
			String getParentIdMethodName) {
		Method getIdMethod = null;
		Method getParentIdMethod = null;

		List ls = new ArrayList();
		if (InfoList != null) {
			try {

				for (Iterator it = InfoList.iterator(); it.hasNext();) {
					Object Node = it.next();
					if (getIdMethod == null) {
						getIdMethod = Node.getClass().getMethod(
								getIdMethodName, null);

					}
					if (getParentIdMethod == null) {
						getParentIdMethod = Node.getClass().getMethod(
								getParentIdMethodName, null);
					}
					// getIdMethod.invoke(PNode,null).equals(obj)

					if (getIdMethod.invoke(Node, null).equals(
							getParentIdMethod.invoke(Node, null)))
						continue;
					if ("0".equals(getParentIdMethod.invoke(Node, null))) {
						// it.remove();
						Object parent = Node;
						getSubTreeData(InfoList, parent, getIdMethodName,
								getParentIdMethodName);
						ls.add(parent);
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return ls;
	}

	public static void getSubTreeData(List InfoList, Object par,
			String getIdMethodName, String getParentIdMethodName) {
		Method getIdMethod = null;
		Method getParentIdMethod = null;

		Method getChildMethod;
		try {
			getChildMethod = par.getClass().getMethod("getChildren", null);
			// 设置child的参数
			Class[] cl = new Class[1];
			cl[0] = List.class;
			Method setChildMethod = par.getClass().getMethod("setChildren", cl);

			for (Iterator it = InfoList.iterator(); it.hasNext();) {
				Object sub =  it.next();
				if (getIdMethod == null) {
					getIdMethod = sub.getClass().getMethod(getIdMethodName,
							null);
				}
				if (getParentIdMethod == null) {
					getParentIdMethod = sub.getClass().getMethod(
							getParentIdMethodName, null);
				}
				if (getParentIdMethod.invoke(sub, null).equals(
						getIdMethod.invoke(par, null))) {
					if (getChildMethod.invoke(par, null) == null) {
						setChildMethod.invoke(par, new ArrayList());
					}
					// it.remove();
					((List) getChildMethod.invoke(par, null)).add(sub);
				}
			}
			List list = (List) getChildMethod.invoke(par, null);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					if (InfoList.size() == 0)
						break;
					Object sz = list.get(i);
					getSubTreeData(InfoList, sz, getIdMethodName,
							getParentIdMethodName);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param InfoList 待建立的列表
	 * @param getIdMethodName 获取id的get方法的名称
	 * @param getParentIdMethodName 获取父对象id的方法的名称
	 * @return 建立好树的列表
	 * 传入的List 里的bean 必须包含一个名为Children的List， 同时也需要有getChildren 和 setChildren的方法
	 */
	public static List buildTreeData(List InfoList, String topId) {
		Method getIdMethod = null;
		Method getParentIdMethod = null;

		List ls = new ArrayList();
		if (InfoList != null) {
			try {
				for (Iterator it = InfoList.iterator(); it.hasNext();) {
					Object Node = it.next();
					if (getIdMethod == null) {
						getIdMethod = Node.getClass().getMethod("getNodeId", null);
					}
					if (getParentIdMethod == null) {
						getParentIdMethod = Node.getClass().getMethod("getParentId", null);
					}
					if (getIdMethod.invoke(Node, null).equals(getParentIdMethod.invoke(Node, null)))
						continue;
					if (topId.equals(getParentIdMethod.invoke(Node, null))) {
						Object parent = Node;
						getSubTreeData(InfoList, parent, "getNodeId", "getParentId");
						ls.add(parent);
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return ls;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		List ls = new ArrayList<TreeBean>();
	    TreeBean de = new TreeBean();
	    de.setId("1");
	    de.setParentId("0");
	    ls.add(de);

	    de = new TreeBean();
	    de.setId("2");
	    de.setParentId("1");
	    ls.add(de);

	    de = new TreeBean();
	    de.setId("3");
	    de.setParentId("1");
	    ls.add(de);

	    de = new TreeBean();
	    de.setId("4");
	    de.setParentId("0");
	    ls.add(de);


	    for(int i=0; i< 100; i++) {
	    	 de = new TreeBean();
	 	    de.setId("4");
	 	    de.setParentId("0");
	 	    ls.add(de);
	    }
	    List ls2 = buildTreeData(ls, "getId", "getParentId");
	    System.out.println( JsonUtil.getExtGridJsonData(ls2));
	}

}
