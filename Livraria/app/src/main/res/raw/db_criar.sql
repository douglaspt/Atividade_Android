CREATE TABLE categoria (
    id integer NOT NULL PRIMARY KEY,
    nome varchar(60) NOT NULL
);

CREATE TABLE livro (
    id integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    categoria_id integer NOT NULL,
    titulo varchar(60) NOT NULL,
    autor varchar(60) NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);