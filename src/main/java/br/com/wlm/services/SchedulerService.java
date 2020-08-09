package br.com.wlm.services;

import java.util.List;

import br.com.wlm.domain.entity.Scheduler;

public interface SchedulerService {
	
	
	List<List<Integer>> listarIdsJobsValidos(Scheduler scheduler);

}
