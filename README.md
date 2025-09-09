# 🚀 Guia rápido para rodar Apache Kafka no Windows

Este guia mostra como iniciar um broker Kafka localmente, criar um tópico e testar com producer/consumer no **Windows** usando os scripts `.bat`.

---

## 📦 Pré-requisitos

- [Java 11+](https://adoptium.net/) instalado e configurado no `PATH`
- [Apache Kafka](https://kafka.apache.org/downloads) baixado e extraído
- Terminal: **Git Bash** ou **PowerShell**

---

### Cria variável

````shell
KAFKA_CLUSTER_ID="$(./windows/kafka-storage.bat random-uuid)"
````

### Cria diretório de logs

````shell
./windows/kafka-storage.bat format --standalone -t $KAFKA_CLUSTER_ID -c ../config/server.properties
````

### Comando para subir o broker

````shell
./windows/kafka-server-start.bat ../config/server.properties
````

### Comando para criar um topic

````shell
 bin/windows/kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
````

### Comando para listar um topic

````shell
 bin/windows/kafka-topics.bat --list --bootstrap-server localhost:9092
````

### Criando um producer no console

````shell
bin/windows/kafka-console-producer.bat --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO 
````

### Consumindo uma mensagem e especificando como

````shell
bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO --from-beginning
````
## Grupo de consumo

````shell
.\bin\windows\kafka-consumer-groups.bat --all-groups --bootstrap-server localhost:9092 --describe
````


## Configurando Vários consumidores e produtores

- Defina um grupo no contexto do Kafka

### Comando para alterar uma partição:
- Não é possível diminuir uma partição já criada
- A chave quem direciona em qual a partição a mensagem vai cair;
- Configurar o pull para 1 evita rebalanceamento por erro de commit
````shell
.\bin\windows\kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3 

````

