package com.xfy.bernard.thread.practice.service;

import java.util.Random;

import com.xfy.bernard.thread.practice.aim.BusiMock;

/**
 * 
 * @author Administrator
 * 
 *         题目处理的基础服务,模拟解析题目文本，下载图片等操作， 返回解析后的文本
 */
public class BaseProblemService {
	/**
	 * 对题目进行处理，如解析文本，下载图片等等工作
	 * 
	 * @param problemId
	 *            题目id
	 * @return 题目解析后的文本
	 */
	public static String handleProblem(Integer problemId, String content) {
		Random r = new Random();
		BusiMock.buisness(450 + r.nextInt(100));
		return "CompleteProblem[id=" + problemId + " content=:" + content + "]";
	}
}
