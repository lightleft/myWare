package cn.cj.appthems.ampper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.cj.appthems.domain.User;

public interface UserMapper {
	@Select(value = "select * from user where id = #{id}")
	User getUserById(@Param("id") Integer id);
}
