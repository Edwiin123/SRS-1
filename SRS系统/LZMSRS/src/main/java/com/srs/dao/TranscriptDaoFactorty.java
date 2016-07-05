package com.srs.dao;

public class TranscriptDaoFactorty {
	public SectionDao createSectionDao() {
		return new SectionSqliDao();
	}

}
