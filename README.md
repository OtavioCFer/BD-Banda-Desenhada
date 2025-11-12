# 🎨 Banda Desenhada

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql)

> **Banda Desenhada** é uma plataforma web feita para colecionadores e leitores de quadrinhos que querem **organizar, avaliar e descobrir novas HQs** — tudo em um só lugar.

---

## 📘 Sobre o Projeto

O sistema tem como objetivo servir tanto como **base de dados consultável sobre quadrinhos** quanto como **ferramenta de gestão de coleção pessoal**.  
Cada usuário pode cadastrar, marcar o que já leu, adicionar à lista de desejos e ainda comparar suas notas com as da comunidade.  

A ideia é simples: unir **organização, estatísticas e paixão por quadrinhos** num só sistema. 🦸‍♂️📚

---

## 🚀 Funcionalidades

### 👤 Cadastro e Login
- Criação de conta com e-mail e senha;
- Login seguro com autenticação;
- Acesso personalizado às funções do usuário.

### 🔎 Navegação e Busca
- Pesquisa por **título**, **autor**, **personagem** ou **editora**;
- Página detalhada com capa, sinopse e nota média da comunidade.

### 📚 Coleção Pessoal
- Adicione HQs à sua coleção;
- Marque como **Lido**, **Tenho**, **Quero** ou **Vendido**;
- Visualize e filtre por status.

### ⭐ Avaliações
- Dê notas de **1 a 10** para roteiro, arte, cores e edição;
- Escreva uma resenha opcional;
- Veja sua média pessoal e compare com a média geral.

---

## 📊 Relatórios e Consultas SQL

O sistema traz três relatórios principais (e muito úteis 💡):

### 1️⃣ **Minha Coleção**
- Dashboard pessoal com:
  - Gráfico de pizza por editora;
  - Tabela com autores mais frequentes e nota média;
  - Cards com total de HQs e estatísticas gerais.

### 2️⃣ **Top 10 da Comunidade**
- Ranking dos quadrinhos mais bem avaliados;
- Filtros por **gênero** ou **década**;
- Funções SQL de ranking (`RANK()` / `ROW_NUMBER()`).

### 3️⃣ **Minhas Notas vs. Comunidade**
- Gráfico comparando **suas notas** com a **média da comunidade**;
- Construído com subconsultas e CTEs (`WITH`).

---

## 🧩 Modelo de Dados
Usuário
Editora
Autor
Gênero
Quadrinho
Coleção
Avaliação


Relacionamentos N:N são tratados por tabelas intermediárias como:
- `QuadrinhoAutor`
- `QuadrinhoGenero`
O sistema é composto por entidades como:

