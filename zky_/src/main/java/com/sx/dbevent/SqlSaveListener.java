package com.sx.dbevent;

import com.sx.common.GenerateId;
import com.sx.helper.DateHelper;
import com.sx.helper.StringHelper;
import com.sx.db.DaoDbTemplate;
import com.sx.db.DbBeanUtils;
import com.sx.db.DbEventListener;
import com.sx.support.dba.dbAPI;

/**
 * DB 操作日志
 */
public class SqlSaveListener implements DbEventListener {

	public void onExecuteSql(dbAPI edba, String sql, String[][] value,
			Object obj) throws Exception {
		
		//排除行为日志
		if(StringHelper.isEmpty(sql) || sql.contains("demo_czrz")){
				return ;
		}
		String relsql = DbBeanUtils.getRealSql(sql, value);
		
		//避免因为sql过长，日志报错的问题
		if(relsql.length()<=4000){
		    String inssql = "insert into sqltable (id,sql,create_time) values(?,?,?)";
			String id = GenerateId.getGenerateId();
			
			DaoDbTemplate ddt = new DaoDbTemplate(edba);
			ddt.execSql(inssql, new String[]{id,relsql,DateHelper.long2str17(System.currentTimeMillis())});
		}
	}

	public void setIsAsync() {

	}

}
