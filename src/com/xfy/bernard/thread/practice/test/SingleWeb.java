package com.xfy.bernard.thread.practice.test;

import java.util.List;

import com.xfy.bernard.thread.practice.aim.MakeSrcDoc;
import com.xfy.bernard.thread.practice.aim.ProblemBank;
import com.xfy.bernard.thread.practice.service.DocService;
import com.xfy.bernard.thread.practice.vo.PendingDocVo;

public class SingleWeb {
	public static void main(String[] args) {
		System.out.println("题库开始初始化...........");
		ProblemBank.init();
		System.out.println("题库初始化完成。");
		List<PendingDocVo> docList = MakeSrcDoc.makeDoc(2);
		long startTotal = System.currentTimeMillis();
		for (PendingDocVo doc : docList) {
			System.out.println("开始处理文档：" + doc.getDocName() + ".......");
			long start = System.currentTimeMillis();
			String localName = DocService.generateDoc(doc);
			System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			String remoteUrl = DocService.uploadDoc(localName);
			System.out.println("已上传至[" + remoteUrl + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
		}
		System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");
	}
}
