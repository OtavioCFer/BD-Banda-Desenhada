TRUNCATE TABLE avaliacao RESTART IDENTITY CASCADE;
TRUNCATE TABLE quadrinho RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE genero RESTART IDENTITY CASCADE;
TRUNCATE TABLE autor RESTART IDENTITY CASCADE;
TRUNCATE TABLE editora RESTART IDENTITY CASCADE;

INSERT INTO editora (nome, pais) VALUES
('Marvel', 'EUA'),
('DC Comics', 'EUA'),
('Panini', 'Brasil'),
('Image Comics', 'EUA');

INSERT INTO usuario (nome, email, senha_hash) VALUES
('Otavio', 'otavio@test.com', '123'),
('Gabriel Guines', 'gg@test.com', '123'),
('Gabriel Lima', 'gabriellima@test.com', '123'),
('Xmalcon Gamer', 'xmalcon@test.com', '123');

INSERT INTO autor (nome, nacionalidade) VALUES
('Robert Kirkman', 'EUA'),
('Frank Miller', 'EUA'),
('Stan Lee', 'EUA'),
('Alan Moore', 'Reino Unido'),
('Mark Waid', 'EUA'),
('Jeff Lemire', 'Canadá');

INSERT INTO genero (nome) VALUES
('Super-Herói'),
('Ação'),
('Ficção Científica'),
('Terror'),
('Drama'),
('Fantasia');

INSERT INTO quadrinho 
(titulo, sinopse, numero_edicao, ano_edicao, paginas, isbn, capa_url, id_editora)
VALUES
('Invencível', 'A origem do maior herói do universo', 1, 2003, 45, '1111', '', 4),
('Batman Ano Um', 'Renascimento do Cavaleiro das Trevas', 1, 1987, 96, '2222', '', 2),
('Homem-Aranha: De Volta ao Lar', 'Peter Parker de volta ao básico', 1, 2017, 120, '3333', '', 1),
('Watchmen', 'O clássico dos quadrinhos adultos', 1, 1986, 400, '4444', '', 2);
