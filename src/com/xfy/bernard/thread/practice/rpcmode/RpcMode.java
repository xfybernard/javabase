package com.xfy.bernard.thread.practice.rpcmode;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xfy.bernard.thread.practice.aim.Constants;
import com.xfy.bernard.thread.practice.aim.MakeSrcDoc;
import com.xfy.bernard.thread.practice.aim.ProblemBank;
import com.xfy.bernard.thread.practice.service.DocService;
import com.xfy.bernard.thread.practice.vo.PendingDocVo;

/***
 * ���̲߳��д�������doc���ϴ�doc
 * 
 * @author Administrator
 *
 */
public class RpcMode {
	// �����ĵ��̳߳�
	private static ExecutorService handlerService = Executors.newFixedThreadPool(Constants.THREAD_COUNT_BASE * 2);
	// �ϴ��ĵ��̳߳�
	private static ExecutorService uploadService = Executors.newFixedThreadPool(Constants.THREAD_COUNT_BASE * 2);

	private static CompletionService<String> doCompletionService = new ExecutorCompletionService<>(handlerService);

	private static CompletionService<String> uploadCompletionService = new ExecutorCompletionService<>(uploadService);

	static class MakeDoc implements Callable<String> {

		private PendingDocVo pendingDocVo;

		public MakeDoc(PendingDocVo pendingDocVo) {
			this.pendingDocVo = pendingDocVo;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
			String localName = DocService.generateDocNew(pendingDocVo);
			System.out.println("�ĵ�" + localName + "���ɺ�ʱ��" + (System.currentTimeMillis() - start) + "ms");
			return localName;
		}
	}

	static class UploadDoc implements Callable<String> {
		private String docPath;

		public UploadDoc(String docPath) {
			this.docPath = docPath;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
			String uploadPath = DocService.uploadDoc(docPath);
			System.out.println("���ϴ���[" + uploadPath + "]��ʱ��" + (System.currentTimeMillis() - start) + "ms");
			return uploadPath;
		}
	}

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("��⿪ʼ��ʼ��...........");
		ProblemBank.init();
		System.out.println("����ʼ����ɡ�");
		List<PendingDocVo> docList = MakeSrcDoc.makeDoc(60);
		long startTotal = System.currentTimeMillis();
		for (int i = 0; i < docList.size(); i++) {
			doCompletionService.submit(new MakeDoc(docList.get(i)));
		}

		for (int i = 0; i < docList.size(); i++) {
			Future<String> handleResult = doCompletionService.take();
			uploadCompletionService.submit(new UploadDoc(handleResult.get()));
		}

		for (int i = 0; i < docList.size(); i++) {
			uploadCompletionService.take().get();
		}
		System.out.println("����ʱ��" + (System.currentTimeMillis() - startTotal) + "ms");
		handlerService.shutdown();
		uploadService.shutdown();
		ProblemBank.closeUpdateProblemTimer();
	}
}
