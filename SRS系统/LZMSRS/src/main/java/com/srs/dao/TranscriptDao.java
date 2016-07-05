package com.srs.dao;

import java.util.List;

import com.srs.model.TranscriptEntry;

public interface TranscriptDao {
	/**
	 * 获取所有的Transcript对象
	 * @return
	 */
	public List<TranscriptEntry> getTranscript();
}
