package com.mook.locker.misc.test.mapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mook.locker.misc.domain.User2;
import com.mook.locker.misc.mapper.UserMapper2;

public class UserMapperTest2 {
	
	private static SqlSession sqlSession = null;
	private User2 user = new User2();
	
	@BeforeClass
	public static void doInitTest() throws Exception {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		sqlSession = sqlSessionFactory.openSession(true);
	}
	
	// 每次测试前都将数据库中的id为100的User的version设置成100
	@Before
	public void initPojo() throws Exception {
		user.setId(100);
		user.setName("test");
		user.setPassword("test");
		user.setMyVersion(100L);
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		userMapper.initData(user);
	}
	
	@After
	public void resetDatabaseTest() {
		user.setId(100);
		user.setName("test");
		user.setPassword("test");
		user.setMyVersion(100L);
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		userMapper.resetData(user);
	}

	@Test
	public void updateUserPojoTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUser(user);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
		
//		打印结果如下：
//		DEBUG - ==> originalSql: update smart_user set name = ?, password = ?, version = ? where id = ?
//		DEBUG - ==>  Preparing: update smart_user set name = ?, password = ?, version = ? where id = ? and version = ? 
//		DEBUG - ==> Parameters: test(String), test(String), 101(Long), 100(Integer), 100(Long)
//		DEBUG - <==    Updates: 1
	}
	
	@Test
	public void updateUserAtParamTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUser("test", "test", 100L, 100);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
		
//		打印结果如下：
//		DEBUG - ==> originalSql: update smart_user set name = ?, password = ?, version = ? where id = ?
//		DEBUG - ==>  Preparing: update smart_user set name = ?, password = ?, version = ? where id = ? and version = ? 
//		DEBUG - ==> Parameters: test(String), test(String), 101(Long), 100(Integer), 100(Long)
//		DEBUG - <==    Updates: 1
	}
	
	@Test
	public void updateUserParamPojoTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUser3(user);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}
	
	@Test
	public void updateUserMapTest() {
		Map<Object, Object> param = new HashMap<>();
		param.put("name", "test");
		param.put("password", "test");
		param.put("myVersion", 100L);
		param.put("id", 100);
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUser(param);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
		
	}
	
	@Test(expected = BindingException.class)
	public void updateUserErrorTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUserError("test", "test", 100L, 100);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	}
	
	@Test
	public void updateUserNoVersionLockerTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		Integer result = userMapper.updateUserNoVersionLocker(user);
		Assert.assertEquals(1L, Long.parseLong(result + ""));

	}
	
	@Test
	public void updateUserListTest() {
		UserMapper2 userMapper = sqlSession.getMapper(UserMapper2.class);
		List<User2> userlist = new ArrayList<User2>();
		
		User2 user2 = new User2();
		user2.setId(101);
		user2.setName("test");
		user2.setPassword("test");
		user2.setMyVersion(100L);
		
		
		userlist.add(user);
		userlist.add(user2);
		
		Integer result = userMapper.updateUserList(userlist);
		Assert.assertEquals(1L, Long.parseLong(result + ""));
	
	}
	
	
	
}
