package com.zhyy.services.impl;

import com.zhyy.entity.Log;
import com.zhyy.mapper.LogMapper;
import com.zhyy.services.LogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogServicesImpl implements LogServices
{
	@Autowired
	private LogMapper logMapper;

	@Override
	public List<Log> queryAll(String where,String limits)
	{
		return logMapper.queryAll(where,limits);
	}

	@Override
	public int save(Log log)
	{
		return logMapper.save(log);
	}
}
