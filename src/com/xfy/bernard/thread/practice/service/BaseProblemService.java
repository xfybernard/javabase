package com.xfy.bernard.thread.practice.service;

import java.util.Random;

import com.xfy.bernard.thread.practice.aim.BusiMock;

/**
 * 
 * @author Administrator
 * 
 *         ��Ŀ����Ļ�������,ģ�������Ŀ�ı�������ͼƬ�Ȳ����� ���ؽ�������ı�
 */
public class BaseProblemService {
	/**
	 * ����Ŀ���д���������ı�������ͼƬ�ȵȹ���
	 * 
	 * @param problemId
	 *            ��Ŀid
	 * @return ��Ŀ��������ı�
	 */
	public static String handleProblem(Integer problemId, String content) {
		Random r = new Random();
		BusiMock.buisness(450 + r.nextInt(100));
		return "CompleteProblem[id=" + problemId + " content=:" + content + "]";
	}
}
