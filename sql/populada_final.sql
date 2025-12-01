INSERT INTO usuario (nome, email, senha_hash)
VALUES
('Xmalcon Gamer dos Santos', 'XmalconFullPowerX@example.com', 'hash_teste'),
('Maria Aparecida', 'maria@example.com', 'hash_teste'),
('Labirinto', 'carlos@example.com', 'hash_teste'),
('Anna Pereira', 'ana@example.com', 'hash_teste');

INSERT INTO quiz (titulo, descricao)
VALUES
('Quiz de Teste - Relatórios', 'Quiz usado para testar os relatórios gerais de desempenho.');

INSERT INTO questao (enunciado, tipo, resposta_correta)
VALUES
('Qual editora publica o Homem-Aranha?', 'MULTIPLA', 'Marvel');

INSERT INTO questao (enunciado, tipo, resposta_correta)
VALUES
('Quem é o autor principal de Watchmen?', 'MULTIPLA', 'Alan Moore');

INSERT INTO questao (enunciado, tipo, resposta_correta)
VALUES
('Descreva em poucas palavras o que achou da narrativa de Watchmen.', 'TEXTO', NULL);

INSERT INTO opcao_questao (id_questao, letra_opcao, texto_opcao)
VALUES
(1, 'A', 'Marvel'),
(1, 'B', 'DC'),
(1, 'C', 'Image'),
(1, 'D', 'Dark Horse');

INSERT INTO opcao_questao (id_questao, letra_opcao, texto_opcao)
VALUES
(2, 'A', 'Alan Moore'),
(2, 'B', 'Frank Miller'),
(2, 'C', 'Grant Morrison'),
(2, 'D', 'Neil Gaiman');

INSERT INTO quiz_questao (id_quiz, id_questao)
VALUES
(1, 1),
(1, 2),
(1, 3);

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 1, 'Marvel', NULL, TRUE, NOW() - INTERVAL '5 days'),
(1, 2, 1, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '5 days'),
(1, 3, 1, NULL, 'Narrativa excelente e profunda.', TRUE, NOW() - INTERVAL '5 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 1, 'DC', NULL, FALSE, NOW() - INTERVAL '4 days'),
(1, 2, 1, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '4 days'),
(1, 3, 1, NULL, 'Boa narrativa, mas um pouco densa.', TRUE, NOW() - INTERVAL '4 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 1, 'Marvel', NULL, TRUE, NOW() - INTERVAL '3 days'),
(1, 2, 1, 'Frank Miller', NULL, FALSE, NOW() - INTERVAL '3 days'),
(1, 3, 1, NULL, 'Gostei da narrativa, final impactante.', TRUE, NOW() - INTERVAL '3 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 1, 'Marvel', NULL, TRUE, NOW() - INTERVAL '2 days'),
(1, 2, 1, 'Neil Gaiman', NULL, FALSE, NOW() - INTERVAL '2 days'),
(1, 3, 1, NULL, 'Boa, mas não é meu estilo.', TRUE, NOW() - INTERVAL '2 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 2, 'Marvel', NULL, TRUE, NOW() - INTERVAL '5 days'),
(1, 2, 2, 'Frank Miller', NULL, FALSE, NOW() - INTERVAL '5 days'),
(1, 3, 2, NULL, 'Narrativa muito densa, mas interessante.', TRUE, NOW() - INTERVAL '5 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 2, 'Marvel', NULL, TRUE, NOW() - INTERVAL '4 days'),
(1, 2, 2, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '4 days'),
(1, 3, 2, NULL, 'História complexa, gostei muito.', TRUE, NOW() - INTERVAL '4 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 2, 'DC', NULL, FALSE, NOW() - INTERVAL '3 days'),
(1, 2, 2, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '3 days'),
(1, 3, 2, NULL, 'Narrativa pesada, mas bem escrita.', TRUE, NOW() - INTERVAL '3 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 2, 'Marvel', NULL, TRUE, NOW() - INTERVAL '1 day'),
(1, 2, 2, 'Grant Morrison', NULL, FALSE, NOW() - INTERVAL '1 day'),
(1, 3, 2, NULL, 'Gostei, mas prefiro algo mais leve.', TRUE, NOW() - INTERVAL '1 day');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 3, 'Image', NULL, FALSE, NOW() - INTERVAL '6 days'),
(1, 2, 3, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '6 days'),
(1, 3, 3, NULL, 'Achei boa, mas confusa em alguns pontos.', TRUE, NOW() - INTERVAL '6 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 3, 'Marvel', NULL, TRUE, NOW() - INTERVAL '4 days'),
(1, 2, 3, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '4 days'),
(1, 3, 3, NULL, 'Muito boa, vale releitura.', TRUE, NOW() - INTERVAL '4 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 3, 'DC', NULL, FALSE, NOW() - INTERVAL '2 days'),
(1, 2, 3, 'Frank Miller', NULL, FALSE, NOW() - INTERVAL '2 days'),
(1, 3, 3, NULL, 'Não é muito meu estilo.', TRUE, NOW() - INTERVAL '2 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 3, 'Marvel', NULL, TRUE, NOW()),
(1, 2, 3, 'Alan Moore', NULL, TRUE, NOW()),
(1, 3, 3, NULL, 'Agora gostei bem mais da história.', TRUE, NOW());

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 4, 'Marvel', NULL, TRUE, NOW() - INTERVAL '7 days'),
(1, 2, 4, 'Neil Gaiman', NULL, FALSE, NOW() - INTERVAL '7 days'),
(1, 3, 4, NULL, 'Achei a narrativa interessante, porém lenta.', TRUE, NOW() - INTERVAL '7 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 4, 'Marvel', NULL, TRUE, NOW() - INTERVAL '5 days'),
(1, 2, 4, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '5 days'),
(1, 3, 4, NULL, 'História excelente, personagens marcantes.', TRUE, NOW() - INTERVAL '5 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 4, 'DC', NULL, FALSE, NOW() - INTERVAL '3 days'),
(1, 2, 4, 'Grant Morrison', NULL, FALSE, NOW() - INTERVAL '3 days'),
(1, 3, 4, NULL, 'Gostei, mas é bem pesada.', TRUE, NOW() - INTERVAL '3 days');

INSERT INTO resposta_quiz (id_quiz, id_questao, id_usuario, resposta_opcao, resposta_texto, correta, data_resposta)
VALUES
(1, 1, 4, 'Marvel', NULL, TRUE, NOW() - INTERVAL '1 day'),
(1, 2, 4, 'Alan Moore', NULL, TRUE, NOW() - INTERVAL '1 day'),
(1, 3, 4, NULL, 'Uma das minhas HQs favoritas agora.', TRUE, NOW() - INTERVAL '1 day');

