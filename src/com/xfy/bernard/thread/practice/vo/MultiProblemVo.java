package com.xfy.bernard.thread.practice.vo;

import java.util.concurrent.Future;

/**
 * 并个线程并发处理相同的题目时, 返回处理的题目结果
 * 
 * @author Administrator
 *
 */
public class MultiProblemVo {
	private final String problemText;// 要么就是题目处理后的结果文本;
	private final Future<ProblemCacheVo> probleFuture; // 要么就是正在处理题目的任务

	public MultiProblemVo(Future<ProblemCacheVo> probleFuture) {
		this.probleFuture = probleFuture;
		this.problemText = null;
	}

	public MultiProblemVo(String problemText) {
		this.problemText = problemText;
		this.probleFuture = null;
	}

	public String getProblemText() {
		return problemText;
	}

	public Future<ProblemCacheVo> getProbleFuture() {
		return probleFuture;
	}

}
