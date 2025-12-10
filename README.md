# 📚 Sistema de Gerenciamento de Biblioteca (Java · MVC · OOP)

Este é um **Sistema de Gerenciamento de Biblioteca** desenvolvido em **Java**, utilizando o padrão **MVC (Model–View–Controller)** e aplicando todos os pilares da **Programação Orientada a Objetos**:

✔️ Abstração  
✔️ Encapsulamento  
✔️ Herança  
✔️ Polimorfismo  

O projeto foi desenvolvido como **trabalho acadêmico**, com o objetivo de praticar arquitetura, boas práticas e funcionalidades completas dentro de um sistema realista de biblioteca.

---

## 🚀 Funcionalidades

### 📖 Livros
- Cadastro de livros (título, autor, ano de publicação, categoria e exemplares).
- Pesquisa por **código, título, autor ou categoria**.
- Controle de exemplares disponíveis.

### 👤 Usuários
- Cadastro de usuários (nome, telefone, endereço e e-mail).
- Histórico de empréstimos.

### 📘 Empréstimos
- Empréstimo realizado apenas se:
  - o usuário não tiver livros pendentes;
  - houver exemplares disponíveis.
- Registro de data do empréstimo e data prevista de devolução.

### 🔄 Devoluções
- Atualização automática dos exemplares.
- Registro da data efetiva de devolução.
- Cálculo automático de **dias em atraso**.

### ⏳ Atrasos
- Lista de devoluções com atraso.
- Ordenação automática do maior para o menor atraso.

### 📊 Relatórios
- Livros atualmente emprestados.
- Usuários com devoluções atrasadas.
- Lista ordenada de atrasos.
- Livros mais requisitados (extensível).

### ⚙️ Pré-Carga de Dados
O sistema possui uma classe específica para inserir automaticamente:
- Usuários  
- Livros  
- Empréstimos  
- Devoluções  

Facilitando **testes rápidos** sem precisar inserir tudo manualmente.

---

## 🧱 Arquitetura (MVC)
  ```
  src/
 ├── model/
 │     ├── Livro.java
 │     ├── Usuario.java
 │     ├── Emprestimo.java
 │     ├── Pessoa.java (abstrata)
 │     ├── Relatorio.java (abstrata)
 │     ├── RelatorioEmprestimos.java
 │     └── RelatorioAtrasos.java
 │
 ├── controller/
 │     └── BibliotecaController.java
 │
 ├── view/
 │     └── BibliotecaView.java
 │
 ├── service/
 │     └── Biblioteca.java
 │
 ├── precarga/
 │     └── PreCargaDados.java
 │
 └── Main.java
```

---

## ▶️ Como Executar
Pré-requisitos
- Java 8+
- Qualquer IDE (IntelliJ, Eclipse, NetBeans) ou terminal.

Execução via terminal
  ```
  javac Main.java
  java Main
  ```

---

## 🧪 Exemplos de Uso (Console)
Menu Principal
  ```
  --- Sistema de Biblioteca ---
1 - Cadastrar Livro
2 - Pesquisar Livro
3 - Cadastrar Usuário
4 - Empréstimo
5 - Devolução
6 - Relatórios
0 - Sair
```

---

## 🛠️ Tecnologias Utilizadas
- Java SE
- Programação Orientada a Objetos
- Coleções (List, Map, ArrayList, HashMap)
- Java Time (LocalDate, ChronoUnit)
- Arquitetura MVC

---

## 🎓 Objetivo Acadêmico

Este projeto foi desenvolvido como prática de conceitos fundamentais de:
- Orientação a Objetos
- Arquitetura MVC
- Estruturação de sistemas reais
- Boas práticas de programação

Servindo também como um sistema-base para evoluções futuras, como interface gráfica, persistência em banco de dados ou API REST.

---

## 👥 Autores
Projeto acadêmico desenvolvido por:

- Leon Trigo
- Lucas Kumegawa de Godoi
- Thauana Vitória Ferreira Farias
- Vitor Gonçalves

---


## 📄 Licença

Projeto desenvolvido apenas para fins acadêmicos, não destinado a uso comercial.
