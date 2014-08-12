package com.wq.ibaits;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wq.utils.CaughtException;
import com.wq.utils.LogUtils;

/**
 * Ibaits查询代理执行.
 * 
 * @author qingwu
 * 
 */
public class IbaitsProxy implements InvocationHandler {

	LogUtils log = new LogUtils(IbaitsProxy.class);

	/**
	 * 查询标志.
	 */
	public static final String SELECT = "select";
	/**
	 * 插入标志.
	 */
	public static final String INSERT = "insert";
	/**
	 * 更新标志.
	 */
	public static final String UPDATE = "update";
	/**
	 * 删除标志.
	 */
	public static final String DELETE = "delete";
	/**
	 * 列表查询标志.
	 */
	public static final String LIST = "list";
	/**
	 * 对象查询标志.
	 */
	public static final String OBJECT = "object";

	/**
	 * 代理执行方法.
	 */
	@SuppressWarnings("static-access")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		IbaitsVO ibaitsVO = IbaitsFactory.getSqlMap().get(methodName);
		if (ibaitsVO == null) {
			log.err("不存在ID为" + methodName + "的Ibaits配置！");
			throw new CaughtException("不存在ID为" + methodName + "的配置！");
		}
		String optType = ibaitsVO.getOptType();
		if (this.SELECT.equals(optType)) {
			if (method.getReturnType().getName().equals("java.util.List")) {
				optType = this.LIST;
			} else {
				optType = this.OBJECT;
			}
		}
		// System.out.println("------------------>代理执行:\n方法名称：" + methodName
		// + "\n方法返回类型：" + method.getReturnType().getName() + "\n参数："
		// + args[0]);
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		if (this.INSERT.equals(optType)) {// 插入操作
			if (args != null && args.length > 0) {
				sqlMap.insert(methodName, args[0]);
			} else {
				sqlMap.insert(methodName);
			}
			return true;
		} else if (this.UPDATE.equals(optType)) {// 更新操作
			if (args != null && args.length > 0) {
				sqlMap.update(methodName, args[0]);
			} else {
				sqlMap.update(methodName);
			}
			return true;
		} else if (this.DELETE.equals(optType)) {// 删除
			if (args != null && args.length > 0) {
				sqlMap.delete(methodName, args[0]);
			} else {
				sqlMap.delete(methodName);
			}
			return true;
		} else if (this.LIST.equals(optType)) {// 列表查询
			if (args != null && args.length > 0) {
				return sqlMap.queryForList(methodName, args[0]);
			} else {
				return sqlMap.queryForList(methodName);
			}
		} else if (this.OBJECT.equals(optType)) {// 对象查询
			if (args != null && args.length > 0) {
				return sqlMap.queryForObject(methodName, args[0]);
			} else {
				return sqlMap.queryForObject(methodName);
			}
		}
		return null;
	}
}
