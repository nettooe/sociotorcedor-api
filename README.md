# sociotorcedor-api

Serviço de Gerenciamento de Sócios Torcedores, desenvolvido por mim para demonstração de construção de microserviço REST utilizando Spring Boot.

## Começando
Essas instruções irão demostrar como obter uma cópia do projeto e colocá-la em execução em uma máquina linux para fins de desenvolvimento e teste.

### Pré requisitos
Você precisará somente ter o [Java 8 JDK](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html) instalado  para o desenvolvimento. O projeto possui o [Maven](https://maven.apache.org/) encapsulado e você poderá executá-lo substituindo o comando *mvn* por *mvnw*, por exemplo:
```
./mvnw clean install
```
### Instalando e executando
Para instalar e executar o projeto localmente, basta [clonar o projeto](https://help.github.com/articles/cloning-a-repository/) e executar. Para isto, abra o terminal no seu linux e execute os comandos:
```
$ git clone git@github.com:nettooe/sociotorcedor-api.git
$ cd campanha-git
$ ./mvnw clean install spring-boot:run
```

### Instalação adicional recomendada
Para que sua experiência fique ainda melhor, saiba que este serviço consome outros serviços! Para isto eu recomendo que você clone e execute também o meu repositório "campanha-api".
Abra mais um terminal no seu Linux e utilize os seguintes comando:
```
$ git clone git@github.com:nettooe/campanha-api.git
$ cd campanha-git
$ ./mvnw clean install spring-boot:run
```

## Documentação
A documentação dos serviços REST deste projeto se encontram no próprio projeto. Para visualizar, siga os passos do tópico 'Instalando e executando' e, em seguida, no seu navegador de internet, abra a seguinte URL http://localhost:8091/swagger-ui.html

## Arquitetura
Esta solução utiliza arquitetura de microserviço. Mais precisamente, este é  um microserviço e inicializa um banco de dados próprio do tipo H2.

Divirta-se!
