package br.com.wlm.services.impl;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.wlm.domain.entity.Job;
import br.com.wlm.domain.entity.Scheduler;
import br.com.wlm.domain.exception.JobInvalidoException;
import br.com.wlm.services.SchedulerService;

public class SchedulerServiceImplTest {
		
	private SchedulerService schedulerService;
	
	@BeforeEach
	public void setUp() {
		this.schedulerService = new SchedulerServiceImpl();
	}
	
	@Test
	@DisplayName("Deverá retornar um erro quando um job tiver Data Maxima execução é anterior a inicio da janela ou posterior ao fim da janela")
	public void listIdsJobsValidosErroJobsComDataMaximaExecucaoInvalidaTest() {		
		Job job1 = Job.builder()
				.id(1)
				.descricao("Importação de arquivos de fundos")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,9,12,00,00))
				.tempoEstimadoEmHoras(2l)
				.build();
		Scheduler scheduler = 
				Scheduler.builder()
				.inicioJanela(LocalDateTime.of(2019, 11, 11, 12, 00, 00))
				.fimJanela(LocalDateTime.of(2019, 11, 10, 12, 00, 00))
				.jobs(Collections.singletonList(job1))
				.build();
		String dataInicioJanelaFormatada = scheduler.getInicioJanela().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String dataFimJanelaFormatada = scheduler.getFimJanela().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String mensagemException = 
				String.format("A data máxima de execução de um job "
				+ "não pode ser anterior ao inicio da janaela: %1$s e também não pode ser posterior ao fim da janela: %2$s. Id Jobs invalidos: %3$s", 
				dataInicioJanelaFormatada, dataFimJanelaFormatada, scheduler.getJobs().stream().map(Job::getId).collect(Collectors.toList()));
		Throwable throwable = catchThrowable(() -> schedulerService.listarIdsJobsValidos(scheduler));
		assertThat(throwable).isInstanceOf(JobInvalidoException.class);
		assertThat(throwable.getMessage()).isEqualTo(mensagemException);
	}
	
	@Test
	@DisplayName("Deve retornar um conjunto de arrays de ids de jobs para execução")
	public void listIdsJobsValidosTest() {
		List<List<Integer>> idsEsperados = Arrays.asList(Arrays.asList(1,2), Arrays.asList(3));		
		Job job1 = Job.builder()
				.id(1)
				.descricao("Importação de arquivos de fundos")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,10,12,00,00))
				.tempoEstimadoEmHoras(2l)
				.build();
		Job job2 = Job.builder()
				.id(2)
				.descricao("Importação de dados da Base Legada")
				.dataMaximaExecucao(LocalDateTime.of(2019,11,11,12,00,00))
				.tempoEstimadoEmHoras(4l)
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
		List<List<Integer>> idsRetornados = schedulerService.listarIdsJobsValidos(scheduler);
		assertThat(idsRetornados).isEqualTo(idsEsperados);
	}
	
}
