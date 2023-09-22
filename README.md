# Estcar

ESTCAR Local
spring.datasource.username=root
spring.datasource.password=20112011
spring.datasource.url=jdbc:mysql://localhost:3306/DB_Estacionamento?useTimezone=true&serverTimezone=UTC
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

Railway
spring.datasource.username=${MYSQLUSER}
spring.datasource.password=${MYSQLPASSWORD}
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
spring.datasource.url=jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}?useTimezone=true&serverTimezone=UTC
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

Estacionamento
<td th:text="${#temporals.format(vaga.tempoEstacionado.minusHours(3), 'dd/MM/yyyy HH:mm')}"></td>

Financeiro
<td th:text="${#temporals.format(registro.dataLiberacao.minusHours(3), 'dd/MM/yyyy HH:mm')}"></td>
