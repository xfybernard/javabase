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
 * 多线程并行处理生成doc，上传doc
 * 
 * @author Administrator
 *
 */
public class RpcMode {
	// 处理文档线程池
	private static ExecutorService handlerService = Executors.newFixedThreadPool(Constants.THREAD_COUNT_BASE * 2);
	// 上传文档线程池
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
			System.out.println("文档" + localName + "生成耗时：" + (System.currentTimeMillis() - start) + "ms");
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
			System.out.println("已上传至[" + uploadPath + "]耗时：" + (System.currentTimeMillis() - start) + "ms");
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
		System.out.println("题库开始初始化...........");
		ProblemBank.init();
		System.out.println("题库初始化完成。");
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
		System.out.println("共耗时：" + (System.currentTimeMillis() - startTotal) + "ms");
		handlerService.shutdown();
		uploadService.shutdown();
		ProblemBank.closeUpdateProblemTimer();
	}
}
