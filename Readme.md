
💻 Projeto Donation API

API para gerenciamento de doações de dispositivos entre doadores e beneficiários.
O sistema permite cadastrar doadores, beneficiários, dispositivos e registrar doações, integrando tudo com um banco de dados PostgreSQL.

# ⚙️ 1️⃣ CONFIGURAÇÃO DO BANCO (via Docker)

# Para facilitar o ambiente de desenvolvimento, o projeto pode ser executado com Docker Compose.
Abaixo está o docker-compose.yml recomendado:

version: '3.8'

services:
  postgres:
    image: postgres:16
    container_name: donation_postgres
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: donation_db
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:


## Após salvar esse arquivo na raiz do projeto, execute o comando abaixo para iniciar o banco:

docker-compose up -d


🧰 2️⃣ CONFIGURAÇÃO DO application.properties

No arquivo src/main/resources/application.properties, configure a conexão com o banco:

spring.datasource.url=jdbc:postgresql://localhost:5432/donation_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


# 🧾 Banco de Dados — Sistema de Doações de Dispositivos

Este script SQL define a estrutura e os dados iniciais utilizados pelo sistema de gerenciamento de doações de dispositivos da AEP Tech Solidária.

---

## 🗄️ 3️⃣ SCRIPT SQL (DDL + DML)

### 🧩 DDL – Estrutura das Tabelas

O banco foi modelado considerando a integridade referencial entre as entidades **Doador**, **Beneficiário**, **Dispositivo** e **Doação**, refletindo as relações mapeadas nas entidades do projeto Java (JPA).

```sql
-- =====================================
-- Tabela: DOADOR
-- =====================================
CREATE TABLE doador (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- Tabela: BENEFICIARIO
-- =====================================
CREATE TABLE beneficiario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf_ou_documento VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(20),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- Tabela: DISPOSITIVO
-- =====================================
CREATE TABLE dispositivo (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    condicao VARCHAR(30) CHECK (condicao IN ('WORKING', 'NEEDS_REPAIR', 'FOR_PARTS')),
    descricao TEXT,
    registrado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- Tabela: DOACAO
-- =====================================
CREATE TABLE doacao (
    id SERIAL PRIMARY KEY,
    data_doacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    doador_id INT NOT NULL,
    beneficiario_id INT NOT NULL,
    dispositivo_id INT NOT NULL UNIQUE,
    observacoes TEXT,
    FOREIGN KEY (doador_id) REFERENCES doador(id) ON DELETE CASCADE,
    FOREIGN KEY (beneficiario_id) REFERENCES beneficiario(id) ON DELETE CASCADE,
    FOREIGN KEY (dispositivo_id) REFERENCES dispositivo(id) ON DELETE CASCADE
);


-- =====================================
-- Inserindo Doadores
-- =====================================
INSERT INTO doador (nome, email, telefone) VALUES
('Carlos Mendes', 'carlos.mendes@email.com', '(11) 98888-1111'),
('Fernanda Alves', 'fernanda.alves@email.com', '(11) 97777-2222'),
('Rogério Lima', 'rogerio.lima@email.com', '(11) 96666-5555');

-- =====================================
-- Inserindo Beneficiários
-- =====================================
INSERT INTO beneficiario (nome, cpf_ou_documento, email, telefone) VALUES
('Maria Souza', '123.456.789-00', 'maria.souza@email.com', '(11) 96666-3333'),
('João Pereira', '987.654.321-00', 'joao.pereira@email.com', '(11) 95555-4444'),
('Larissa Carvalho', '456.789.123-00', 'larissa.carvalho@email.com', '(11) 93333-2222');

-- =====================================
-- Inserindo Dispositivos
-- =====================================
INSERT INTO dispositivo (tipo, marca, modelo, condicao, descricao) VALUES
('Notebook', 'Dell', 'Inspiron 15', 'WORKING', 'Notebook em bom estado, sem carregador.'),
('Smartphone', 'Samsung', 'Galaxy A52', 'NEEDS_REPAIR', 'Tela trincada, mas funcional.'),
('Tablet', 'Lenovo', 'Tab M10', 'FOR_PARTS', 'Com falha na placa-mãe.'),
('Computador', 'HP', 'Pavilion', 'WORKING', 'Pronto para uso.');

-- =====================================
-- Inserindo Doações
-- =====================================
INSERT INTO doacao (doador_id, beneficiario_id, dispositivo_id, observacoes) VALUES
(1, 1, 1, 'Doação feita durante o evento AEP Tech Solidária.'),
(2, 2, 2, 'Doação realizada via campanha online.'),
(3, 3, 3, 'Doação registrada pelo time administrativo.');


--Consultas uteis


-- Listar todas as doações com informações completas
SELECT 
    d.id AS id_doacao,
    doador.nome AS nome_doador,
    beneficiario.nome AS nome_beneficiario,
    dispositivo.tipo AS tipo_dispositivo,
    dispositivo.marca,
    dispositivo.condicao,
    d.data_doacao,
    d.observacoes
FROM doacao d
JOIN doador ON d.doador_id = doador.id
JOIN beneficiario ON d.beneficiario_id = beneficiario.id
JOIN dispositivo ON d.dispositivo_id = dispositivo.id
ORDER BY d.data_doacao DESC;

-- Contar total de dispositivos disponíveis
SELECT COUNT(*) AS total_dispositivos FROM dispositivo;

-- Contar total de doações realizadas
SELECT COUNT(*) AS total_doacoes FROM doacao;


🚀 4️⃣ COMO EXECUTAR O PROJETO

Suba o banco de dados:

docker-compose up -d


Execute o projeto Spring Boot:

mvn spring-boot:run
