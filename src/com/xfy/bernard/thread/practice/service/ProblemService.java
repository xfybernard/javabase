package com.xfy.bernard.thread.practice.service;

import com.xfy.bernard.thread.practice.aim.ProblemBank;

public class ProblemService {
	public static String makeProblem(Integer problemId) {
		return BaseProblemService.handleProblem(problemId, ProblemBank.getProblem(problemId).getContent());
	}
}
