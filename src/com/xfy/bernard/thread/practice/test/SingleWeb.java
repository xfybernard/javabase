package com.xfy.bernard.thread.practice.test;

import java.util.List;

import com.xfy.bernard.thread.practice.aim.MakeSrcDoc;
import com.xfy.bernard.thread.practice.aim.ProblemBank;
import com.xfy.bernard.thread.practice.service.DocService;
import com.xfy.bernard.thread.practice.vo.PendingDocVo;

public class SingleWeb {
	public static void main(String[] args) {
		System.out.println("��⿪ʼ��ʼ��...........");
		ProblemBank.init();
		System.out.println("����ʼ����ɡ�");
		List<PendingDocVo> docList = MakeSrcDoc.makeDoc(2);
		long startTotal = System.currentTimeMillis();
		for (PendingDocVo doc : docList) {
			System.out.println("��ʼ�����ĵ���" + doc.getDocName() + ".......");
			long start = System.currentTimeMillis();
			String localName = DocService.generateDoc(doc);
			System.out.println("�ĵ�" + localName + "���ɺ�ʱ��" + (System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			String remoteUrl = DocService.uploadDoc(localName);
			System.out.println("���ϴ���[" + remoteUrl + "]��ʱ��" + (System.currentTimeMillis() - start) + "ms");
		}
		System.out.println("����ʱ��" + (System.currentTimeMillis() - startTotal) + "ms");
	}
}
