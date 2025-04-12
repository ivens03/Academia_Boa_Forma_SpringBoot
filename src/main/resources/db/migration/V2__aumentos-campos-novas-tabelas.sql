-- Aumentos campos da tabela alunos
-- Criação da tabela pagamentos alunos

-- Aumentos campos da tabela alunos
-- Criação da tabela pagamentos alunos

CREATE TABLE IF NOT EXISTS alunos
(
    idAluno            SERIAL PRIMARY KEY, -- Identificador do aluno (auto-increment)
    nomeAluno          VARCHAR(255),       -- Nome do aluno
    idadeAluno         SMALLINT,           -- Idade do aluno (representado como Byte em Java)
    senhaAluno         VARCHAR(255),       -- Senha do aluno
    numeroCelularAluno VARCHAR(255),       -- Número de celular do aluno
    numeroEnergencia   VARCHAR(255),       -- Número de emergência
    doenca             BOOLEAN,            -- Indica se o aluno possui doença
    descricaoDoenca    VARCHAR(255),       -- Descrição da doença, se aplicável
    rua                VARCHAR(255),       -- Endereço: rua (campo embutido de Endereco)
    cidade             VARCHAR(255),       -- Endereço: cidade
    estado             VARCHAR(255),       -- Endereço: estado
    cep                VARCHAR(50),        -- Endereço: CEP
    objetivoAluno      VARCHAR(50)
);

-- Criação da tabela "pagamentosAlunos"
CREATE TABLE IF NOT EXISTS pagamentosAlunos
(
    idPagamento  SERIAL PRIMARY KEY, -- Identificador do pagamento (auto-increment)
    idAluno      INTEGER,            -- Chave estrangeira para "alunos" baseada no idAluno
    nomeAluno    VARCHAR(255),       -- Nome do aluno (apenas para registro/consulta, sem restrição FK)
    diaPagamento DATE,               -- Data agendada para o pagamento
    diaPago      DATE,               -- Data em que o pagamento foi efetuado
    CONSTRAINT fk_pagamento_idAluno FOREIGN KEY (idAluno) REFERENCES alunos (idAluno)
);

