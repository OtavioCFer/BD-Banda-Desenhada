CREATE TABLE quiz (
    id_quiz      BIGSERIAL PRIMARY KEY,
    titulo       VARCHAR(200) NOT NULL,
    descricao    TEXT
);

CREATE TABLE questao (
    id_questao   BIGSERIAL PRIMARY KEY,
    enunciado    TEXT NOT NULL,
    tipo         VARCHAR(20) NOT NULL CHECK (tipo IN ('MULTIPLA', 'TEXTO'))
);

CREATE TABLE quiz_questao (
    id_quiz      BIGINT NOT NULL REFERENCES quiz(id_quiz) ON DELETE CASCADE,
    id_questao   BIGINT NOT NULL REFERENCES questao(id_questao) ON DELETE CASCADE,
    PRIMARY KEY (id_quiz, id_questao)
);

CREATE TABLE resposta_quiz (
    id_resposta      BIGSERIAL PRIMARY KEY,
    id_quiz          BIGINT NOT NULL REFERENCES quiz(id_quiz) ON DELETE CASCADE,
    id_questao       BIGINT NOT NULL REFERENCES questao(id_questao) ON DELETE CASCADE,
    id_usuario       BIGINT NOT NULL REFERENCES usuario(id_usuario),
    resposta_texto   TEXT,
    resposta_opcao   VARCHAR(200),
    correta          BOOLEAN,
    data_resposta    TIMESTAMP NOT NULL DEFAULT NOW()
);
