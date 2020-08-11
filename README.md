# Scheduler tools

## Tecnologias usadas

1. [JDK 1.8]([https://jdk.java.net/java-se-ri/8-MR3](https://jdk.java.net/java-se-ri/8-MR3))
2. [Java Stream]([https://www.baeldung.com/java-8-streams](https://www.baeldung.com/java-8-streams))
3. [Maven 3.6.3]([https://maven.apache.org/ref/3.6.3/](https://maven.apache.org/ref/3.6.3/))

## Para rodar o projeto
O projeto é apenas um jar com dados fixados no método main chamando a classe especialista SchedulerServiceImpl, que implementa a classe SchedulerService. Rode o método main a partir da IDE, ou gere o arquivo jar com o comando:

``
mvn package
``

Após gerado o objeto em target/schedulertools-1.0.0.jar. Executar o seguinte comando

``
java -cp schedulertools-1.0.0.jar br.com.wlm.App
``
