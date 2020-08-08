package br.com.wlm.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Job {
		
	private Integer id;
	
	private String descricao;
	
	private LocalDateTime dataMaximaExecucao;
	
	private Long tempoEstimadoEmHoras;

}
