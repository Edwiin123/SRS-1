package com.srs.dao;

import java.util.List;
import java.util.Map;

import com.srs.model.Professor;
import com.srs.model.ScheduleOfClasses;
import com.srs.model.Section;
import com.srs.model.Student;

public class SectionSqliDao implements SectionDao {

	@Override
	public List<Section> getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int inSection(Student s, int sectionno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ScheduleOfClasses getSchedule(int sectionno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Section getSectionByNo(int sectionno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getHasSectionNo(Student s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getHasSectionNo(Professor p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSectionOfStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> querySectionByPro(String ssn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addSection(String courseNo, String sectionNo, String week, String room, String seat, String time,
			String ssn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delSection(String sectionNo) {
		// TODO Auto-generated method stub

	}

}
