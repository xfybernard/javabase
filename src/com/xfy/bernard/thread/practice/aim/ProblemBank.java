package com.xfy.bernard.thread.practice.aim;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.xfy.bernard.thread.practice.vo.ProblemDBVo;

/**
 * ����ģ��
 * 
 * @author Administrator
 *
 */
public class ProblemBank {
	private static ConcurrentHashMap<Integer, ProblemDBVo> pbMap = new ConcurrentHashMap<>();
	private static ScheduledExecutorService updateProblemBank = Executors.newScheduledThreadPool(1);

	// ��ʼ�����
	public static void init() {
		for (int i = 0; i < Constants.PROBLEM_BANK_COUNT; i++) {
			pbMap.put(i, newProblem(i));
		}
		updateProblemTimer();
	}

	private static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	private static ProblemDBVo newProblem(Integer id) {
		String content = getRandomString(800);
		String sha = EncryptTools.EncryptBySHA1(content);
		ProblemDBVo vo = new ProblemDBVo(id, content, sha);
		return vo;
	}

	public static ProblemDBVo getProblem(int key) {
		BusiMock.buisness(20);
		return pbMap.get(key);
	}

	public static String getProblemSHA(int key) {
		BusiMock.buisness(10);
		return pbMap.get(key).getSha();
	}

	static class UpdateProblemTask implements Runnable {

		@Override
		public void run() {
			Random random = new Random();
			int problemId = random.nextInt(Constants.PROBLEM_BANK_COUNT);
			String problemContent = getRandomString(800);
			ProblemDBVo pbmVoUpdate = new ProblemDBVo(problemId, problemContent,
					EncryptTools.EncryptBySHA1(problemContent));
			pbMap.put(problemId, pbmVoUpdate);
			// System.out.println(String.format("��Ŀ��%s���Ѹ��£���", problemId));
		}
	}

	/**
	 * ��ʱ�������
	 */
	public static void updateProblemTimer() {
		updateProblemBank.scheduleAtFixedRate(new UpdateProblemTask(), 15, 5, TimeUnit.SECONDS);
	}

	public static void closeUpdateProblemTimer() {
		updateProblemBank.shutdown();
	}
}
