package com.bhl.dams.damsactiviti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhl.dams.damsactiviti.dao.ActivitiDao;

@Service
public class WorkFlowService {
	@Autowired
	ActivitiDao dao;

	public String searchProcceId(String did) {
		return dao.searchProcceId(did);
	}
}
