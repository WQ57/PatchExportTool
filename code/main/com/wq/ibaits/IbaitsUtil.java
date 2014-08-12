package com.wq.ibaits;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Ibaits���÷���.<br>
 * ������ȡSqlMapClient
 * 
 * @author qingwu
 * 
 */
public class IbaitsUtil {

	/**
	 * sqlMapConfig.xml����λ��.
	 */
	public static final String SQL_MAP_CONFIG = "SqlMapConfig.xml";
	/**
	 * SqlMapClient.
	 */
	private static SqlMapClient sqlMap;

	/**
	 * ��̬��ʼ��.
	 */
	static {
		Reader reader = null;
		try {
			// ibatis SQLMap
			reader = Resources.getResourceAsReader(SQL_MAP_CONFIG);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static SqlMapClient getSqlMap() {
		return sqlMap;
	}

	public static void setSqlMap(SqlMapClient sqlMap) {
		IbaitsUtil.sqlMap = sqlMap;
	}

	public static String getSqlMapConfig() {
		return SQL_MAP_CONFIG;
	}

	/**
	 * ����.
	 * 
	 * @param obj
	 * @param flag
	 * @return
	 */
	public static boolean insert(Object obj, String flag) {
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		try {
			sqlMap.startTransaction();
			sqlMap.insert(flag, obj);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * ����.
	 * 
	 * @param obj
	 * @param flag
	 * @return
	 */
	public static boolean update(Object obj, String flag) {
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		try {
			sqlMap.startTransaction();
			sqlMap.update(flag, obj);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * ɾ��.
	 * 
	 * @param obj
	 * @param flag
	 * @return
	 */
	public static boolean delete(Object obj, String flag) {
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		try {
			sqlMap.startTransaction();
			sqlMap.delete(flag, obj);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * ����.
	 * 
	 * @param obj
	 * @param flag
	 * @return һ������
	 */
	public static Object queryForObject(Object obj, String flag) {
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		Object retObj = null;
		try {
			sqlMap.startTransaction();
			retObj = sqlMap.queryForObject(flag, obj);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return retObj;
	}

	/**
	 * ����.
	 * 
	 * @param obj
	 * @param flag
	 * @return һ���б�
	 */
	@SuppressWarnings("rawtypes")
	public static List queryForList(Object obj, String flag) {
		SqlMapClient sqlMap = IbaitsUtil.getSqlMap();
		List list = null;
		try {
			sqlMap.startTransaction();
			list = sqlMap.queryForList(flag, obj);
			sqlMap.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				sqlMap.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
