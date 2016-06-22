package com.mook.locker.misc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mook.locker.annotation.VersionLocker;
import com.mook.locker.misc.domain.User2;

public interface UserMapper2 {
	
	// 参数为POJO对象方式(推荐使用方式1)
	Integer updateUser(User2 user);
	
	// 参数为单个参数方式(推荐使用方式2)
	Integer updateUser(@Param("name") String name, @Param("password") String password, @Param("myVersion") Long version, @Param("id") Integer id);
	
	// 参数为POJO对象方式(推荐使用方式1)
	Integer updateUser3(@Param("user") User2 user);
		
	// 参数为Map方式(不推荐使用方式)
	Integer updateUser(Map<Object, Object> user);
	
	// 单个参数未带@Param，报错(严重不推荐使用方式)
	Integer updateUserError(String name, String password, Long version, Integer id);
	
	// 不参与乐观锁控制
	@VersionLocker(false)
	Integer updateUserNoVersionLocker(User2 user);

	// 重置数据库数据
	@VersionLocker(false)
	void resetData(User2 user);
	
	// 重置数据库数据
	@VersionLocker(false)
	void initData(User2 user);
	
	List<User2> selectAllTeacher();
	
	
	public int updateUserList(List<User2> users);
	
}
