# Roteiro do vídeo — Challenge Sprint 4 (SOLIN · Clyvo Vet)

Até 15 minutos · todos aparecem e falam · app aberto em https://app-564099-solin.azurewebsites.net/solin/login

| Tempo | Quem | O que mostra na tela | Critério |
|---|---|---|---|
| 0:00–1:30 | Natália | Rosto de todos rapidamente; tela de login do SOLIN | Narrativa |
| 1:30–4:30 | Rodrigo | Login do tutor → cadastrar pet → registrar evento → alerta aparece | Demonstração |
| 4:30–6:30 | Nickolas | Login do veterinário → painel de alertas → marcar resolvido | Demonstração |
| 6:30–8:30 | Otávio | Azure Boards (backlog, release plan, casos de teste) → Selenium rodando | Integração (QA) |
| 8:30–10:30 | Samara | Sensor IoT e componente de IA | Integração (IoT/IA) |
| 10:30–12:30 | Natália | Portal da Azure: App Service e PostgreSQL; banco com os dados | Integração (DevOps/BD) |
| 12:30–14:00 | Rodrigo | Código: Strategy das regras de alerta, Flyway, Spring Security | Narrativa / decisões |
| 14:00–15:00 | Natália | Encerramento | Narrativa |

---

## 0:00 — Natália · Abertura

**Fala:** "Somos o grupo do SOLIN: Natália, Rodrigo, Nickolas, Otávio e Samara. O problema que a gente
atacou é que o tutor só percebe que o pet está mal quando já virou emergência: o cachorro passou o dia sem
urinar, o gato não bebeu água, e ninguém viu. O SOLIN registra a rotina do pet, à mão ou por um sensor, e
avisa o tutor e a Clyvo Vet quando algo sai do normal para aquela espécie."

**Tela:** cada um aparece por 2 ou 3 segundos; depois a tela de login aberta no link da Azure.

## 1:30 — Rodrigo · O tutor usando o sistema

1. Entrar com `tutor.demo@solin.com` / `demo123`.
   **Fala:** "Essa é a área do tutor, já publicada na Azure. O painel mostra quantos pets ele tem, quantos estão com alerta e o último evento."
2. Cadastrar pet → nome **Thor**, Cachorro, Labrador, 3 anos, Macho, 12.5 kg → Salvar.
   **Fala:** "Ao cadastrar, o pet já passa a ser monitorado."
3. Registrar evento → Thor, **Urinou**, Manual, data/hora = **9 horas atrás** → Salvar.
   **Fala:** "O limite do cachorro é 8 horas sem urinar. Como passaram 9, o SOLIN cria um alerta amarelo na hora. Se passasse de 16, seria vermelho."
4. Abrir "Assistente SOLIN" e clicar em uma pergunta pronta.
   **Fala:** "O assistente responde com os dados reais do pet."

## 4:30 — Nickolas · A clínica

1. Sair e entrar com `vet.demo@clyvovet.com.br` / `demo123`.
   **Fala:** "Essa é a visão da Clyvo Vet. O veterinário vê os alertas de todos os pets, os críticos primeiro."
2. Clicar em **Marcar resolvido** no alerta do Thor.
   **Fala:** "Depois de falar com o tutor, ele marca como resolvido e o alerta sai dos pendentes."
3. Mostrar que o tutor não entra no painel do veterinário (logado como tutor, abrir `/solin/vet/painel` → 403).
   **Fala:** "Cada perfil só vê a própria área, isso é o Spring Security."

## 6:30 — Otávio · Qualidade

1. Azure Boards → Backlog.
   **Fala:** "Aqui está o backlog em Scrum: épicos, features e itens, com esforço, prioridade, dependências e em qual sprint cada um foi entregue."
2. Abrir o caso de teste **CT09**.
   **Fala:** "São 12 casos de teste com dados fixos, cada um ligado ao item que ele testa. Esse aqui achou um defeito: registrar que o pet bebeu água repetia o alerta de urina. A gente corrigiu nesta sprint."
3. Mostrar o vídeo ou o terminal com o Selenium rodando os 4 testes.
   **Fala:** "E 4 desses casos rodam sozinhos no Selenium IDE."

## 8:30 — Samara · IoT e IA

**Fala:** "Além do registro manual, o evento pode vir do sensor: a coleira e o tapete inteligente, com ESP32
e sensor de umidade, mandam os eventos de urina em tempo real por MQTT. No SOLIN eles entram com origem SENSOR_IOT."
**Tela:** a simulação no Wokwi (link no repositório `solin-iot`).
**Fala:** "Na parte de IA, o SOLIN Insights usa esses dados do pet para recomendar ações ao tutor e à clínica." (ajustar com o que foi feito na entrega de IA da Sprint 4)
**Tela:** o documento do repositório `clyvo-vet-ia-sprint3`.

## 10:30 — Natália · Nuvem e banco

1. Portal da Azure → grupo `rg-564099-solin`.
   **Fala:** "O app roda num App Service com Java 17, e o banco é um PostgreSQL gerenciado da Azure. Tudo foi criado por um script com um comando por passo."
2. Mostrar o banco (tabelas `TB_PET`, `TB_EVENTO`, `TB_ALERTA`) com o Thor e o alerta que criamos agora.
   **Fala:** "O Flyway cria e versiona as tabelas; aqui estão os dados que acabamos de cadastrar."

## 12:30 — Rodrigo · Decisões técnicas

**Fala:** "Cada regra de alerta é uma classe separada, no padrão Strategy. Quando entrou a regra de
hidratação, a gente só criou duas classes novas, sem mexer em nada que já existia. O banco é versionado
pelo Flyway, e as senhas são guardadas com BCrypt."
**Tela:** pasta `strategy/` no GitHub.

## 14:00 — Natália · Encerramento

**Fala:** "O SOLIN junta o que vimos no semestre: Java e Spring no sistema, banco relacional versionado,
nuvem na Azure, testes e qualidade no Azure Boards e no Selenium, IoT e IA. Obrigada!"

---

**Antes de gravar:** abrir o link do app, o Boards, o portal da Azure e o GitHub em abas; testar o login.
**YouTube:** subir como **Não listado** (privado o professor não abre).
