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
	 * 生成文档
	 * 
	 * @param pdoc
	 * @return
	 */
	public static String generateDoc(PendingDocVo pdoc) {
		List<Integer> problemVoList = pdoc.getProblemVoList();
		StringBuffer sbf = new StringBuffer();
		System.out.println(String.format("文档【%s】需要生成题目:%d,请耐心等待完成", pdoc.getDocName(), pdoc.getProblemVoList().size()));
		for (Integer problemVoId : problemVoList) {
			String textResult = ProblemService.makeProblem(problemVoId);
			sbf.append(textResult);
		}
		return "complete_" + System.currentTimeMillis() + "_" + pdoc.getDocName() + ".pdf";
	}

	/**
	 * 优化后的生成文档(缓存题目的处理结果)
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
		System.out.println(String.format("文档【%s】需要生成题目:%d,请耐心等待完成", pdoc.getDocName(), pdoc.getProblemVoList().size()));
		// 对题目处理的缓存
		for (Integer problemVoId : problemVoList) {
			MultiProblemVo mpvo = ProblemNewService.makeProblem(problemVoId);
			problemResult.put(problemVoId, mpvo);
		}

		// 依照文档中题目的顺序,依次获取每个题目的处理结果
		for (Integer problemId : problemVoList) {
			String problemText = problemResult.get(problemId).getProblemText();
			if (StringUtils.isBlank(problemText)) {
				sbf.append(problemResult.get(problemId).getProbleFuture().get());
			} else {
				sbf.append(problemText);
			}
		}

		// 返回生成文档的路径
		return "complete_" + System.currentTimeMillis() + "_" + pdoc.getDocName() + ".pdf";
	}

	/**
	 * 上传文档至网络
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
