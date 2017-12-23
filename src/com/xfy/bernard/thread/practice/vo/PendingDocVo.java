package com.xfy.bernard.thread.practice.vo;

import java.util.List;

/**
 * 待处理文档实体类
 * 
 * @author Administrator
 *
 */
public class PendingDocVo {
	// 生成文档后的名称
	private final String docName;
	// 需要处理的题目集合
	private final List<Integer> problemVoList;

	public PendingDocVo(String docName, List<Integer> problemVoList) {
		super();
		this.docName = docName;
		this.problemVoList = problemVoList;
	}

	public String getDocName() {
		return docName;
	}

	public List<Integer> getProblemVoList() {
		return problemVoList;
	}
}
