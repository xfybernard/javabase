package com.xfy.bernard.thread.practice.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import com.xfy.bernard.thread.practice.aim.Constants;
import com.xfy.bernard.thread.practice.aim.ProblemBank;
import com.xfy.bernard.thread.practice.vo.MultiProblemVo;
import com.xfy.bernard.thread.practice.vo.ProblemCacheVo;
import com.xfy.bernard.thread.practice.vo.ProblemDBVo;

public class ProblemNewService {
	// 存放已经处理的题目
	private static final ConcurrentMap<Integer, ProblemCacheVo> problemCache = new ConcurrentHashMap<>();
	// 存放正在处理的题目,防止多个任务处理同个题目
	private static final ConcurrentMap<Integer, Future<ProblemCacheVo>> handerProblem = new ConcurrentHashMap<>();

	// 处理题目线程池
	private static final ExecutorService problemService = Executors.newFixedThreadPool(Constants.THREAD_COUNT_BASE * 2);

	static class ProblemTask implements Callable<ProblemCacheVo> {

		private ProblemDBVo problemDBVo;

		private Integer problemId;

		public ProblemTask(ProblemDBVo problemDBVo, Integer problemId) {
			this.problemDBVo = problemDBVo;
			this.problemId = problemId;
		}

		@Override
		public ProblemCacheVo call() throws Exception {
			ProblemCacheVo cacheVo;
			try {
				cacheVo = new ProblemCacheVo();
				cacheVo.setProblemSha(problemDBVo.getSha());
				cacheVo.setProcessedContent(BaseProblemService.handleProblem(problemId, problemDBVo.getContent()));
				problemCache.put(problemId, cacheVo);
			} finally {
				handerProblem.remove(problemId);
			}
			return cacheVo;
		}
	}

	private static Future<ProblemCacheVo> getProblemFuture(Integer problemId) {
		Future<ProblemCacheVo> future = handerProblem.get(problemId);
		if (future == null) {
			ProblemDBVo problemDBVo = ProblemBank.getProblem(problemId);
			ProblemTask task = new ProblemTask(problemDBVo, problemId);
			FutureTask<ProblemCacheVo> ft = new FutureTask<>(task);
			future = handerProblem.putIfAbsent(problemId, ft);
			if (future == null) {
				// 表示当前没有别的任务在处理当前题目
				future = ft;
				problemService.submit(ft);
				// System.out.println(String.format("题目【%s】计算任务启动,请等待完成>>>>>>>>",
				// problemId));
			} else {
				// 表示当前有别的任务正在处理当前题目
				// System.out.println(String.format("刚刚有其它任务生成并正在处理
				// 题目【%s】计算,不必重新新建任务", problemId));
			}
		} else {
			// System.out.println(String.format("当前已经有任务在处理任务【%s】", problemId));
		}
		return future;
	}

	/**
	 * 供调用者使用 ,返回题目的处理结果或处理题目的任务
	 * 
	 * @param problemId
	 * @return
	 */
	public static MultiProblemVo makeProblem(Integer problemId) {
		ProblemCacheVo cacheVo = problemCache.get(problemId);
		if (cacheVo == null) {
			// System.out.println(String.format("题目【%s】在缓存中不存在,需要新建处理任务",
			// problemId));
			Future<ProblemCacheVo> future = getProblemFuture(problemId);
			return new MultiProblemVo(future);
		} else {
			String problemSha = ProblemBank.getProblemSHA(problemId);
			// 题目存在缓存，且未修改过
			if (cacheVo.getProblemSha().equals(problemSha)) {
				// System.out.println(String.format("题目【%s】在缓存中的数据未发生变化,可以直接使用",
				// problemId));
				MultiProblemVo mpvo = new MultiProblemVo(cacheVo.getProcessedContent());
				return mpvo;
			} else {
				// System.out.println(String.format("题目【%s】在缓存中的数据已过期,需要新建处理任务更新缓存",
				// problemId));
				Future<ProblemCacheVo> future = getProblemFuture(problemId);
				return new MultiProblemVo(future);
			}
		}
	}
}
