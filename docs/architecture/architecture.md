# Arquitetura Técnica - 404 ERP

## Visão Geral da Arquitetura

O 404 ERP adota uma arquitetura em camadas seguindo o modelo cliente-servidor, com separação clara entre frontend e backend, comunicação via REST API e persistência de dados em banco relacional.

## Fluxo Conceitual

```
┌─────────────────────────────────────────┐
│   Frontend                              │
│   Vue 3 + TypeScript                    │
└──────────────────┬──────────────────────┘
                   │ HTTP/REST
                   ↓
┌─────────────────────────────────────────┐
│   API REST                              │
│   (Interface de Comunicação)            │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│   Backend                               │
│   Spring Boot                           │
│   - Spring Security (Autenticação)      │
│   - JWT (Tokens)                        │
│   - Controllers & Services              │
│   - Lógica de Negócio                   │
└──────────────────┬──────────────────────┘
                   │ SQL
                   ↓
┌─────────────────────────────────────────┐
│   Camada de Persistência                │
│   JPA / Hibernate                       │
└──────────────────┬──────────────────────┘
                   │
                   ↓
┌─────────────────────────────────────────┐
│   Banco de Dados                        │
│   PostgreSQL                            │
└─────────────────────────────────────────┘
```

## Componentes Principais

### 1. Frontend (Vue 3 + TypeScript)

**Responsabilidades:**
- Interface de usuário responsiva e intuitiva
- Consumo da REST API
- Autenticação via tokens JWT
- Validação de entrada
- State management (a definir)

**Características:**
- Tipagem forte via TypeScript
- Componentes reutilizáveis
- Comunicação com backend via HTTP

### Planejamento aprovado para o frontend

O frontend será uma SPA em **Vue 3**, **TypeScript** e **Vite**, consumindo a API REST do backend. A navegação será feita com Router e a organização será por funcionalidades/domínios. Uma camada compartilhada concentrará comunicação HTTP, componentes reutilizáveis, composables e utilitários.

Estrutura planejada:

```text
frontend/
    src/
        app/
            App.vue
            main.ts
            router/
        layouts/
        shared/
            api/
            components/
            composables/
            utils/
        features/
            categorias/
            clientes/
            fornecedores/
            produtos/
            estoque/
            movimentacoes-estoque/
            compras/
            vendas/
```

O shell previsto usa sidebar persistente, header, breadcrumb e área principal, com layout responsivo, foco em desktop/notebook e adaptação para tablet/mobile. A Fase 1 contempla Categorias e Produtos dentro desse shell.

#### Design base aprovado

- Background: `#F8FAFC`
- Surface: `#FFFFFF`
- Border: `#E5E7EB`
- Primary: `#256EDC`
- Text: `#0F172A`
- Secondary text: `#475569`
- Muted text: `#64748B`
- Tipografia: Inter

Dashboard, autenticação e financeiro não fazem parte da Fase 1. O frontend não deve simular funcionalidades que o backend ainda não oferece.

### 2. API REST

**Responsabilidades:**
- Contrato padronizado entre frontend e backend
- Validação de requisições
- Resposta em formato JSON
- Versionamento (a definir)

**Padrões:**
- HTTP REST (GET, POST, PUT, DELETE, PATCH)
- Status HTTP semânticos
- Tratamento de erros padronizado

### 3. Backend (Spring Boot)

**Responsabilidades:**
- Processamento de requisições HTTP
- Autenticação e Autorização (Spring Security + JWT)
- Lógica de negócio
- Validação de dados
- Orquestração de operações complexas

**Componentes Spring:**
- Controllers: Recebimento de requisições
- Services: Lógica de negócio
- Repositories: Acesso a dados via JPA
- Entities: Modelos de domínio

### 4. Persistência (JPA / Hibernate)

**Responsabilidades:**
- Mapeamento objeto-relacional
- Conversão entre objetos Java e tabelas SQL
- Queries otimizadas
- Transações ACID

### 5. Banco de Dados (PostgreSQL)

**Responsabilidades:**
- Armazenamento permanente de dados
- Integridade referencial
- Desempenho e escalabilidade
- Backup e recuperação

**Características:**
- Suporte a ACID
- Relacionamentos complexos
- Extensibilidade e tipos de dados avançados

## Segurança

### Autenticação
- **Mecanismo**: Spring Security + JWT
- **Fluxo**: Credenciais → Token JWT → Requisições autenticadas
- **Tokens**: Portadores em header Authorization

### Autorização
- Controle de acesso baseado em roles/perfis (a definir)
- Validação de permissões em endpoints
- Proteção de dados sensíveis

## Decisões Arquiteturais Pendentes

- [ ] State management do frontend (Vuex, Pinia, etc)
- [ ] Versionamento da API
- [ ] Estrutura de módulos/plugins do backend
- [ ] Strategy de logging e monitoring
- [ ] Tratamento global de erros e exceções
- [ ] Caching (Redis, etc)
- [ ] Filas de mensagens (se necessário)
- [ ] Strategy de testes (unitários, integração, e2e)

---

*Última atualização: 2026-09-01*