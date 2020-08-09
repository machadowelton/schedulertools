package br.com.wlm.domain.control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.wlm.domain.entity.Job;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ControlJob {
	
	@Builder.Default
	private List<Job> jobs = new ArrayList<>();
	
	public boolean estaCompleto() {
		return jobs.stream().mapToLong(Job::getTempoEstimadoEmHoras).sum() == 8;
	}
	
	public boolean adiconarJob(Job job) {
		long somaHoras = this.jobs.stream().mapToLong(Job::getTempoEstimadoEmHoras).sum();
		if(somaHoras == 8) return false;
		if(somaHoras + job.getTempoEstimadoEmHoras() > 8) return false;
		List<Job> refazJobs = new ArrayList<>(this.jobs);
		refazJobs.add(job);
		this.jobs = refazJobs;
		return true;
	}
	
	public List<Integer> listarIdJobs() {
		return this.jobs.stream().map(Job::getId).collect(Collectors.toList());
	}
	
}
