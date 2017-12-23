package com.xfy.bernard.thread.practice.vo;

/**
 * 题目缓存对象
 * 
 * @author Administrator
 *
 */
public class ProblemCacheVo {
	private String processedContent;
	private String problemSha;

	public ProblemCacheVo() {
		super();
	}

	public ProblemCacheVo(String processedContent, String problemSha) {
		super();
		this.processedContent = processedContent;
		this.problemSha = problemSha;
	}

	public String getProcessedContent() {
		return processedContent;
	}

	public void setProcessedContent(String processedContent) {
		this.processedContent = processedContent;
	}

	public String getProblemSha() {
		return problemSha;
	}

	public void setProblemSha(String problemSha) {
		this.problemSha = problemSha;
	}
}
