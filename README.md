# YAutomation #

Este é um aplicativo de integração de testes (java/andrid) com a ferramentas que reportam ou gerenciam teste de software. 
Através desta integração é possível visualizar gráficos pizza, barra, horizontais e de performance. Também é possível
parametrizar ambientes e configurações de servidores para execução de testes através do app. Este aplicativo funciona 
perfeitamente com os projetos:
- ALMIntegration
- JenkinsIntegration

#### Este app é capaz de realizar a monitoração de testes de software para: ####
* Ferramentas de bug tracking
* Ferramentas de integração contínua
* Ferramentas de gerenciamento de casos de testes

Lembrando que tudo isso pertence a camada de customização.

### Este é um repositório privado. Apenas pessoas autorizadas podem realizar alterações. ###

* Android YAutomation
* v0.01

### O que precisamos ter em mente antes de iniciar as configurações? ###

* 1 - Ferramentas e tecnologias
* 2 - Resultado de gráficos
* 3 - Execução e parametrização
* 4 - Em desenvolvimento

### Contribua conosco ###

* Escreva melhorias tanto no framework como na estrutura dos testes
* A nossa política de code review está em construção

### Entre em contato ###

* Owner/Admin - Gabriel Aguido Fraga


## 1 - Ferramentas e tecnologias ##

Estas são as tecnologias e estruturas que utilizamos no projeto:
- Android/Java 8
- Gradle
- Firebase
	- Real-time database
	- Push notifications
		
			
## 2 - Resultados de gráficos ##

Todos os gráficos são gerados em tempo de execução e gerenciados pelo processamento do próprio app. 
É possível realizar testes de:
- Performane
- Smoke Test
- Regression
- WebServices
- Database
- Etc
Nestes gráficos são possíveis as visualizações de percentuais de testes OK e NOK (gráfico pizza), tempo de execução de 
cada teste (gráficos de barras horizontais), relatório geral de execuções (gráficos de barras verticais) e performance 
(gráfico de picos de execuções).


## 3 - Execução e parametrização ##
Para realizar a parametrização, é necessário acessar a página de parametrização de testes, selecionar os filtros e visualizar 
os bugs para ter ciência da execução dos testes. Dentre os filtros:
- Ambiente de execução
- Sprint
- Host | IP
Já os bugs dependem da integração da ferramenta de bug tracking. Sugerimos JiraIntegration para entendermos melhor o processo.

## 4 - Em desenvolvimento ##

Estamos trabalhando para as seguintes integrações e funcionalidades:
- JiraIntegration
- SlackIntegration
- Níveis de acesso
- Push Notifications (fina de execuções)
- Send Email - (Report de execuções)