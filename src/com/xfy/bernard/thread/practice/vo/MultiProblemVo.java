package com.xfy.bernard.thread.practice.vo;

import java.util.concurrent.Future;

/**
 * �����̲߳���������ͬ����Ŀʱ, ���ش������Ŀ���
 * 
 * @author Administrator
 *
 */
public class MultiProblemVo {
	private final String problemText;// Ҫô������Ŀ�����Ľ���ı�;
	private final Future<ProblemCacheVo> probleFuture; // Ҫô�������ڴ�����Ŀ������

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
