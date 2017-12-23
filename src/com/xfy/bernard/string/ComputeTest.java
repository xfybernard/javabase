package com.xfy.bernard.string;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComputeTest {
	static final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
		@Override
		public BigInteger[] compute(BigInteger arg) throws InterruptedException {
			return factor(arg);
		}
	};

	private static BigInteger[] factor(BigInteger arg) {
		System.out.println("计算公约数");
		long t = arg.longValue();
		List<BigInteger> result = new ArrayList<>();
		long i = t - 1;
		while (t != 1 && i > 1) {
			if (t % i == 0) {
				t = t / i;
				result.add(BigInteger.valueOf(i));
			} else {
				i = i - 1;
			}
		}
		if (t == arg.longValue()) {
			result.add(BigInteger.ONE);
			result.add(arg);
		}
		return result.toArray(new BigInteger[0]);
	}

	public static void main(String[] args) throws InterruptedException {
		/*
		 * for (int i = 2; i < 100; i++) { BigInteger[] result =
		 * factor(BigInteger.valueOf(i)); StringBuilder sbd = new
		 * StringBuilder().append(i).append("="); for (BigInteger r : result) {
		 * sbd.append(r).append("*"); } System.out.println(sbd.substring(0,
		 * sbd.length() - 1)); }
		 */
		Memoizer<BigInteger, BigInteger[]> cache = new Memoizer<BigInteger, BigInteger[]>(c);
		for (int i = 1; i < 100; i++) {
			BigInteger[] result = cache.compute(BigInteger.valueOf(15));
		}
	}
}
