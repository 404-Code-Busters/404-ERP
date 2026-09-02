# Módulos do ERP - 404 ERP

## Estrutura de Módulos

O 404 ERP será organizado em módulos independentes, cada um responsável por uma área específica do negócio.

O sistema será desenvolvido como um ERP simples para uma empresa de tecnologia que presta serviços e também comercializa produtos.

## MVP

### 🟢 Funcionalidades essenciais

- Autenticação
  - Login
  - JWT
  - Perfis e permissões básicas

- Usuários
  - Cadastro
  - Edição
  - Ativação e desativação
  - Perfil

- Clientes
  - Cadastro
  - Endereços
  - Consulta
  - Histórico básico

- Fornecedores
  - Cadastro
  - Endereços
  - Consulta
  - Histórico básico

- Produtos
  - Cadastro
  - Preço de custo
  - Preço de venda
  - Fornecedor
  - Estoque mínimo
  - Status

- Estoque
  - Entrada
  - Saída
  - Ajuste
  - Consulta de movimentações

- Compras
  - Cadastro de compra
  - Itens
  - Recebimento
  - Atualização do estoque

- Vendas
  - Cadastro de venda
  - Produtos
  - Serviços
  - Descontos
  - Pagamento
  - Baixa do estoque

- Financeiro
  - Contas a pagar
  - Contas a receber
  - Pagamentos
  - Recebimentos
  - Status

- Dashboard
  - Vendas
  - Compras
  - Recebimentos
  - Pagamentos
  - Produtos com estoque baixo

### 🟡 Funcionalidades para evolução

- Relatórios mais avançados
- Parcelamentos mais elaborados
- Fluxo de caixa avançado
- Inventário completo
- Avaliação detalhada de fornecedores
- Variações de produtos
- Filtros e indicadores avançados

### 🔵 Funcionalidades futuras

- Emissão fiscal
- NF-e/NFS-e
- Integração bancária
- Múltiplos estoques
- BI e análises avançadas
- Integrações externas
- Aplicativo mobile
- Multiempresa
- Auditoria completa

---

## Próximos Passos

Após a definição dos módulos e do MVP, serão especificados:

- [ ] Entidades principais.
- [ ] Relacionamentos entre entidades.
- [ ] Estrutura inicial do banco de dados.
- [ ] Endpoints principais da API.
- [ ] Permissões de acesso.
- [ ] Fluxos principais do sistema.
- [ ] Estrutura inicial do backend.
- [ ] Estrutura inicial do frontend.

---

*Última atualização: 2026-09-01*

---

## 1. Autenticação e Segurança

### Objetivo

Controlar o acesso ao 404 ERP, garantindo que somente usuários autorizados possam utilizar o sistema.

### Funcionalidades

* Login com e-mail e senha.
* Autenticação utilizando JWT.
* Expiração e renovação do token.
* Logout.
* Recuperação e alteração de senha.
* Confirmação de e-mail.
* Bloqueio após tentativas inválidas.
* Controle de acesso por perfis e permissões.

### Regras principais

* Senhas nunca serão armazenadas em texto puro.
* Usuários não autenticados não poderão acessar recursos protegidos.
* Cada usuário possuirá um perfil de acesso.
* Perfis possuirão permissões.
* Usuários não poderão alterar ou elevar o próprio nível de acesso.
* Tokens possuirão tempo de expiração.
* Usuários bloqueados não poderão realizar login enquanto permanecerem bloqueados.

### Integrações

* Usuários.
* Spring Security.
* API REST.

### Observações

Os detalhes de segurança serão definidos durante a implementação.

---

## 2. Usuários

### Objetivo

Gerenciar os usuários que possuem acesso ao 404 ERP.

### Funcionalidades

* Cadastrar usuários.
* Editar usuários.
* Consultar usuários.
* Pesquisar e filtrar usuários.
* Ativar e inativar usuários.
* Alterar perfil de acesso.
* Redefinir senha.
* Alterar dados pessoais permitidos.
* Alterar a própria senha.

### Dados principais

* Nome.
* E-mail.
* Telefone.
* Senha.
* Perfil.
* Status.
* Data de cadastro.
* Último acesso.

### Regras principais

* Somente usuários autorizados poderão criar novos usuários.
* Cada usuário possuirá um único perfil.
* O e-mail será único.
* Usuários inativos não poderão acessar o sistema.
* Usuários não poderão elevar os próprios privilégios.
* Usuários inativados não serão excluídos fisicamente.
* O histórico relacionado ao usuário deverá ser preservado.

### Integrações

* Autenticação e Segurança.
* Demais módulos do ERP.

---

## 3. Clientes

### Objetivo

Gerenciar os clientes da empresa e seus dados utilizados nas vendas e no relacionamento comercial.

### Funcionalidades

* Cadastrar cliente.
* Editar cliente.
* Consultar cliente.
* Pesquisar e filtrar clientes.
* Cadastrar múltiplos endereços.
* Inativar cliente.
* Consultar histórico de compras.
* Consultar informações financeiras relacionadas ao cliente.

### Dados principais

* Nome completo.
* CPF.
* E-mail.
* Telefone.
* Data de nascimento.
* Endereços.
* Limite de crédito.
* Observações.
* Status.
* Data de cadastro.

### Regras principais

* CPF será obrigatório e único.
* O cliente poderá possuir mais de um endereço.
* O endereço poderá ser selecionado durante uma venda.
* A data de nascimento será armazenada.
* Clientes poderão possuir limite de crédito.
* Clientes inativos não deverão ser utilizados em novas vendas.
* O histórico do cliente deverá ser preservado.

### Integrações

* Vendas.
* Financeiro.
* Dashboard.
* Relatórios.

---

## 4. Fornecedores

### Objetivo

Gerenciar os fornecedores utilizados pela empresa para aquisição de produtos e serviços.

### Funcionalidades

* Cadastrar fornecedor.
* Editar fornecedor.
* Consultar fornecedor.
* Pesquisar e filtrar fornecedores.
* Cadastrar múltiplos endereços.
* Cadastrar dados bancários.
* Inativar fornecedor.
* Consultar histórico de compras.
* Registrar informações de avaliação.

### Dados principais

* Tipo de pessoa: PF ou PJ.
* CPF ou CNPJ.
* Nome ou razão social.
* Nome fantasia, quando aplicável.
* E-mail.
* Telefone.
* Endereços.
* Dados bancários.
* Observações.
* Status.
* Data de cadastro.

### Regras principais

* CPF/CNPJ será único.
* O documento deverá ser válido.
* O fornecedor poderá possuir múltiplos endereços.
* Dados bancários deverão possuir acesso controlado.
* Fornecedores inativos não deverão ser utilizados em novas compras.
* O histórico do fornecedor deverá ser preservado.
* Fornecedores com movimentações não serão excluídos fisicamente.

### Integrações

* Produtos.
* Compras.
* Estoque.
* Financeiro.

---

## 5. Produtos

### Objetivo

Gerenciar os produtos comercializados pela empresa.

### Funcionalidades

* Cadastrar produto.
* Editar produto.
* Consultar produto.
* Pesquisar e filtrar produtos.
* Definir preço de custo.
* Definir preço de venda.
* Associar fornecedor.
* Controlar estoque.
* Ativar e inativar produto.
* Registrar imagem do produto.
* Preparar estrutura para futuras variações de produtos.

### Dados principais

* Nome.
* Código interno.
* Código de barras, quando aplicável.
* Descrição.
* Categoria.
* Unidade de medida.
* Preço de custo.
* Preço de venda.
* Estoque mínimo.
* Estoque máximo.
* Fornecedor.
* Imagem.
* Status.

### Regras principais

* Cada produto deverá possuir um código interno único.
* O código de barras será opcional.
* Produtos inativos não poderão ser incluídos em novas vendas.
* Produtos comercializados deverão possuir controle de estoque.
* Um produto poderá estar associado a mais de um fornecedor.
* A estrutura deverá permitir suporte futuro a variações de produtos.

### Integrações

* Fornecedores.
* Compras.
* Estoque.
* Vendas.

---

## 6. Estoque

### Objetivo

Controlar a quantidade e a movimentação dos produtos armazenados pela empresa.

### Funcionalidades

* Consultar estoque.
* Registrar entrada de produtos.
* Registrar saída de produtos.
* Ajustar estoque.
* Consultar movimentações.
* Identificar produtos com estoque baixo.
* Controlar estoque mínimo.
* Associar movimentações às compras e vendas.

### Dados principais

* Produto.
* Quantidade atual.
* Estoque mínimo.
* Estoque máximo.
* Data da movimentação.
* Tipo de movimentação.
* Quantidade movimentada.
* Origem da movimentação.

### Regras principais

* Entradas e saídas deverão atualizar o estoque.
* Toda movimentação deverá possuir origem identificável.
* O sistema deverá permitir ajustes de estoque mediante permissão.
* O sistema deverá alertar quando o estoque atingir o nível mínimo.
* Produtos sem controle de estoque não deverão gerar movimentações de estoque.

### Integrações

* Produtos.
* Compras.
* Vendas.
* Dashboard.
* Relatórios.

---

## 7. Compras

### Objetivo

Gerenciar a aquisição de produtos e serviços junto aos fornecedores.

### Funcionalidades

* Criar compra.
* Selecionar fornecedor.
* Adicionar produtos.
* Informar quantidades e valores.
* Calcular total da compra.
* Registrar recebimento.
* Atualizar estoque após recebimento.
* Consultar histórico de compras.
* Cancelar compras conforme as regras do sistema.

### Dados principais

* Fornecedor.
* Produtos ou serviços.
* Quantidades.
* Valores.
* Descontos.
* Total.
* Data da compra.
* Status.
* Forma de pagamento.

### Regras principais

* Toda compra deverá possuir um fornecedor.
* Uma compra poderá possuir vários itens.
* O total deverá ser calculado pelo sistema.
* O estoque deverá ser atualizado quando os produtos forem recebidos.
* Compras canceladas não deverão permanecer como movimentações válidas de estoque.

### Integrações

* Fornecedores.
* Produtos.
* Estoque.
* Financeiro.

---

## 8. Vendas

### Objetivo

Gerenciar as vendas de produtos e serviços realizadas pela empresa.

### Funcionalidades

* Criar venda.
* Selecionar cliente.
* Adicionar produtos.
* Adicionar serviços.
* Informar quantidades.
* Aplicar descontos.
* Calcular total.
* Selecionar forma de pagamento.
* Registrar venda.
* Consultar histórico de vendas.
* Cancelar venda conforme as regras do sistema.

### Dados principais

* Cliente.
* Produtos.
* Serviços.
* Quantidades.
* Valores.
* Descontos.
* Total.
* Data da venda.
* Status.
* Forma de pagamento.

### Regras principais

* Uma venda poderá possuir produtos, serviços ou ambos.
* O total deverá ser calculado pelo sistema.
* Produtos vendidos deverão gerar saída de estoque.
* Serviços não deverão gerar movimentação de estoque.
* Vendas canceladas deverão seguir regras específicas para reversão de estoque e financeiro.
* O cliente poderá ser associado à venda.
* O limite de crédito deverá ser considerado em vendas a prazo, quando aplicável.

### Integrações

* Clientes.
* Produtos.
* Estoque.
* Financeiro.
* Dashboard.
* Relatórios.

---

## 9. Financeiro

### Objetivo

Controlar as movimentações financeiras básicas da empresa relacionadas às vendas, compras, pagamentos e recebimentos.

### Funcionalidades

* Registrar contas a receber.
* Registrar contas a pagar.
* Registrar pagamentos.
* Registrar recebimentos.
* Consultar movimentações financeiras.
* Consultar valores em aberto.
* Controlar vencimentos.
* Consultar situação financeira.

### Dados principais

* Tipo de movimentação.
* Descrição.
* Valor.
* Data de lançamento.
* Data de vencimento.
* Data de pagamento ou recebimento.
* Status.
* Forma de pagamento.
* Origem da movimentação.

### Regras principais

* Vendas poderão gerar contas a receber.
* Compras poderão gerar contas a pagar.
* Pagamentos e recebimentos deverão atualizar o status financeiro.
* Valores em aberto deverão permanecer identificados.
* Movimentações financeiras deverão possuir origem identificável.

### Integrações

* Clientes.
* Fornecedores.
* Compras.
* Vendas.
* Dashboard.
* Relatórios.

---

## 10. Dashboard

### Objetivo

Apresentar uma visão resumida das principais informações do ERP.

### Funcionalidades

* Exibir resumo de vendas.
* Exibir resumo de compras.
* Exibir situação financeira.
* Exibir quantidade de clientes.
* Exibir produtos com estoque baixo.
* Exibir indicadores básicos do sistema.

### Indicadores iniciais

* Vendas do período.
* Compras do período.
* Valores a receber.
* Valores a pagar.
* Clientes cadastrados.
* Produtos cadastrados.
* Produtos com estoque baixo.

### Integrações

* Clientes.
* Produtos.
* Estoque.
* Compras.
* Vendas.
* Financeiro.

---

## 11. Relatórios

### Objetivo

Permitir a consulta e visualização de informações relevantes do ERP.

### Funcionalidades

* Relatório de clientes.
* Relatório de fornecedores.
* Relatório de produtos.
* Relatório de estoque.
* Relatório de compras.
* Relatório de vendas.
* Relatório financeiro.

### Regras principais

* Relatórios deverão respeitar as permissões do usuário.
* Os dados apresentados deverão refletir as informações registradas no sistema.
* Os relatórios poderão possuir filtros por período, status e outros critérios relevantes.

### Integrações

* Clientes.
* Fornecedores.
* Produtos.
* Estoque.
* Compras.
* Vendas.
* Financeiro.

---

## Escopo inicial

O 404 ERP será desenvolvido inicialmente como um ERP simples, focado nas necessidades de uma empresa de tecnologia que comercializa produtos e presta serviços.

Funcionalidades avançadas poderão ser adicionadas futuramente conforme a necessidade do projeto.

O objetivo inicial é construir uma base funcional, organizada e escalável, sem implementar complexidades desnecessárias.

---

## Próximos Passos

Após a definição dos módulos, serão especificados:

* [ ] Entidades principais.
* [ ] Relacionamentos entre entidades.
* [ ] Estrutura inicial do banco de dados.
* [ ] Endpoints principais da API.
* [ ] Permissões de acesso.
* [ ] Fluxos principais do sistema.
* [ ] Escopo do MVP.

---

*Última atualização: 2026-09-01*
