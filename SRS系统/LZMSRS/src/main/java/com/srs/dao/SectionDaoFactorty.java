package com.srs.dao;

public class SectionDaoFactorty {
	public SectionDao createSectionDao() {
		return new SectionSqliDao();
	}

}
