package br.com.wlm;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import br.com.wlm.domain.entity.Job;
import br.com.wlm.domain.entity.Scheduler;
import br.com.wlm.services.SchedulerService;
import br.com.wlm.services.impl.SchedulerServiceImpl;

public class App {
	public static void main(String[] args) {
		
		SchedulerService schedulerService = new SchedulerServiceImpl();
		Job job1 = Job.builder()
				.id(1)
				.descricao("Importação de arquivos de fundos")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,10,12,00,00))
				.tempoEstimadoEmHoras(6l)
				.build();
		Job job2 = Job.builder()
				.id(2)
				.descricao("Importação de dados da Base Legada")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,11,12,00,00))
				.tempoEstimadoEmHoras(2l)
				.build();
		Job job3 = Job.builder()
				.id(3)
				.descricao("Importação de dados de integração")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,11,8,00,00))
				.tempoEstimadoEmHoras(6l)
				.build();		
		Scheduler scheduler = Scheduler.builder()
				.inicioJanela(LocalDateTime.of(2019,11,10,9,00,00))
				.fimJanela(LocalDateTime.of(2019,11,11,12,00,00))
				.jobs(Arrays.asList(job1, job2, job3))
				.build();
		final List<List<Integer>> jobIdsOrderExecutor = schedulerService.listarIdsJobsValidos(scheduler);
		System.out.println(jobIdsOrderExecutor);
		
	}
}
