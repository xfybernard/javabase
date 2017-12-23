package com.xfy.bernard.thread.practice.vo;

import java.util.List;

/**
 * �������ĵ�ʵ����
 * 
 * @author Administrator
 *
 */
public class PendingDocVo {
	// �����ĵ��������
	private final String docName;
	// ��Ҫ�������Ŀ����
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
