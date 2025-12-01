-- 1. Criação da nova tabela para as opções de múltipla escolha
CREATE TABLE opcao_questao (
    id_opcao BIGSERIAL PRIMARY KEY,
    id_questao BIGINT NOT NULL REFERENCES questao(id_questao) ON DELETE CASCADE,
    letra_opcao VARCHAR(1) NOT NULL, -- Ex: 'A', 'B', 'C', 'D'
    texto_opcao VARCHAR(255) NOT NULL, -- O texto da opção
    CONSTRAINT UQ_QQ_LETRA UNIQUE (id_questao, letra_opcao) -- Garante que uma questão não tenha duas opções 'A'
);