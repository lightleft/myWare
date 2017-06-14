package cn.cj.appthems.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.cj.appthems.ampper.UserMapper;
import cn.cj.appthems.domain.User;

@Service
public class LoginService {
	@Resource
	private UserMapper userMapper;
	
	public User getUserById(Integer id){
		return userMapper.getUserById(id);
	}
}
