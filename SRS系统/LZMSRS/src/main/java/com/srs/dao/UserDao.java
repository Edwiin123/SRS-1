package com.srs.dao;

import com.srs.model.Person;
import com.srs.model.User;

public interface UserDao {
	/**
	 * 登录
	 * @param username 账号
	 * @param password 密码
	 * @return
	 */
	public User login(String username,String password);
	/**
	 * 获取person对象
	 * @param ssn 学号
	 * @param type professor或student
	 * @return
	 */
	public Person getPerson(String ssn,int type);
	/**
	 * 向person表中持有的所选课的数据中添加已选课程
	 * 
	 * @param ssn  学号
	 * @param sectionno   班次编号
	 * @return 返回受影响的数据库行数
	 */
	public int addCourseToPerson(String ssn,String sectionno);
}
