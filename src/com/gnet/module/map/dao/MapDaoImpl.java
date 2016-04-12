package com.gnet.module.map.dao;


import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository(value="mapDao")
public class MapDaoImpl implements IMapDao {
protected final Logger logger = Logger.getLogger(getClass());
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
}
