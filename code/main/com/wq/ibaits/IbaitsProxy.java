package com.wq.ibaits;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wq.utils.CaughtException;
import com.wq.utils.LogUtils;

/**
 * Ibaits��ѯ����ִ��.
 * 
 * @author qingwu
 * 
 */
public class IbaitsProxy implements InvocationHandler {

	LogUtils log = new LogUtils(IbaitsProxy.class);

	/**
	 * ��ѯ��־.
	 */
	public static final String SELECT = "select";
	/**
	 * �����־.
	 */
	public static final String INSERT = "insert";
	/**
	 * ���±�־.
	 */
	public static final String UPDATE = "update";
	/**
	 * ɾ����־.
	 */
	public static final String DELETE = "delete";
	/**
	 * �б��ѯ��־.
	 */
	public static final String LIST = "list";
	/**
	 * �����ѯ��־.
	 */
	public static final String OBJECT = "object";

	/**
	 * ����ִ�з���.
	 */
	@SuppressWarnings("static-access")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		IbaitsVO ibaitsVO = IbaitsFactory.getSqlMap().get(methodName);
		if (ibaitsVO == null) {
			log.err("������IDΪ" + methodName + "��Ibaits���ã�");
			throw new CaughtException("������IDΪ" + methodName + "�����ã�");
		}
		String optType = ibaitsVO.getOptType();
		if (this.SELECT.equals(optType)) {
			if (method.getReturnType().getName().equals("java.util.List")) {
				optType = this.LIST;
			} else {
				optType = this.OBJECT;
			}
		}
		// System.out.println("------------------>����ִ��:\n�������ƣ�" + methodName
		// + "\n�����������ͣ�" + method.getReturnType().getName() + "\n������"
		// + args[0]);
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		if (this.INSERT.equals(optType)) {// �������
			if (args != null && args.length > 0) {
				sqlMap.insert(methodName, args[0]);
			} else {
				sqlMap.insert(methodName);
			}
			return true;
		} else if (this.UPDATE.equals(optType)) {// ���²���
			if (args != null && args.length > 0) {
				sqlMap.update(methodName, args[0]);
			} else {
				sqlMap.update(methodName);
			}
			return true;
		} else if (this.DELETE.equals(optType)) {// ɾ��
			if (args != null && args.length > 0) {
				sqlMap.delete(methodName, args[0]);
			} else {
				sqlMap.delete(methodName);
			}
			return true;
		} else if (this.LIST.equals(optType)) {// �б��ѯ
			if (args != null && args.length > 0) {
				return sqlMap.queryForList(methodName, args[0]);
			} else {
				return sqlMap.queryForList(methodName);
			}
		} else if (this.OBJECT.equals(optType)) {// �����ѯ
			if (args != null && args.length > 0) {
				return sqlMap.queryForObject(methodName, args[0]);
			} else {
				return sqlMap.queryForObject(methodName);
			}
		}
		return null;
	}
}
