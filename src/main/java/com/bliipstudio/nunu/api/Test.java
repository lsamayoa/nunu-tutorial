package com.bliipstudio.nunu.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.bliisptudio.nunu.service.SuperCrazyService;

@Path("/test")
public class Test {
	@Inject
	private SuperCrazyService crazy;

	@GET
	public String getTest(){
		return "It Works";
	}
	
	@Path("/crazy")
	@GET
	public String getCrazy(){
		return crazy.getCrazy();
	}
}
