package br.com.wlm.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.wlm.domain.control.ControlJob;
import br.com.wlm.domain.entity.Job;
import br.com.wlm.domain.entity.Scheduler;
import br.com.wlm.domain.exception.JobInvalidoException;
import br.com.wlm.services.SchedulerService;

public class SchedulerServiceImpl implements SchedulerService {

	private final DateTimeFormatter FORAMTTER_WITH_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


	@Override
	public List<List<Integer>> listarIdsJobsValidos(Scheduler scheduler) {
		List<Job> jobsInvalidos = scheduler.getJobs().stream()
				.filter((j) -> j.getDataMaximaExecucao().isBefore(scheduler.getInicioJanela())
						|| j.getDataMaximaExecucao().isAfter(scheduler.getFimJanela()))
				.collect(Collectors.toList());
		if(!jobsInvalidos.isEmpty())
			throw new JobInvalidoException(
					formatarMensagemJobDataMaximaInvalida(scheduler.getInicioJanela(), scheduler.getFimJanela(), 
							jobsInvalidos.stream().map(Job::getId).collect(Collectors.toList())));
		return organizarJobsEPegarIds(scheduler.getJobs()
				.stream()
				.collect(Collectors.toList()));
	}
	
	
	private List<List<Integer>> organizarJobsEPegarIds(List<Job> jobs) {
		List<ControlJob> controlJobList = new ArrayList<>();
		jobs.forEach((itJob) -> {
			if(controlJobList.isEmpty())
				controlJobList.add(ControlJob.builder()
						.jobs(Collections.singletonList(itJob))
						.build());
			else {
				List<ControlJob> controlJobsIncompletos = controlJobList.stream()
						.filter((f) -> !f.estaCompleto()).collect(Collectors.toList());
				for(ControlJob controlJob: controlJobsIncompletos) {
					if(!controlJob.adiconarJob(itJob)) {
						controlJobList.add(ControlJob.builder()
								.jobs(Collections.singletonList(itJob))
								.build());
						break;
					}
					
				}
			}
		});
		return controlJobList.stream().map(ControlJob::listarIdJobs).collect(Collectors.toList());
	}
	
	private String formatarMensagemJobDataMaximaInvalida(LocalDateTime inicioJanela, LocalDateTime fimJanela, List<Integer> jobIds) {
		final String inicioJanelaFormatada = inicioJanela.format(FORAMTTER_WITH_PATTERN);
		final String fimJanaleaFormatada = fimJanela.format(FORAMTTER_WITH_PATTERN);
		final String mensagemComExpressao = "A data máxima de execução de um job "
				+ "não pode ser anterior ao inicio da janaela: %1$s e também não pode ser posterior ao fim da janela: %2$s. Id Jobs invalidos: %3$s";
		return String.format(mensagemComExpressao, inicioJanelaFormatada, fimJanaleaFormatada, jobIds);
	}

}
