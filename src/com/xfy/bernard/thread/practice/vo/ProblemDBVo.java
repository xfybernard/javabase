package com.xfy.bernard.thread.practice.vo;

/**
 * ��Ŀ�����ݿ��д��ʵ����
 * 
 * @author Administrator
 *
 */
public class ProblemDBVo {
	// ��Ŀid
	private final int problemId;
	// ��Ŀ����
	private final String content;
	// ��ĿshaժҪ
	private final String sha;

	public ProblemDBVo(int problemId, String content, String sha) {
		this.problemId = problemId;
		this.content = content;
		this.sha = sha;
	}

	public int getProblemId() {
		return problemId;
	}

	public String getContent() {
		return content;
	}

	public String getSha() {
		return sha;
	}
}
