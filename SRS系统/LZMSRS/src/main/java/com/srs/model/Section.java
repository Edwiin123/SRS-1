package com.srs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 班次类，重要
 * 
 * @author lzm
 *
 */
public class Section {

	private int sectionNo;
	private String dayOfWeek;
	private String timeOfDay;
	private String room;
	private int seatingCapacity;
	private Course representedCourse;
	private ScheduleOfClasses offeredIn;
	private Professor instructor;

	private HashMap<String, Student> enrolledStudents;

	private HashMap<Student, TranscriptEntry> assignedGrades;

	public Section(int sNo, String day, String time, Course course, String room, int capacity) {
		setSectionNo(sNo);
		setDayOfWeek(day);
		setTimeOfDay(time);
		setRepresentedCourse(course);
		setRoom(room);
		setSeatingCapacity(capacity);

		setInstructor(null);

		enrolledStudents = new HashMap<String, Student>();
		assignedGrades = new HashMap<Student, TranscriptEntry>();
	}

	// 学生登记进入某个班次,选课核心方法
	public EnrollmentStatus enroll(Student s) {
		// 是否在学生学习计划中
		if (!inPlan(s)) {
			return EnrollmentStatus.notInPlan;
		}
		Transcript transcript = s.getTranscript();
		// 是否修已经修过相同课程，并及格
		if (s.isCurrentlyEnrolledInSimilar(this) || transcript.verifyCompletion(this.getRepresentedCourse())) {
			return EnrollmentStatus.prevEnroll;
		}

		// 先修课程是否已完成
		Course c = this.getRepresentedCourse();
		if (c.hasPrerequisites()) {
			// 遍历所有的先修课
			for (Course pre : c.getPrerequisites()) {
				if (!transcript.verifyCompletion(pre)) {
					return EnrollmentStatus.prereq;
				}
			}
		}
		// 是否有空余名额
		if (!this.confirmSeatAvailability()) {
			return EnrollmentStatus.secFull;
		}
		enrolledStudents.put(s.getSsn(), s);
		s.addSection(this);

		return EnrollmentStatus.success;
	}

	// 判断学生所选班次是否在计划中
	private boolean inPlan(Student s) {
		for (Course course : s.getPlan().getCourses()) {
			if (this.getRepresentedCourse().getCourseNo().equals(course.getCourseNo())) {
				return true;
			}
		}
		return false;
	}

	// 判断作为容量是否足够
	private boolean confirmSeatAvailability() {
		if (enrolledStudents.size() < getSeatingCapacity())
			return true;
		else
			return false;
	}

	// 去除某个学生
	public boolean drop(Student s) {

		if (!s.isEnrolledIn(this))
			return false;
		else {

			enrolledStudents.remove(s.getSsn());

			s.dropSection(this);
			return true;
		}
	}

	// 获得总人数
	public int getTotalEnrollment() {
		return enrolledStudents.size();
	}

	/**
	 * 获取某个学生的成绩
	 * 
	 * @param s
	 * @return
	 */
	public String getGrade(Student s) {
		String grade = null;

		TranscriptEntry te = assignedGrades.get(s);
		if (te != null) {
			grade = te.getGrade();
		}

		return grade;
	}

	/**
	 * 提交某个学生的成绩
	 * 
	 * @param s
	 * @param grade
	 * @return
	 */
	public boolean postGrade(Student s, String grade) {
		// 首先由TanscriptEntry验证成绩是否通过
		if (!TranscriptEntry.validateGrade(grade))
			return false;
		if (assignedGrades.get(s) != null)
			return false;
		TranscriptEntry te = new TranscriptEntry(s, grade, this);
		assignedGrades.put(s, te);

		return true;
	}

	public boolean isSectionOf(Course c) {
		if (c == representedCourse)
			return true;
		else
			return false;
	}

	/**
	 * 显示当前的section
	 * 
	 * @return
	 */
	public Map<String, Object> display() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();

		map.put("Semester", getOfferedIn().getSemester());
		map.put("CourseNo", getRepresentedCourse().getCourseNo());
		map.put("SectionNo", getSectionNo());
		map.put("Offered", getDayOfWeek() + " at " + getTimeOfDay());
		map.put("InRoom", getRoom());
		if (getInstructor() != null)
			map.put("Professor", getInstructor().getName());
		map.put("Total", getTotalEnrollment());
		for (Student s : enrolledStudents.values()) {
			list.add(s.getName());
		}
		map.put("NameList", list);
		return map;
	}

	@Override
	public String toString() {
		return getRepresentedCourse().getCourseNo() + " - " + getSectionNo() + " - " + getDayOfWeek() + " - "
				+ getTimeOfDay();
	}

	// ---- get set 方法 ----
	public void setSectionNo(int no) {
		sectionNo = no;
	}

	public int getSectionNo() {
		return sectionNo;
	}

	public void setDayOfWeek(String day) {
		dayOfWeek = day;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setTimeOfDay(String time) {
		timeOfDay = time;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setInstructor(Professor prof) {
		instructor = prof;
	}

	public Professor getInstructor() {
		return instructor;
	}

	public void setRepresentedCourse(Course c) {
		representedCourse = c;
	}

	public Course getRepresentedCourse() {
		return representedCourse;
	}

	public void setRoom(String r) {
		room = r;
	}

	public String getRoom() {
		return room;
	}

	public void setSeatingCapacity(int c) {
		seatingCapacity = c;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setOfferedIn(ScheduleOfClasses soc) {
		offeredIn = soc;
	}

	public ScheduleOfClasses getOfferedIn() {
		return offeredIn;
	}
}
