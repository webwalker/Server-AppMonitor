package bms.core.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import bms.core.common.util.MessageUtil;

/**
 * @author xu.jian
 * 
 */
public class BaseService {

	@Autowired
	protected MessageUtil msg;

	@Autowired
	protected SqlSession sqlSession;
}
