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
	// ����Ѿ��������Ŀ
	private static final ConcurrentMap<Integer, ProblemCacheVo> problemCache = new ConcurrentHashMap<>();
	// ������ڴ������Ŀ,��ֹ���������ͬ����Ŀ
	private static final ConcurrentMap<Integer, Future<ProblemCacheVo>> handerProblem = new ConcurrentHashMap<>();

	// ������Ŀ�̳߳�
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
				// ��ʾ��ǰû�б�������ڴ���ǰ��Ŀ
				future = ft;
				problemService.submit(ft);
				// System.out.println(String.format("��Ŀ��%s��������������,��ȴ����>>>>>>>>",
				// problemId));
			} else {
				// ��ʾ��ǰ�б���������ڴ���ǰ��Ŀ
				// System.out.println(String.format("�ո��������������ɲ����ڴ���
				// ��Ŀ��%s������,���������½�����", problemId));
			}
		} else {
			// System.out.println(String.format("��ǰ�Ѿ��������ڴ�������%s��", problemId));
		}
		return future;
	}

	/**
	 * ��������ʹ�� ,������Ŀ�Ĵ�����������Ŀ������
	 * 
	 * @param problemId
	 * @return
	 */
	public static MultiProblemVo makeProblem(Integer problemId) {
		ProblemCacheVo cacheVo = problemCache.get(problemId);
		if (cacheVo == null) {
			// System.out.println(String.format("��Ŀ��%s���ڻ����в�����,��Ҫ�½���������",
			// problemId));
			Future<ProblemCacheVo> future = getProblemFuture(problemId);
			return new MultiProblemVo(future);
		} else {
			String problemSha = ProblemBank.getProblemSHA(problemId);
			// ��Ŀ���ڻ��棬��δ�޸Ĺ�
			if (cacheVo.getProblemSha().equals(problemSha)) {
				// System.out.println(String.format("��Ŀ��%s���ڻ����е�����δ�����仯,����ֱ��ʹ��",
				// problemId));
				MultiProblemVo mpvo = new MultiProblemVo(cacheVo.getProcessedContent());
				return mpvo;
			} else {
				// System.out.println(String.format("��Ŀ��%s���ڻ����е������ѹ���,��Ҫ�½�����������»���",
				// problemId));
				Future<ProblemCacheVo> future = getProblemFuture(problemId);
				return new MultiProblemVo(future);
			}
		}
	}
}
