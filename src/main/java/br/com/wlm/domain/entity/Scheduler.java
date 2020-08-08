package br.com.wlm.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Scheduler {
	
	private LocalDateTime inicioJanela;
	
	private LocalDateTime fimJanela;
	
	@Builder.Default
	List<Job> jobs = new ArrayList<>();
	
}
