-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 10/11/2025 às 01:49
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `sicsecdb`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `administrador`
--

CREATE TABLE `administrador` (
  `id` bigint(20) NOT NULL,
  `cargo_administrador` enum('CHEFE_DE_DEPARTAMENTO','COORDENADOR','ROOT') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `administrador`
--

INSERT INTO `administrador` (`id`, `cargo_administrador`) VALUES
(12, 'ROOT'),
(13, 'COORDENADOR'),
(14, 'CHEFE_DE_DEPARTAMENTO');

-- --------------------------------------------------------

--
-- Estrutura para tabela `afastamento`
--

CREATE TABLE `afastamento` (
  `id` bigint(20) NOT NULL,
  `causa` varchar(255) DEFAULT NULL,
  `data_afastamento` date DEFAULT NULL,
  `data_retorno` date DEFAULT NULL,
  `professor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno`
--

CREATE TABLE `aluno` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aluno`
--

INSERT INTO `aluno` (`id`) VALUES
(1),
(4),
(8),
(11);

-- --------------------------------------------------------

--
-- Estrutura para tabela `assinaturas`
--

CREATE TABLE `assinaturas` (
  `id` bigint(20) NOT NULL,
  `documento_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) NOT NULL,
  `data_criacao` datetime DEFAULT NULL,
  `data_assinatura` datetime DEFAULT NULL,
  `status` ENUM('CONFIRMADA','PENDENTE','REJEITADA', 'ATRASADA') DEFAULT 'PENDENTE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `atividade`
--

CREATE TABLE `atividade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `professor_id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `valor` double DEFAULT NULL,
  `data_encerramento` varchar(255) DEFAULT NULL,
  `hora_encerramento` varchar(255) DEFAULT NULL,
  `data_criacao` varchar(255) DEFAULT NULL,
  `enunciado` longtext,
  `questoes` longtext,
  `tentativas` int(11) DEFAULT NULL,
  `horas` int(11) DEFAULT NULL,
  `minutos` int(11) DEFAULT NULL,
  `tipo_timer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_atividade_professor` (`professor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `atividade`
--

INSERT INTO `atividade` (`id`, `professor_id`, `nome`, `tipo`, `valor`, `data_encerramento`, `hora_encerramento`, `data_criacao`, `enunciado`, `questoes`, `tentativas`, `horas`, `minutos`, `tipo_timer`) VALUES
(1, 2, 'Resenha crítica Guerreiras do Kpop', 'Redação', 10, '2025-12-23', '08:00', '2025-12-20', 'Faça uma resenha crítica acerca do filme Guerreiras do Kpop', NULL, 1, 1, 10, 'continuo'),
(2, 2, 'Competição do PDF mais insado', 'Envio de Arquivo', 5, '2025-12-25', '08:00', '2025-12-20', 'Envie um PDF super insano. Quanto mais maneiro, melhor!', NULL, 1, 1, 10, 'interrompivel');

-- --------------------------------------------------------

--
-- Estrutura para tabela `aula`
--

CREATE TABLE `aula` (
  `id` bigint(20) NOT NULL,
  `dia` enum('DOMINGO','QUARTA_FEIRA','QUINTA_FEIRA','SABADO','SEGUNDA_FEIRA','SEXTA_FEIRA','TERCA_FEIRA') DEFAULT NULL,
  `fim` time(6) DEFAULT NULL,
  `inicio` time(6) DEFAULT NULL,
  `turno` enum('FORA','INTERVALO_ALMOCO','INTERVALO_JANTAR','MANHA','NOITE','TARDE') DEFAULT NULL,
  `sala` varchar(255) DEFAULT NULL,
  `id_disciplina` bigint(20) NOT NULL,
  `id_turma` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aula`
--

INSERT INTO `aula` (`id`, `dia`, `fim`, `inicio`, `turno`, `sala`, `id_disciplina`, `id_turma`) VALUES
(1, 'SEGUNDA_FEIRA', '09:40:00.000000', '08:00:00.000000', 'MANHA', 'A101', 1, 1),
(2, 'TERCA_FEIRA', '11:40:00.000000', '10:00:00.000000', 'MANHA', 'B202', 2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `biblioteca`
--

CREATE TABLE `biblioteca` (
  `id` bigint(20) NOT NULL,
  `campus` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `biblioteca`
--

INSERT INTO `biblioteca` (`id`, `campus`, `nome`, `telefone`) VALUES
(1, 'Campus I', 'Biblioteca Central', '(31) 3409-0001'),
(2, 'Campus II', 'Biblioteca de Exatas', '(31) 3409-0002');

-- --------------------------------------------------------

--
-- Estrutura para tabela `bibliotecario`
--

CREATE TABLE `bibliotecario` (
  `id` bigint(20) NOT NULL,
  `id_biblioteca` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `biblioteca_livro`
--

CREATE TABLE `biblioteca_livro` (
  `biblioteca_id` bigint(20) NOT NULL,
  `livro_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `biblioteca_livro`
--

INSERT INTO `biblioteca_livro` (`biblioteca_id`, `livro_id`) VALUES
(1, 1),
(1, 2),
(2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `boletim`
--

CREATE TABLE `boletim` (
  `id` bigint(20) NOT NULL,
  `situacao_do_ano` enum('APROVADO','MATRICULADO','REPROVADO') DEFAULT NULL,
  `aluno_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `boletim`
--

INSERT INTO `boletim` (`id`, `situacao_do_ano`, `aluno_id`) VALUES
(1, 'MATRICULADO', 1),
(2, 'MATRICULADO', 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `bolsa`
--

CREATE TABLE `bolsa` (
  `id` bigint(20) NOT NULL,
  `fim` date DEFAULT NULL,
  `inicio` date DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `vagas` int(11) NOT NULL,
  `valor_mensal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `bolsista`
--

CREATE TABLE `bolsista` (
  `id_bolsa` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `chefe_departamento`
--

CREATE TABLE `chefe_departamento` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `chefe_departamento`
--

INSERT INTO `chefe_departamento` (`id`) VALUES
(14);

-- --------------------------------------------------------

--
-- Estrutura para tabela `componente_curricular`
--

CREATE TABLE `componente_curricular` (
  `id` bigint(20) NOT NULL,
  `faltas` int(11) NOT NULL,
  `nota_final` int(11) NOT NULL,
  `situacao` enum('APROVADO','EM_CURSO','EM_RECUPERACAO','REPROVADO') DEFAULT NULL,
  `boletim_id` bigint(20) NOT NULL,
  `disciplina_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `componente_curricular`
--

INSERT INTO `componente_curricular` (`id`, `faltas`, `nota_final`, `situacao`, `boletim_id`, `disciplina_id`) VALUES
(1, 2, 85, 'EM_CURSO', 1, 1),
(2, 3, 90, 'EM_CURSO', 2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `coordenador`
--

CREATE TABLE `coordenador` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `coordenador`
--

INSERT INTO `coordenador` (`id`) VALUES
(13);

-- --------------------------------------------------------

--
-- Estrutura para tabela `curso`
--

CREATE TABLE `curso` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `coordenador_id` bigint(20) DEFAULT NULL,
  `departamento_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `curso`
--

INSERT INTO `curso` (`id`, `codigo`, `nome`, `coordenador_id`, `departamento_id`) VALUES
(1, 'COM1', 'Engenharia de Computação', 13, 1),
(2, 'MAT1', 'Licenciatura em Matemática', NULL, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `dados_bancarios`
--

CREATE TABLE `dados_bancarios` (
  `id` bigint(20) NOT NULL,
  `agencia` varchar(255) DEFAULT NULL,
  `banco` varchar(255) DEFAULT NULL,
  `conta` varchar(255) DEFAULT NULL,
  `cpf` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `dados_bancarios`
--

INSERT INTO `dados_bancarios` (`id`, `agencia`, `banco`, `conta`, `cpf`) VALUES
(1, '1234', 'Santander', '00000-1', 99876543211),
(2, '4321', 'Banco do Brasil', '11111-2', 11223344556),
(3, '5678', 'Caixa', '22222-3', 99887766554),
(4, '8765', 'Itaú', '33333-4', 88776655443),
(5, '1111', 'Bradesco', '44444-5', 77665544332),
(6, '2222', 'Santander', '55555-6', 66554433221),
(7, '3333', 'Nubank', '66666-7', 55443322110),
(8, '4444', 'Inter', '77777-8', 44332211009),
(9, '5555', 'Banco do Brasil', '88888-9', 33221100998),
(10, '6666', 'Caixa', '99999-0', 22110099887),
(11, '7777', 'Itaú', '01010-1', 11009988776),
(12, NULL, NULL, NULL, NULL),
(13, '1221', 'Banco do Brasil', '98765-4', 12345678901),
(14, '4343', 'Caixa Econômica Federal', '12345-6', 98765432100),
(15, '4312', 'Santander', '65412-3', 19112122113);

-- --------------------------------------------------------

--
-- Estrutura para tabela `departamento`
--

CREATE TABLE `departamento` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `coordenador_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `departamento`
--

INSERT INTO `departamento` (`id`, `nome`, `coordenador_id`) VALUES
(1, 'Departamento de Computação', 14),
(2, 'Departamento de Matemática', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `disciplina`
--

CREATE TABLE `disciplina` (
  `id` bigint(20) NOT NULL,
  `area` enum('A','B','C','TECNICO') DEFAULT NULL,
  `carga_horaria` int(11) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `curso_id` bigint(20) NOT NULL,
  `departamento_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `disciplina`
--

INSERT INTO `disciplina` (`id`, `area`, `carga_horaria`, `nome`, `curso_id`, `departamento_id`) VALUES
(1, 'A', 60, 'Programação I', 1, 1),
(2, 'B', 60, 'Cálculo I', 2, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `documento`
--

CREATE TABLE `documento` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `conteudo` varchar(512) DEFAULT NULL,
  `criador_id` bigint(20) DEFAULT NULL,
  `status` enum('ARQUIVADO','ASSINADO','PENDENTE','EXPIRADO') DEFAULT NULL,
  `data_criacao` date DEFAULT NULL,
  `data_expiracao` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `emprestimo`
--

CREATE TABLE `emprestimo` (
  `id` bigint(20) NOT NULL,
  `data` date DEFAULT NULL,
  `duracao` int(11) NOT NULL,
  `reserva` enum('EMPRESTIMO','HISTORICO','RESERVA') DEFAULT NULL,
  `id_livro` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `emprestimo`
--

INSERT INTO `emprestimo` (`id`, `data`, `duracao`, `reserva`, `id_livro`, `id_usuario`) VALUES
(1, '2025-10-20', 15, 'EMPRESTIMO', 1, 1),
(2, '2025-10-22', 10, 'EMPRESTIMO', 2, 8);

-- --------------------------------------------------------

--
-- Estrutura para tabela `lecionamento`
--

CREATE TABLE `lecionamento` (
  `id` bigint(20) NOT NULL,
  `fim` date DEFAULT NULL,
  `inicio` date DEFAULT NULL,
  `professor_id` bigint(20) NOT NULL,
  `turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `lecionamento`
--

INSERT INTO `lecionamento` (`id`, `fim`, `inicio`, `professor_id`, `turma_id`) VALUES
(1, '2025-12-15', '2025-08-01', 2, 1),
(2, '2025-12-15', '2025-08-01', 10, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `lista_presenca`
--

CREATE TABLE `lista_presenca` (
  `id` bigint(20) NOT NULL,
  `data` date DEFAULT NULL,
  `fim` time(6) DEFAULT NULL,
  `inicio` time(6) DEFAULT NULL,
  `turno` enum('FORA','INTERVALO_ALMOCO','INTERVALO_JANTAR','MANHA','NOITE','TARDE') DEFAULT NULL,
  `autor_id` bigint(20) NOT NULL,
  `turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `lista_presenca`
--

INSERT INTO `lista_presenca` (`id`, `data`, `fim`, `inicio`, `turno`, `autor_id`, `turma_id`) VALUES
(1, '2025-10-15', '09:40:00.000000', '08:00:00.000000', 'MANHA', 2, 1),
(2, '2025-10-16', '11:40:00.000000', '10:00:00.000000', 'MANHA', 6, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `livro`
--

CREATE TABLE `livro` (
  `id` bigint(20) NOT NULL,
  `ano` int(11) NOT NULL,
  `autor` varchar(255) DEFAULT NULL,
  `codigo` bigint(20) DEFAULT NULL,
  `editora` varchar(255) DEFAULT NULL,
  `isbn` bigint(20) DEFAULT NULL,
  `status` enum('DISPONIVEL','EMPRESTADO','INDISPONIVEL') DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `livro`
--

INSERT INTO `livro` (`id`, `ano`, `autor`, `codigo`, `editora`, `isbn`, `status`, `titulo`) VALUES
(1, 2020, 'Andrew Tanenbaum', 1001, 'Pearson', 9788576054010, 'DISPONIVEL', 'Sistemas Operacionais Modernos'),
(2, 2018, 'Thomas Cormen', 1002, 'MIT Press', 9788577806182, 'DISPONIVEL', 'Algoritmos: Teoria e Prática');

-- --------------------------------------------------------

--
-- Estrutura para tabela `material_didatico`
--

CREATE TABLE `material_didatico` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `material_didatico`
--

INSERT INTO `material_didatico` (`id`, `nome`, `url`, `turma_id`) VALUES
(1, 'Apostila de Programação I', 'https://materiais.cefetmg.br/apostila-prog1.pdf', 1),
(2, 'Lista de Exercícios de Cálculo', 'https://materiais.cefetmg.br/lista-calc1.pdf', 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `matricula`
--

CREATE TABLE `matricula` (
  `id` bigint(20) NOT NULL,
  `cpf` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `numero_matricula` bigint(20) DEFAULT NULL,
  `telefone` varchar(255) DEFAULT NULL,
  `curso_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `matricula`
--

INSERT INTO `matricula` (`id`, `cpf`, `email`, `nome`, `numero_matricula`, `telefone`, `curso_id`) VALUES
(1, 12345678900, 'jose@gmail.com', 'José', 202512345, '+31 0000-0000', 1),
(2, 98765432100, 'maria.silva@gmail.com', 'Maria Silva', 202512346, '+31 91234-5678', 1),
(3, 12312312399, 'carlos.pereira@cefetmg.br', 'Carlos Pereira', 202512347, '+31 99876-5432', NULL),
(4, 32165498700, 'ana.souza@hotmail.com', 'Ana Souza', 202512348, '+31 91122-3344', 1),
(5, 65498732100, 'roberto.lima@biblioteca.com', 'Roberto Lima', 202512349, '+31 97777-8888', NULL),
(6, 11122233344, 'fernanda.alves@cefetmg.br', 'Fernanda Alves', 202512350, '+31 90000-1122', 2),
(7, 99988877766, 'gustavo.torres@lab.com', 'Gustavo Torres', 202512351, '+31 95555-6666', NULL),
(8, 44455566677, 'laura.mendes@gmail.com', 'Laura Mendes', 202512352, '+31 96666-7777', 2),
(9, 88899900011, 'paulo.rocha@cefetmg.br', 'Paulo Rocha', 202512353, '+31 92222-3333', NULL),
(10, 55566677788, 'beatriz.faria@prof.com', 'Beatriz Faria', 202512354, '+31 98888-9999', 2),
(11, 77788899900, 'lucas.oliveira@gmail.com', 'Lucas Oliveira', 202512355, '+31 93333-4444', 1),
(12, 1, 'admin@cefetmg.br', 'root', NULL, NULL, NULL),
(13, 54191514131, 'edson.marchetti@cefetmg.br', 'Edson Marchetti', 202512356, '+31 98765-4321', 1),
(14, 13166151820, 'cristiano.maffort@cefetmg.br', 'Cristiano Maffort', 202512357, '+31 99123-4567', NULL),
(15, 19112122113, 'prof.sallum@cefetmg.br', 'William Sallum', 202512358, '+31 98266-6600', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `nescessidade_especial`
--

CREATE TABLE `nescessidade_especial` (
  `id` bigint(20) NOT NULL,
  `acomodacao` varchar(255) DEFAULT NULL,
  `cidr` varchar(255) DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `aluno_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `nota`
--

CREATE TABLE `nota` (
  `id` bigint(20) NOT NULL,
  `avaliacao` varchar(255) DEFAULT NULL,
  `bimestre` enum('PRIMEIRO','QUARTO','SEGUNDO','TERCEIRO') DEFAULT NULL,
  `valor` int(11) NOT NULL,
  `componente_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `nota`
--

INSERT INTO `nota` (`id`, `avaliacao`, `bimestre`, `valor`, `componente_id`) VALUES
(1, 'Prova 1', 'PRIMEIRO', 40, 1),
(2, 'Trabalho', 'PRIMEIRO', 45, 1),
(3, 'Prova 1', 'PRIMEIRO', 50, 2),
(4, 'Trabalho', 'PRIMEIRO', 40, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `noticia`
--

CREATE TABLE `noticia` (
  `id` bigint(20) NOT NULL,
  `corpo` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `manchete` varchar(255) DEFAULT NULL,
  `autor_id` bigint(20) NOT NULL,
  `turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `noticia`
--

INSERT INTO `noticia` (`id`, `corpo`, `data`, `manchete`, `autor_id`, `turma_id`) VALUES
(1, 'Prova marcada para próxima semana.', '2025-10-28', 'Aviso de Prova', 2, 1),
(2, 'Entrega de trabalhos até sexta.', '2025-10-29', 'Trabalhos Finais', 10, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pesquisador`
--

CREATE TABLE `pesquisador` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `presenca`
--

CREATE TABLE `presenca` (
  `id` bigint(20) NOT NULL,
  `presente` bit(1) NOT NULL,
  `aluno_id` bigint(20) DEFAULT NULL,
  `lista_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `presenca`
--

INSERT INTO `presenca` (`id`, `presente`, `aluno_id`, `lista_id`) VALUES
(1, b'1', 1, 1),
(2, b'0', 4, 1),
(3, b'1', 8, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `producao_academica`
--

CREATE TABLE `producao_academica` (
  `id` bigint(20) NOT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `publicacao` date DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `id_autor` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `producao_academica`
--

INSERT INTO `producao_academica` (`id`, `descricao`, `publicacao`, `tipo`, `titulo`, `id_autor`) VALUES
(1, 'Artigo sobre IA', '2024-09-10', 'ARTIGO', 'Inteligência Artificial Aplicada', 3),
(2, 'Estudo sobre redes', '2023-06-05', 'PESQUISA', 'Redes de Computadores', 7);

-- --------------------------------------------------------

--
-- Estrutura para tabela `professor`
--

CREATE TABLE `professor` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `professor`
--

INSERT INTO `professor` (`id`) VALUES
(2),
(6),
(10),
(15);

-- --------------------------------------------------------

--
-- Estrutura para tabela `root_user`
--

CREATE TABLE `root_user` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `sub_turma`
--

CREATE TABLE `sub_turma` (
  `id` bigint(20) NOT NULL,
  `super_turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `super_turma`
--

CREATE TABLE `super_turma` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `turma`
--

CREATE TABLE `turma` (
  `id` bigint(20) NOT NULL,
  `tipo` enum('SUBTURMA','SUPERTURMA','TURMA_UNICA') DEFAULT NULL,
  `disciplina_id` bigint(20) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `curso_id` bigint(20) NOT NULL,
  `ano_letivo` int(11) NOT NULL,
  `ativo` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `turma`
--

INSERT INTO `turma` (`id`, `tipo`, `disciplina_id`, `nome`, `curso_id`, `ano_letivo`, `ativo`) VALUES
(1, 'TURMA_UNICA', 1, 'Programação 1 COM1A', 1, 2025, b'0'),
(2, 'TURMA_UNICA', 2, 'Calculo 1 MAT1A', 2, 2025, b'1');

-- --------------------------------------------------------

--
-- Estrutura para tabela `turma_aluno`
--

CREATE TABLE `turma_aluno` (
  `turma_id` bigint(20) NOT NULL,
  `aluno_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `turma_aluno`
--

INSERT INTO `turma_aluno` (`turma_id`, `aluno_id`) VALUES
(1, 1),
(2, 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `turma_professor`
--

CREATE TABLE `turma_professor` (
  `turma_id` bigint(20) NOT NULL,
  `professor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `turma_professor`
--

INSERT INTO `turma_professor` (`turma_id`, `professor_id`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `atividade_turma`
--

CREATE TABLE `atividade_turma` (
  `atividade_id` bigint(20) NOT NULL,
  `turma_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `atividade`
--

INSERT INTO `atividade_turma` (`atividade_id`, `turma_id`) VALUES
(1, 1),
(1, 2),
(2, 1);


-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `cargo` enum('ADMINISTRADOR','ALUNO','BIBLIOTECARIO','PESQUISADOR','PROFESSOR') DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `id_dados` bigint(20) NOT NULL,
  `id_matricula` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `cargo`, `senha`, `id_dados`, `id_matricula`) VALUES
(1, 'ALUNO', 'senha', 1, 1),
(2, 'PROFESSOR', 'senha123', 2, 2),
(3, 'PESQUISADOR', 'abc123', 3, 3),
(4, 'ALUNO', 'senhaSegura', 4, 4),
(5, 'BIBLIOTECARIO', 'livros@123', 5, 5),
(6, 'PROFESSOR', 'prof#2025', 6, 6),
(7, 'PESQUISADOR', 'pesq!456', 7, 7),
(8, 'ALUNO', 'aluno@2025', 8, 8),
(9, 'BIBLIOTECARIO', 'bib#senha', 9, 9),
(10, 'PROFESSOR', 'ensina123', 10, 10),
(11, 'ALUNO', '123aluno!', 11, 11),
(12, 'ADMINISTRADOR', 'root', 12, 12),
(13, 'ADMINISTRADOR', 'edson', 13, 13),
(14, 'ADMINISTRADOR', 'mafofo', 14, 14),
(15, 'PROFESSOR', 'html.php', 15, 15);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `afastamento`
--
ALTER TABLE `afastamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjluv6uen7b4jf9kjt71hp29y4` (`professor_id`);

--
-- Índices de tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `assinaturas`
--
ALTER TABLE `assinaturas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKptyue4cqc0qw05ggov7buawmh` (`documento_id`),
  ADD KEY `FK3em2vxh4af8j06wu6rp2re6t7` (`usuario_id`);

--
-- Índices de tabela `aula`
--
ALTER TABLE `aula`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnxilct2rqynmw6c2sh2w61w2s` (`id_disciplina`),
  ADD KEY `FK6hx7tp4pic1km9rcm9qo35t1n` (`id_turma`);

--
-- Índices de tabela `biblioteca`
--
ALTER TABLE `biblioteca`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `bibliotecario`
--
ALTER TABLE `bibliotecario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgrnrsoixt0bfnj0vorupy4ktg` (`id_biblioteca`);

--
-- Índices de tabela `biblioteca_livro`
--
ALTER TABLE `biblioteca_livro`
  ADD KEY `FK1j48414ryyigcuko4u2oa6rgo` (`livro_id`),
  ADD KEY `FKpb3lyk958t4g256rbx8n4tfst` (`biblioteca_id`);

--
-- Índices de tabela `boletim`
--
ALTER TABLE `boletim`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6m39h9x3qrjetbkn949gervx0` (`aluno_id`);

--
-- Índices de tabela `bolsa`
--
ALTER TABLE `bolsa`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `bolsista`
--
ALTER TABLE `bolsista`
  ADD KEY `FKim2n3yj4k1jwd7gpyw6tgwkwi` (`id_usuario`),
  ADD KEY `FKanhk3con2gqtx7btlat9859b8` (`id_bolsa`);

--
-- Índices de tabela `chefe_departamento`
--
ALTER TABLE `chefe_departamento`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `componente_curricular`
--
ALTER TABLE `componente_curricular`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKag90kwbxc3ko7d9ve9s8uwnyw` (`boletim_id`),
  ADD KEY `FKaljsguiv5qv8mta83a9rals39` (`disciplina_id`);

--
-- Índices de tabela `coordenador`
--
ALTER TABLE `coordenador`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKngc8ac8rvdeiyh5cxi3v6ctjg` (`coordenador_id`),
  ADD KEY `FK9kqc01uiq80nur59e5y5sghbk` (`departamento_id`);

--
-- Índices de tabela `dados_bancarios`
--
ALTER TABLE `dados_bancarios`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `departamento`
--
ALTER TABLE `departamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKefey7ddb9d0o9o40nbmho8jg7` (`coordenador_id`);

--
-- Índices de tabela `disciplina`
--
ALTER TABLE `disciplina`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkhdiw1swjoa2ml3md0mt8g4sf` (`curso_id`),
  ADD KEY `FKsnmpurmkvs693joxmqld4fq2b` (`departamento_id`);

--
-- Índices de tabela `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9o80s7i3wn6ks727ytgmudti4` (`id_livro`),
  ADD KEY `FKs8lirup1wisehyym648mgb5qg` (`id_usuario`);

--
-- Índices de tabela `lecionamento`
--
ALTER TABLE `lecionamento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKvem72eut81j6yn18ib3533yb` (`professor_id`),
  ADD KEY `FKe8i6rm3ojccubf537eejkjqvi` (`turma_id`);

--
-- Índices de tabela `lista_presenca`
--
ALTER TABLE `lista_presenca`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4iwj36hls1d288091c22e55n2` (`autor_id`),
  ADD KEY `FKsujgywijqc1bwktn03h1ntcy5` (`turma_id`);

--
-- Índices de tabela `livro`
--
ALTER TABLE `livro`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `material_didatico`
--
ALTER TABLE `material_didatico`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcurx4ekdk1k808n84jfogbghk` (`turma_id`);

--
-- Índices de tabela `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK133qjgbs681xntmnvxvg2g08w` (`curso_id`);

--
-- Índices de tabela `nescessidade_especial`
--
ALTER TABLE `nescessidade_especial`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKltfuixq6jip5pnsl6hbolnpki` (`aluno_id`);

--
-- Índices de tabela `nota`
--
ALTER TABLE `nota`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKc6khrg7ng6yactroft5tb76k5` (`componente_id`);

--
-- Índices de tabela `noticia`
--
ALTER TABLE `noticia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcutmub7bkoso37l0h2hukf0jh` (`autor_id`),
  ADD KEY `FK8lalpwf6i4h4ib6sn766vw88u` (`turma_id`);

--
-- Índices de tabela `pesquisador`
--
ALTER TABLE `pesquisador`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `presenca`
--
ALTER TABLE `presenca`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrkj8spu6xfxdk4it0dw4xb8v2` (`aluno_id`),
  ADD KEY `FK72vysvx6nw0ss94k7228j1n5t` (`lista_id`);

--
-- Índices de tabela `producao_academica`
--
ALTER TABLE `producao_academica`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlnxkunnkkgadruv4uai9rd2av` (`id_autor`);

--
-- Índices de tabela `professor`
--
ALTER TABLE `professor`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `root_user`
--
ALTER TABLE `root_user`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `sub_turma`
--
ALTER TABLE `sub_turma`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKehby1brcfawponpmfyjdrpf8h` (`super_turma_id`);

--
-- Índices de tabela `super_turma`
--
ALTER TABLE `super_turma`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `turma`
--
ALTER TABLE `turma`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeee13bbo1a87bo2i3bxxwvegm` (`disciplina_id`),
  ADD KEY `FKemy6du4jr6a56m5e5sp7nufe7` (`curso_id`);

--
-- Índices de tabela `turma_aluno`
--
ALTER TABLE `turma_aluno`
  ADD KEY `FKo4hb9mkklqma8p1p6dbpg790p` (`aluno_id`),
  ADD KEY `FK8jlmncan0ekxtbsjfdgwcigtn` (`turma_id`);

--
-- Índices de tabela `turma_professor`
--
ALTER TABLE `turma_professor`
  ADD KEY `FKlopoyd8l825id7etoq9pndf0f` (`professor_id`),
  ADD KEY `FKgebnucfia04rcb2rv3773hlhh` (`turma_id`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK7f86srg67oag2lhktlo4qmww4` (`id_dados`),
  ADD UNIQUE KEY `UKtcpft4dwlckmyugy8mg3d95y5` (`id_matricula`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `afastamento`
--
ALTER TABLE `afastamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `assinaturas`
--
ALTER TABLE `assinaturas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `aula`
--
ALTER TABLE `aula`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `biblioteca`
--
ALTER TABLE `biblioteca`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `boletim`
--
ALTER TABLE `boletim`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `bolsa`
--
ALTER TABLE `bolsa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `componente_curricular`
--
ALTER TABLE `componente_curricular`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `curso`
--
ALTER TABLE `curso`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `dados_bancarios`
--
ALTER TABLE `dados_bancarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `departamento`
--
ALTER TABLE `departamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `disciplina`
--
ALTER TABLE `disciplina`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `documento`
--
ALTER TABLE `documento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `emprestimo`
--
ALTER TABLE `emprestimo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `lecionamento`
--
ALTER TABLE `lecionamento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `lista_presenca`
--
ALTER TABLE `lista_presenca`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `livro`
--
ALTER TABLE `livro`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `material_didatico`
--
ALTER TABLE `material_didatico`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `matricula`
--
ALTER TABLE `matricula`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de tabela `nescessidade_especial`
--
ALTER TABLE `nescessidade_especial`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `nota`
--
ALTER TABLE `nota`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `noticia`
--
ALTER TABLE `noticia`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `presenca`
--
ALTER TABLE `presenca`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `producao_academica`
--
ALTER TABLE `producao_academica`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `turma`
--
ALTER TABLE `turma`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `FK2pojw9weqmkc0476cs86vyyrb` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `afastamento`
--
ALTER TABLE `afastamento`
  ADD CONSTRAINT `FKjluv6uen7b4jf9kjt71hp29y4` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`);

--
-- Restrições para tabelas `aluno`
--
ALTER TABLE `aluno`
  ADD CONSTRAINT `FKc8wsngo14dwn23nvgsty37bfx` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `assinaturas`
--
--
-- Restrições para tabelas `assinaturas`
--
ALTER TABLE `assinaturas`
  ADD CONSTRAINT `FK_assinatura_documento` FOREIGN KEY (`documento_id`) REFERENCES `documento` (`id`),
  ADD CONSTRAINT `FK_assinatura_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `aula`
--
ALTER TABLE `aula`
  ADD CONSTRAINT `FK6hx7tp4pic1km9rcm9qo35t1n` FOREIGN KEY (`id_turma`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKnxilct2rqynmw6c2sh2w61w2s` FOREIGN KEY (`id_disciplina`) REFERENCES `disciplina` (`id`);

--
-- Restrições para tabelas `bibliotecario`
--
ALTER TABLE `bibliotecario`
  ADD CONSTRAINT `FKgrnrsoixt0bfnj0vorupy4ktg` FOREIGN KEY (`id_biblioteca`) REFERENCES `biblioteca` (`id`),
  ADD CONSTRAINT `FKjg98wxd04jhn3cpjg34yk7rrw` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `biblioteca_livro`
--
ALTER TABLE `biblioteca_livro`
  ADD CONSTRAINT `FK1j48414ryyigcuko4u2oa6rgo` FOREIGN KEY (`livro_id`) REFERENCES `livro` (`id`),
  ADD CONSTRAINT `FKpb3lyk958t4g256rbx8n4tfst` FOREIGN KEY (`biblioteca_id`) REFERENCES `biblioteca` (`id`);

--
-- Restrições para tabelas `boletim`
--
ALTER TABLE `boletim`
  ADD CONSTRAINT `FK6m39h9x3qrjetbkn949gervx0` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`);

--
-- Restrições para tabelas `bolsista`
--
ALTER TABLE `bolsista`
  ADD CONSTRAINT `FKanhk3con2gqtx7btlat9859b8` FOREIGN KEY (`id_bolsa`) REFERENCES `bolsa` (`id`),
  ADD CONSTRAINT `FKim2n3yj4k1jwd7gpyw6tgwkwi` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `chefe_departamento`
--
ALTER TABLE `chefe_departamento`
  ADD CONSTRAINT `FKm0nlyuq48f7osuj04fj8gt5kl` FOREIGN KEY (`id`) REFERENCES `administrador` (`id`);

--
-- Restrições para tabelas `componente_curricular`
--
ALTER TABLE `componente_curricular`
  ADD CONSTRAINT `FKag90kwbxc3ko7d9ve9s8uwnyw` FOREIGN KEY (`boletim_id`) REFERENCES `boletim` (`id`),
  ADD CONSTRAINT `FKaljsguiv5qv8mta83a9rals39` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`);

--
-- Restrições para tabelas `coordenador`
--
ALTER TABLE `coordenador`
  ADD CONSTRAINT `FK2rum8bby7evrd4gjbj0psxe5x` FOREIGN KEY (`id`) REFERENCES `administrador` (`id`);

--
-- Restrições para tabelas `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `FK9kqc01uiq80nur59e5y5sghbk` FOREIGN KEY (`departamento_id`) REFERENCES `departamento` (`id`),
  ADD CONSTRAINT `FKngc8ac8rvdeiyh5cxi3v6ctjg` FOREIGN KEY (`coordenador_id`) REFERENCES `coordenador` (`id`);

--
-- Restrições para tabelas `departamento`
--
ALTER TABLE `departamento`
  ADD CONSTRAINT `FKefey7ddb9d0o9o40nbmho8jg7` FOREIGN KEY (`coordenador_id`) REFERENCES `chefe_departamento` (`id`);

--
-- Restrições para tabelas `disciplina`
--
ALTER TABLE `disciplina`
  ADD CONSTRAINT `FKkhdiw1swjoa2ml3md0mt8g4sf` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`),
  ADD CONSTRAINT `FKsnmpurmkvs693joxmqld4fq2b` FOREIGN KEY (`departamento_id`) REFERENCES `departamento` (`id`);

--
-- Restrições para tabelas `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `FK_documento_criador` FOREIGN KEY (`criador_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `emprestimo`
--
ALTER TABLE `emprestimo`
  ADD CONSTRAINT `FK9o80s7i3wn6ks727ytgmudti4` FOREIGN KEY (`id_livro`) REFERENCES `livro` (`id`),
  ADD CONSTRAINT `FKs8lirup1wisehyym648mgb5qg` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `lecionamento`
--
ALTER TABLE `lecionamento`
  ADD CONSTRAINT `FKe8i6rm3ojccubf537eejkjqvi` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKvem72eut81j6yn18ib3533yb` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`);

--
-- Restrições para tabelas `lista_presenca`
--
ALTER TABLE `lista_presenca`
  ADD CONSTRAINT `FK4iwj36hls1d288091c22e55n2` FOREIGN KEY (`autor_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKsujgywijqc1bwktn03h1ntcy5` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`);

--
-- Restrições para tabelas `material_didatico`
--
ALTER TABLE `material_didatico`
  ADD CONSTRAINT `FKcurx4ekdk1k808n84jfogbghk` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`);

--
-- Restrições para tabelas `matricula`
--
ALTER TABLE `matricula`
  ADD CONSTRAINT `FK133qjgbs681xntmnvxvg2g08w` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`);

--
-- Restrições para tabelas `nescessidade_especial`
--
ALTER TABLE `nescessidade_especial`
  ADD CONSTRAINT `FKltfuixq6jip5pnsl6hbolnpki` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`);

--
-- Restrições para tabelas `nota`
--
ALTER TABLE `nota`
  ADD CONSTRAINT `FKc6khrg7ng6yactroft5tb76k5` FOREIGN KEY (`componente_id`) REFERENCES `componente_curricular` (`id`);

--
-- Restrições para tabelas `noticia`
--
ALTER TABLE `noticia`
  ADD CONSTRAINT `FK8lalpwf6i4h4ib6sn766vw88u` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKcutmub7bkoso37l0h2hukf0jh` FOREIGN KEY (`autor_id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `pesquisador`
--
ALTER TABLE `pesquisador`
  ADD CONSTRAINT `FK9xgup65kiht980iwsc5w5v1tj` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `presenca`
--
ALTER TABLE `presenca`
  ADD CONSTRAINT `FK72vysvx6nw0ss94k7228j1n5t` FOREIGN KEY (`lista_id`) REFERENCES `lista_presenca` (`id`),
  ADD CONSTRAINT `FKrkj8spu6xfxdk4it0dw4xb8v2` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`);

--
-- Restrições para tabelas `producao_academica`
--
ALTER TABLE `producao_academica`
  ADD CONSTRAINT `FKlnxkunnkkgadruv4uai9rd2av` FOREIGN KEY (`id_autor`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `professor`
--
ALTER TABLE `professor`
  ADD CONSTRAINT `FKluxnu0qdopy9qdid4brfrw5wt` FOREIGN KEY (`id`) REFERENCES `usuario` (`id`);

--
-- Restrições para tabelas `root_user`
--
ALTER TABLE `root_user`
  ADD CONSTRAINT `FKeskrw25eb44bomv7h4wkp9c1m` FOREIGN KEY (`id`) REFERENCES `administrador` (`id`);

--
-- Restrições para tabelas `sub_turma`
--
ALTER TABLE `sub_turma`
  ADD CONSTRAINT `FK2ycsc5end1tf11012dksexytb` FOREIGN KEY (`id`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKehby1brcfawponpmfyjdrpf8h` FOREIGN KEY (`super_turma_id`) REFERENCES `super_turma` (`id`);

--
-- Restrições para tabelas `super_turma`
--
ALTER TABLE `super_turma`
  ADD CONSTRAINT `FKgyxddigj49886oqbibv5nyefn` FOREIGN KEY (`id`) REFERENCES `turma` (`id`);

--
-- Restrições para tabelas `turma`
--
ALTER TABLE `turma`
  ADD CONSTRAINT `FKeee13bbo1a87bo2i3bxxwvegm` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`),
  ADD CONSTRAINT `FKemy6du4jr6a56m5e5sp7nufe7` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`);

--
-- Restrições para tabelas `turma_aluno`
--
ALTER TABLE `turma_aluno`
  ADD CONSTRAINT `FK8jlmncan0ekxtbsjfdgwcigtn` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKo4hb9mkklqma8p1p6dbpg790p` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`);

--
-- Restrições para tabelas `turma_professor`
--
ALTER TABLE `turma_professor`
  ADD CONSTRAINT `FKgebnucfia04rcb2rv3773hlhh` FOREIGN KEY (`turma_id`) REFERENCES `turma` (`id`),
  ADD CONSTRAINT `FKlopoyd8l825id7etoq9pndf0f` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`);

--
-- Restrições para tabelas `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK48g2g7ev035s8fm4yob5t7thw` FOREIGN KEY (`id_dados`) REFERENCES `dados_bancarios` (`id`),
  ADD CONSTRAINT `FKqvhup74ienpc981aswibhj9u9` FOREIGN KEY (`id_matricula`) REFERENCES `matricula` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
