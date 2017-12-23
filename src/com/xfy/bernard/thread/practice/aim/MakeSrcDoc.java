package com.xfy.bernard.thread.practice.aim;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.xfy.bernard.thread.practice.vo.PendingDocVo;

public class MakeSrcDoc {
	/**
	 * 生成待处理文档
	 * 
	 * @param docCount
	 * @return
	 */
	public static List<PendingDocVo> makeDoc(int docCount) {
		Random r = new Random();
		Random rProblemCount = new Random();
		List<PendingDocVo> docList = new LinkedList<>();// 文档列表
		for (int i = 0; i < docCount; i++) {
			int problemCount = rProblemCount.nextInt(60) + 60;
			List<Integer> problemList = new LinkedList<>();
			for (int j = 0; j < problemCount; j++) {
				int problemId = r.nextInt(Constants.PROBLEM_BANK_COUNT);
				if (!problemList.contains(problemId)) {
					problemList.add(problemId);
				} else {
					--j;
				}
			}
			PendingDocVo pdco = new PendingDocVo("pending_" + i, problemList);
			docList.add(pdco);
		}
		return docList;
	}
}
