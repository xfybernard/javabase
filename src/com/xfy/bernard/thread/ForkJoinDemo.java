package com.xfy.bernard.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

	static class CountTask extends RecursiveTask<Long> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1859505840561404151L;
		static final int threshold = 100000000; // 每个子任务处理100000000个数的操作
		private long start;
		private long end;

		public CountTask(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			long sum = 0;
			boolean canCompute = (end - start) <= threshold;
			if (canCompute) {
				for (long i = start; i <= end; i++) {
					sum = sum + i;
				}
			} else {
				long t = end - start + 1;
				long threadcount = t % threshold == 0 ? t / threshold : t / threshold + 1;
				System.out.println(String.format("构建%d个子任务进行计算", threadcount));
				// 构建子任务
				for (long i = 0; i < threadcount; i++) {
					long tstart = i * threshold + 1;
					long tend = (i + 1) * threshold > end ? end : (i + 1) * threshold;
					CountTask task = new CountTask(tstart, tend);
					task.fork();
					long result = task.join();
					System.out.println(String.format("执行子任务[%d+%d]结果为[%d]", tstart, tend, result));
					sum = sum + result;
				}
			}
			return sum;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinPool fjl = new ForkJoinPool();
		long start = 1l, end = 1000000000l;

		CountTask task = new CountTask(start, end);
		Long beginTime = System.currentTimeMillis();
		Future<Long> result = fjl.submit(task);
		System.out.println(
				String.format("计算: %d+%d=%d 耗时:%s", start, end, result.get(), System.currentTimeMillis() - beginTime));
	}
}
