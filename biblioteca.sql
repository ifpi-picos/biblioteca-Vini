
-- Criação do banco de dados (execute separadamente se necessário)
-- CREATE DATABASE biblioteca;

-- Conecte-se manualmente ao banco 'biblioteca' antes de executar o restante
-- via: \c biblioteca (no psql) ou selecione o banco na interface gráfica

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    tipo_notificacao VARCHAR(10) NOT NULL CHECK (tipo_notificacao IN ('email', 'sms')),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Livros
CREATE TABLE IF NOT EXISTS livros (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INTEGER,
    isbn VARCHAR(20) UNIQUE,
    disponivel BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    livro_id INTEGER NOT NULL REFERENCES livros(id) ON DELETE CASCADE,
    data_emprestimo DATE NOT NULL DEFAULT CURRENT_DATE,
    data_devolucao DATE NOT NULL,
    data_devolucao_efetiva DATE,
    status VARCHAR(20) DEFAULT 'ativo' CHECK (status IN ('ativo', 'concluído', 'atrasado')),
    multa DECIMAL(10,2) DEFAULT 0.00,
    CONSTRAINT chk_datas CHECK (data_devolucao > data_emprestimo)
);

-- Índices para melhor performance
CREATE INDEX idx_emprestimos_usuario ON emprestimos(usuario_id);
CREATE INDEX idx_emprestimos_livro ON emprestimos(livro_id);
CREATE INDEX idx_emprestimos_status ON emprestimos(status);

-- Dados iniciais (opcional)
INSERT INTO usuarios (nome, email, telefone, tipo_notificacao) VALUES
('João Silva', 'joao@email.com', '11999999999', 'email'),
('Maria Souza', 'maria@email.com', '21988888888', 'sms');

INSERT INTO livros (titulo, autor, ano_publicacao, isbn) VALUES
('Dom Casmurro', 'Machado de Assis', 1899, '9788535910663'),
('1984', 'George Orwell', 1949, '9788522106169'),
('O Senhor dos Anéis', 'J.R.R. Tolkien', 1954, '9788533603149');

INSERT INTO emprestimos (usuario_id, livro_id, data_devolucao, status) VALUES
(1, 1, CURRENT_DATE + INTERVAL '14 days', 'ativo'),
(2, 3, CURRENT_DATE + INTERVAL '10 days', 'ativo');

-- View para empréstimos ativos
CREATE VIEW vw_emprestimos_ativos AS
SELECT e.id, u.nome AS usuario, l.titulo AS livro, 
       e.data_emprestimo, e.data_devolucao,
       (e.data_devolucao - CURRENT_DATE) AS dias_restantes
FROM emprestimos e
JOIN usuarios u ON e.usuario_id = u.id
JOIN livros l ON e.livro_id = l.id
WHERE e.status = 'ativo';

-- Função para calcular multa
CREATE OR REPLACE FUNCTION calcular_multa()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_devolucao_efetiva > NEW.data_devolucao THEN
        NEW.multa := (NEW.data_devolucao_efetiva - NEW.data_devolucao) * 2.00; -- R$2 por dia
        NEW.status := 'atrasado';
    ELSE
        NEW.status := 'concluído';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para atualização de devolução
CREATE TRIGGER trg_atualizar_multa
BEFORE UPDATE OF data_devolucao_efetiva ON emprestimos
FOR EACH ROW
EXECUTE FUNCTION calcular_multa();

-- Procedure para renovar empréstimo
CREATE OR REPLACE PROCEDURE renovar_emprestimo(
    p_emprestimo_id INTEGER,
    p_dias_adicionales INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE emprestimos
    SET data_devolucao = data_devolucao + (p_dias_adicionales || ' days')::INTERVAL
    WHERE id = p_emprestimo_id AND status = 'ativo';
    
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Empréstimo não encontrado ou já finalizado';
    END IF;
END;
$$;