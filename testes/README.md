# Testes de sistema do SOLIN — Sprint 4

Compliance, Quality Assurance & Tests · Challenge 2026 Clyvo Vet · FIAP 2TDSR

## Links da entrega

| O quê | Link |
|---|---|
| Plano de testes manuais (Azure Boards) | https://dev.azure.com/RM564099/SOLIN%20-%20Clyvo%20Vet/_workitems |
| Vídeo da configuração e execução dos testes automatizados | `[link do YouTube]` |
| Testes automatizados (Selenium IDE) | [`selenium/SOLIN-testes.side`](selenium/SOLIN-testes.side) |

## Parte A — Testes manuais no Azure Boards

No projeto **SOLIN - Clyvo Vet** estão o backlog (épicos, features e PBIs), o release plan
(Sprints 1 a 4) e 12 casos de teste (tipo *Test Case*). Cada caso tem dados de entrada fixos,
saída esperada e passos, e está ligado ao PBI que testa (link *Tests / Tested By*).

| Caso | O que testa | PBI | Sprint do PBI |
|---|---|---|---|
| CT01 | Criar conta de tutor com dados válidos | Criar conta de tutor | 3 |
| CT02 | Recusar cadastro com confirmação de senha diferente | Criar conta de tutor | 3 |
| CT03 | Login de tutor com credencial válida | Login e permissões por perfil | 3 |
| CT04 | Login recusado com senha incorreta | Login e permissões por perfil | 3 |
| CT05 | Cadastrar pet com dados válidos | Tutor cadastra pet pela tela | 3 |
| CT06 | Recusar pet sem peso | Tutor cadastra pet pela tela | 3 |
| CT07 | Cachorro 9 h sem urinar gera alerta AMARELO | Tutor registra evento pela tela | 3 |
| CT08 | Cachorro 17 h sem urinar gera alerta VERMELHO | Alerta de urina amarelo e vermelho | 2 |
| CT09 | Cachorro 13 h sem água gera alerta e não repete o de urina | Alerta de hidratação | 3 |
| CT10 | Veterinário resolve alerta crítico | Veterinário marca alerta como resolvido | 3 |
| CT11 | Tutor não acessa o painel do veterinário | Login e permissões por perfil | 3 |
| CT12 | Remover pet mantém histórico | Remover pet sem perder histórico | 3 |

Os 12 casos foram executados em 03/11/2026 com o banco zerado (migrations Flyway) e passaram.
O CT09 encontrou um defeito: registrar "bebeu água" repetia o alerta de urina já pendente.
A correção está no PBI "Não repetir alerta que ainda está pendente" (Sprint 4).

## Parte B — Testes automatizados (Selenium IDE)

O SOLIN tem telas, então a automação é Record & Playback no **Selenium IDE**. O arquivo
`selenium/SOLIN-testes.side` tem uma suíte com 4 casos:

| Teste | Caso manual | Verifica |
|---|---|---|
| CT03 - Login de tutor com credencial valida | CT03 | Entra e abre "Meus pets" |
| CT05 - Cadastrar pet com dados validos | CT05 | "Pet Thor cadastrado com sucesso." |
| CT07 - Evento de urina ha 9 h gera alerta AMARELO | CT07 | Mensagem do alerta de 9 h (limite 8 h) |
| CT10 - Veterinario resolve alerta critico | CT10 | "marcado como resolvido" |

Os testes usam as contas de demonstração criadas pela migration V4
(`tutor.demo@solin.com` e `vet.demo@clyvovet.com.br`, senha `demo123`).

### Como rodar

**No Selenium IDE** (extensão do Chrome): *Open project* → `SOLIN-testes.side` →
trocar a URL base para a do SOLIN → *Run all tests*.

**Pela linha de comando:**

```bash
npx selenium-side-runner --base-url https://app-564099-solin.azurewebsites.net selenium/SOLIN-testes.side
```

### Rodar o SOLIN local com o banco zerado

```bash
./testes/reset-local.sh   # sobe Postgres em Docker (porta 5433) e o SOLIN em http://localhost:8080/solin
```
