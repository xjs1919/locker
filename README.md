# MyBatis乐观锁插件 #

### MyBatis Optimistic Locker Plugin ###

[![Build Status](https://travis-ci.org/mybatis/mybatis-3.svg?branch=master)](https://travis-ci.org/mybatis/mybatis-3)
[![Coverage Status](https://coveralls.io/repos/mybatis/mybatis-3/badge.svg?branch=master&service=github)](https://coveralls.io/github/mybatis/mybatis-3?branch=master)
[![Dependency Status](https://www.versioneye.com/user/projects/56199c04a193340f320005d3/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56199c04a193340f320005d3)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis/mybatis)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Stack Overflow](http://img.shields.io/:stack%20overflow-mybatis-brightgreen.svg)](http://stackoverflow.com/questions/tagged/mybatis)
[![Project Stats](https://www.openhub.net/p/mybatis/widgets/project_thin_badge.gif)](https://www.openhub.net/p/mybatis)

![mybatis](http://mybatis.github.io/images/mybatis-logo.png)

----------

## Document: ##
	
----------

	描述：本插件主要是为了给Update语句自动生成带有version的乐观锁。

----------
### 1. 使用方式：在mybatis配置文件中加入如下配置，就完成了。 ###
	<plugins>
		<plugin interceptor="com.chrhc.mybatis.locker.interceptor.OptimisticLocker"/>
	</plugins>

----------

### 2. 对插件配置的说明： ###
	
上面对插件的配置默认数据库的乐观锁列对应的Java属性为version。这里可以自定义属性命，例如：

	<plugins>
		<plugin interceptor="com.chrhc.mybatis.locker.interceptor.OptimisticLocker">
			<property name="versionColumn" value="xxx"/><!--数据库的列名-->
			<property name="versionField" value="xxx"/> <!--java字段名-->
		</plugin>
	</plugins>

----------

### 3. 效果： ###
> 之前：**update user set name = ?, password = ?  where id = ?**

> 之后：**update user set name = ?, password = ?, version = version+1 where id = ? and version = ?**

----------


### 4. 对version的值的说明： ###
	1、当PreparedStatement获取到version值之后，插件内部会自动自增1。
	2、乐观锁的整个控制过程对用户而言是透明的，这和Hibernate的乐观锁很相似，用户不需要关心乐观锁的值。

----------
### 5.插件原理描述： ###
	插件通过拦截mybatis执行的update语句，在原有sql语句基础之上增加乐观锁标记，比如，原始sql为：
	update user set name = ?, password = ? where id = ?，
	那么用户不需要修改sql语句，在插件的帮助之下，会自动将上面的sql语句改写成为：
	update user set name = ?, password = ?, version = version + 1 where id = ? and version = ?，
	形式，用户也不用关心version前后值的问题，所有的动作对用户来说是透明的，由插件自己完成这些功能。
----------


### 6.默认约定： ###
	1、本插件拦截的update语句的Statement都是PreparedStatement，仅针对这种方式的sql有效；
	2、mapper.xml的<update>标签必须要与接口Mapper的方法对应上，也就是使用mybatis推荐的方式，
	   但是多个接口可以对应一个mapper.xml的<update>标签；
	3、本插件不会对sql的结果做任何操作，sql本身应该返回什么就是什么；
	4、插件默认拦截所有update语句，如果用户对某个update不希望有乐观锁控制，那么在对应的mapper接口
	   方法上面增加@VersionLocker(false)或者@VersionLocker(value = false),
	   这样插件就不会对这个update做任何操作，等同于没有本插件；
	5、本插件目前暂时不支持批量更新的乐观锁，原因是由于批量更新在实际开发中应用场景不多，另外批量更新乐观锁开发难度比较大；
	6、Mapper接口的参数类型必须和传入的实际类型保持一致，这是由于在JDK版本在JDK8以下没有任何方法能获取接口的参数列表名称，
	   因此，插件内部是使用参数类型和参数作为映射来匹配方法签名的；

----------


### 7.关于插件： ###
	如果您有什么建议或者意见，欢迎留言，也欢迎pull request，作者会将你优秀的思想加入到插件里面来，为其他人更好的解决问题。

----------

### 8.关于作者： ###
	作者QQ：342252328
	作者邮箱：342252328@qq.com
