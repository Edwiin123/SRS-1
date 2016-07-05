package com.srs.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.srs.dao.SectionDao;
import com.srs.dao.TranscriptDao;
import com.srs.daoImp.SectionDaoImp;
import com.srs.daoImp.TranscriptDaoImp;
import com.srs.model.Course;
import com.srs.model.PlanOfStudy;
import com.srs.model.ScheduleOfClasses;
import com.srs.model.Section;
import com.srs.model.Student;
import com.srs.model.Transcript;
import com.srs.model.TranscriptEntry;
import com.srs.service.PlanService;
import com.srs.service.SectionService;
import com.srs.service.UserService;

import net.sf.json.JSONArray;

public class SectionAction extends ActionSupport {
	private static final long serialVersionUID = 5283293241313468644L;
	
	/**
	 * 前端交互使用的属性
	 */
	private Student s;
	private JSONArray root;
	
	/**
	 * 内部方法共用的属性
	 */
	SectionService service = new SectionService();
	HttpServletRequest request = ServletActionContext.getRequest();
	UserService ser = new UserService();
	
	/**
	 * Section类的display()放法的前端显示
	 */
	@Override
	public String execute() throws Exception {
		
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		List<Section> list = new ArrayList<Section>();
		Map<String,Object>  map = new HashMap<String,Object>();
		
		//1、初始化所有section
		list = service.getSections();
		for (Section section : list) {
			SectionDao dao = new SectionDaoImp();
			//2、初始化对应的ScheduleOfClasses
			ScheduleOfClasses soc = dao.getSchedule(section.getSectionNo());
			soc.addSection(section);
			section.setOfferedIn(soc);
			//3、调用section的显示方法
			map = section.display();
			//增加功能，如果是已经成功选课的课程可以选择退选，从数据库person表里面进行筛查，
			//如果发现已选课字段中有与当前section相同，那么在map中添加一个1，如果没有添加一个0
			//初始化Student，获取当前对象
			String ssn = request.getParameter("ssn");
			Student stu = (Student) ser.getPerson(ssn, 2);
			List<Integer> sList = getFromPerson(stu);
			for (Integer tempNo : sList) {
				if(section.getSectionNo()==tempNo){
					//已选，标记为1 
					map.put("mark",1);
					break;
				}else{
					//未选，标记为0
					map.put("mark",0);
				}
			}
			mapList.add(map);
		}
		root = JSONArray.fromObject(mapList);
		return SUCCESS;
	}
	
	/**
	 * 查看是否已选的功能
	 * @return 返回Section对象
	 */
	private List<Integer> getFromPerson(Student s){
		SectionDao dao = new SectionDaoImp();
		return dao.getHasSectionNo(s);
	}
	
	/**
	 * Section的enroll方法的前端显示
	 * 即 选课的核心方法
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	public String inSection() throws IOException {
		
		//1、初始化Student，获取当前对象
		String ssn = request.getParameter("ssn");
		String sectionno = request.getParameter("sectionno");
		s = (Student) ser.getPerson(ssn, 2);
		
		//2、初始化PlanOfStudy,获取当前对象的学习计划
		PlanOfStudy plan = new PlanOfStudy();
		PlanService pService = new PlanService();
		List<Course> list = pService.getCourses(s.getSsn());
		plan.setStudentOwner(s);
		plan.setCourses(list);
		//学生加入计划列表
		s.setPlan(plan);
		
		//3、初始化Transcript,获取当前对象的成绩表
		Transcript t = new Transcript(s);
		TranscriptDao dao = new TranscriptDaoImp();
		List<TranscriptEntry> tList = dao.getTranscript();
		for (TranscriptEntry transcriptEntry : tList) {
			if(transcriptEntry.getStudent().getSsn().equals(s.getSsn())) 	
				t.addTranscriptEntry(transcriptEntry);
		}
		Section section = service.getSectionByNo(Integer.parseInt(sectionno));
		//核心方法
		String msg = section.enroll(s).value();
		//如果加入成功-->在数据库中添加字段，否则不用 
		if(msg.equals("加入成功 ！  :o)")){
			try {
				Class clazz = Class.forName("com.srs.daoImp.UserDaoImp");
				Method m = clazz.getDeclaredMethod("addCourseToPerson",new Class[]{String.class,String.class});
				m.invoke(clazz.newInstance(),new String[]{ssn,sectionno});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		

		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(msg);

		return null;
	}
	
	/*	
 	public void addToPerson(String ssn,String sectionno){
		Connection conn = DBUtil.open();
		String sql = "update person set hassectionno = ? where ssn=?";
		String temp = "select hassectionno from person where ssn = ?";
		String var = "";
		try {
			PreparedStatement p = conn.prepareStatement(temp);
			p.setString(1, ssn);
			ResultSet rs = p.executeQuery();
			if(rs.next()){var = rs.getString("hassectionno");}
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, var+"，"+sectionno);
			pstmt.setString(2, ssn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn);
		}
	}
	 */
	
	public Student getS() {
		return s;
	}

	public void setS(Student s) {
		this.s = s;
	}

	public JSONArray getRoot() {
		return root;
	}

	public void setRoot(JSONArray root) {
		this.root = root;
	}
	
}
