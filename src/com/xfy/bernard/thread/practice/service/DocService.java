package com.xfy.bernard.thread.practice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;

import com.xfy.bernard.thread.practice.aim.BusiMock;
import com.xfy.bernard.thread.practice.vo.MultiProblemVo;
import com.xfy.bernard.thread.practice.vo.PendingDocVo;

public class DocService {

	/**
	 * �����ĵ�
	 * 
	 * @param pdoc
	 * @return
	 */
	public static String generateDoc(PendingDocVo pdoc) {
		List<Integer> problemVoList = pdoc.getProblemVoList();
		StringBuffer sbf = new StringBuffer();
		System.out.println(String.format("�ĵ���%s����Ҫ������Ŀ:%d,�����ĵȴ����", pdoc.getDocName(), pdoc.getProblemVoList().size()));
		for (Integer problemVoId : problemVoList) {
			String textResult = ProblemService.makeProblem(problemVoId);
			sbf.append(textResult);
		}
		return "complete_" + System.currentTimeMillis() + "_" + pdoc.getDocName() + ".pdf";
	}

	/**
	 * �Ż���������ĵ�(������Ŀ�Ĵ�����)
	 * 
	 * @param pdoc
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static String generateDocNew(PendingDocVo pdoc) throws InterruptedException, ExecutionException {
		List<Integer> problemVoList = pdoc.getProblemVoList();
		Map<Integer, MultiProblemVo> problemResult = new HashMap<>();
		StringBuffer sbf = new StringBuffer();
		System.out.println(String.format("�ĵ���%s����Ҫ������Ŀ:%d,�����ĵȴ����", pdoc.getDocName(), pdoc.getProblemVoList().size()));
		// ����Ŀ����Ļ���
		for (Integer problemVoId : problemVoList) {
			MultiProblemVo mpvo = ProblemNewService.makeProblem(problemVoId);
			problemResult.put(problemVoId, mpvo);
		}

		// �����ĵ�����Ŀ��˳��,���λ�ȡÿ����Ŀ�Ĵ�����
		for (Integer problemId : problemVoList) {
			String problemText = problemResult.get(problemId).getProblemText();
			if (StringUtils.isBlank(problemText)) {
				sbf.append(problemResult.get(problemId).getProbleFuture().get());
			} else {
				sbf.append(problemText);
			}
		}

		// ���������ĵ���·��
		return "complete_" + System.currentTimeMillis() + "_" + pdoc.getDocName() + ".pdf";
	}

	/**
	 * �ϴ��ĵ�������
	 * 
	 * @param docPath
	 * @return
	 */
	public static String uploadDoc(String docPath) {
		Random r = new Random();
		BusiMock.buisness(5000 + r.nextInt(400));
		String uploadUrl = "http://xxx.wanpan.com/upload/doc/%s";
		return String.format(uploadUrl, docPath);
	}
}
