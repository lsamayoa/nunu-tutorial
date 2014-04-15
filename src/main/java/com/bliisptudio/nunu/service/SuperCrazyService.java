package com.bliisptudio.nunu.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.inject.Inject;

public class SuperCrazyService {
	
	@Inject
	private ExecutorService executor;
	
	public String getCrazy() {
		return "LOCO";
	}

	public Future<String> getCrazyAsync() {
		FutureTask<String> future = new FutureTask<>(new Callable<String>() {

			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return "LOCO";
			}
		});
		executor.execute(future);
		return future;
	}
}
