package com.wq.ibaits;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Ibaits常用方法.<br>
 * 包含获取SqlMapClient
 * 
 * @author qingwu
 * 
 */
public class IbaitsUtil {

	/**
	 * sqlMapConfig.xml配置位置.
	 */
	public static final String SQL_MAP_CONFIG = "SqlMapConfig.xml";
	/**
	 * SqlMapClient.
	 */
	private static SqlMapClient sqlMap;

	/**
	 * 静态初始化.
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
	 * 插入.
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
	 * 更新.
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
	 * 删除.
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
	 * 查找.
	 * 
	 * @param obj
	 * @param flag
	 * @return 一个对象
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
	 * 查找.
	 * 
	 * @param obj
	 * @param flag
	 * @return 一个列表
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
