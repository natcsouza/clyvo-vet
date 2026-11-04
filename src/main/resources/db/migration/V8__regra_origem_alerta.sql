-- Guarda qual regra (Strategy) gerou cada alerta.
-- Com isso o AlertaService deixa de criar um alerta repetido quando ja
-- existe um alerta pendente da mesma regra para o mesmo pet.
ALTER TABLE TB_ALERTA ADD COLUMN DS_REGRA VARCHAR(60);

COMMENT ON COLUMN TB_ALERTA.DS_REGRA IS 'Nome da regra (Strategy) que gerou o alerta';
