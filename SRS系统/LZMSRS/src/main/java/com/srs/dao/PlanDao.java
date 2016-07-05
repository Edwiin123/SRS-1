package com.srs.dao;

import java.util.List;

import com.srs.model.Course;

public interface PlanDao {
	/**
	 * 根据学生获取学习计划
	 * 
	 * @param ssn
	 * @return
	 */
	public List<Course> getCourses(String ssn);

	/**
	 * 通过course获取课程
	 * 
	 * @param courseNo
	 * @return
	 */
	public Course getCourseByNo(String courseNo);

}
