package com.bhl.dams.damsactiviti.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ActivitiDao {
	@Select("select ID_ from ACT_RE_PROCDEF where DEPLOYMENT_ID_ = #{did}")
	public String searchProcceId(@Param("did")String did);
}
