package com.wq.ibaits;

import java.util.Map;

public class IbaitsFactoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, IbaitsVO> map = IbaitsFactory.getSqlMap();
		IbaitsVO ibaitsVO = map.get("selectUserByVO");
		System.out.print(ibaitsVO.getId() + "\n" + ibaitsVO.getOptType() + "\n"
				+ ibaitsVO.getSql());
	}

}
