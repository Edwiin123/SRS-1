package com.srs.dao;

import java.util.List;
import java.util.Map;

import com.srs.model.Professor;
import com.srs.model.ScheduleOfClasses;
import com.srs.model.Section;
import com.srs.model.Student;

public interface SectionDao {
	/**
	 * 从数据库中获取所有的section
	 * 
	 * @return
	 */
	public List<Section> getSections();

	/**
	 * 某个学生将指定的section加入
	 * 
	 * @param s
	 * @param sectionno
	 * @return
	 */
	public int inSection(Student s, int sectionno);

	/**
	 * 获取scheduleOfClass
	 * 
	 * @param sectionno
	 * @return
	 */
	public ScheduleOfClasses getSchedule(int sectionno);

	/**
	 * 获取指定的section
	 * 
	 * @param sectionno
	 * @return
	 */
	public Section getSectionByNo(int sectionno);

	/**
	 * 获取某个学生已选的section
	 * 
	 * @param s
	 * @return
	 */
	public List<Integer> getHasSectionNo(Student s);

	/**
	 * 获取某个教授开设的section
	 * 
	 * @param p
	 * @return
	 */
	public List<Integer> getHasSectionNo(Professor p);

	/**
	 * 获取所有学生编号与section编号的对应关系，一对多
	 * 
	 * @return
	 */
	public Map<String, String> getSectionOfStudent();

	/**
	 * 教授查询自己教授了哪些课
	 * 
	 * @param ssn
	 * @return
	 */
	public List<String> querySectionByPro(String ssn);

	/**
	 * 更需section
	 * 
	 * @param courseNo
	 * @param sectionNo
	 * @param week
	 * @param room
	 * @param seat
	 * @param time
	 * @param ssn
	 * @return
	 */
	public int updateSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn);

	/**
	 * 添加课程
	 * 
	 * @param courseNo
	 * @param sectionNo
	 * @param week
	 * @param room
	 * @param seat
	 * @param time
	 * @param ssn
	 */
	public void addSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn);

	/**
	 * 删除课程
	 * 
	 * @param sectionNo
	 */
	public void delSection(String sectionNo);

}
