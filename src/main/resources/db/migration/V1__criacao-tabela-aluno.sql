CREATE TABLE if not exists alunos
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
    objetivoAluno      VARCHAR(50)         -- Objeitvo do aluno
);
