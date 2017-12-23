package com.xfy.bernard.thread.practice.vo;

/**
 * 题目在数据库中存放实体类
 * 
 * @author Administrator
 *
 */
public class ProblemDBVo {
	// 题目id
	private final int problemId;
	// 题目内容
	private final String content;
	// 题目sha摘要
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
