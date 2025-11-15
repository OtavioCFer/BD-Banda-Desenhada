INSERT INTO editora (nome, pais)
VALUES 
  ('Panini Comics', 'Brasil'),
  ('Marvel Comics', 'Estados Unidos'),
  ('DC Comics', 'Estados Unidos')
ON CONFLICT DO NOTHING;

INSERT INTO quadrinho (titulo, sinopse, numero_edicao, ano_edicao, paginas, isbn, capa_url, id_editora)
VALUES
  ('Homem-Aranha: De Volta ao Lar',
   'Peter Parker equilibrando vida escolar e heroica.',
   1, 2017, 128, '9781234567890', NULL,
   (SELECT id_editora FROM editora WHERE nome = 'Marvel Comics')),

  ('Batman: Ano Um',
   'A origem moderna do Cavaleiro das Trevas em Gotham.',
   1, 1987, 96, '9789876543210', NULL,
   (SELECT id_editora FROM editora WHERE nome = 'Panini Comics')),

  ('Absolute Batman',
   'Sem a mansão... sem a fortuna... sem o mordomo... tudo o que resta é o Cavaleiro das Trevas Absoluto',
   1, 2024, 48, NULL, NULL,
   (SELECT id_editora FROM editora WHERE nome = 'DC Comics'));   