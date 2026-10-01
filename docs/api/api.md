# API REST - 404 ERP

## Visão Geral

O 404 ERP utiliza uma **API REST** como canal de comunicação entre o frontend (Vue 3 + TypeScript) e o backend (Spring Boot). A API segue princípios RESTful para fornecer uma interface clara, previsível e escalável.

---

## Princípios REST

### Recursos

Toda operação no sistema é modelada como um recurso identificável:
- Clientes
- Fornecedores
- Produtos
- Pedidos
- Faturas
- Usuários
- etc

### Operações HTTP

| Método | Semântica | Uso |
|--------|-----------|-----|
| GET | Leitura | Recuperar um ou mais recursos |
| POST | Criação | Criar um novo recurso |
| PUT | Atualização | Atualizar um recurso completo |
| PATCH | Atualização parcial | Atualizar parcialmente um recurso |
| DELETE | Remoção | Deletar um recurso |

---

## Estrutura de Respostas

### Formato

Todas as respostas devem estar em formato **JSON**.

### Modelo Padrão de Resposta (Sucesso)

A definir

### Modelo Padrão de Resposta (Erro)

A definir

### Status HTTP

A definir

---

## Autenticação e Autorização

### Mecanismo

- **Tipo**: Bearer Token (JWT)
- **Header**: `Authorization: Bearer <token>`
- **Duração do Token**: A definir
- **Refresh Token**: A definir

### Fluxo de Autenticação

A definir

### Validação de Permissões

A definir

---

## Versionamento da API

**Estratégia**: A definir

**Formato**: A definir

Exemplos:
- URL path versioning: `/api/v1/clientes`
- Query parameter: `/api/clientes?version=1`
- Header versioning: `Accept: application/vnd.404erp.v1+json`

---

## Endpoints por Módulo

### Autenticação

A definir

### Usuários

A definir

### Clientes

A definir

### Categorias

Base: `/categorias`. Os controllers retornam entidades diretamente; os exemplos abaixo representam o formato derivado do código atual, não uma amostra validada em runtime.

#### `GET /categorias`

Retorna `200 OK` com uma lista JSON de categorias. Campos: `id` (número inteiro gerado), `nome` (string, obrigatório, único, até 100 caracteres), `descricao` (string ou `null`, opcional) e `status` (boolean, não nulo, default `true`).

#### `GET /categorias/{id}`

Retorna `200 OK` com a categoria quando encontrada ou `404 Not Found` quando não existir.

#### `POST /categorias`

Recebe a entidade `Categoria` diretamente. `nome` é obrigatório; `descricao` é opcional; `status` é opcional e inicia como `true`; `id` é gerado e deve ser omitido. Retorna o objeto salvo. Como o método retorna a entidade diretamente, o status esperado pelo código atual é `200 OK`.

Exemplo de resposta derivado do modelo atual:

```json
{
	"id": 1,
	"nome": "Acessorios",
	"descricao": "Produtos acessórios",
	"status": true
}
```

Não há endpoints `PUT` ou `DELETE` para categorias.

O controller não usa `@Valid`, portanto não há validação do request na camada MVC. A integração de Bean Validation na persistência pode validar a entidade ao salvar; o status e o formato HTTP desse erro não estão padronizados.

### Fornecedores

A definir

### Produtos

Base: `/produtos`. O controller recebe e retorna a entidade `Produto` diretamente.

#### Campos

| Campo | Tipo JSON esperado | Obrigatoriedade, nulabilidade e default |
|---|---|---|
| `id` | número inteiro (`Long`) | Gerado; omitir ao criar |
| `categoria` | objeto Categoria | Obrigatório; `ManyToOne` |
| `nome` | string | Obrigatório; máximo 150 caracteres |
| `codigoInterno` | string | Obrigatório, único; máximo 50 caracteres |
| `codigoBarras` | string ou `null` | Opcional, único; máximo 50 caracteres |
| `descricao` | string ou `null` | Opcional; coluna `TEXT` |
| `unidadeMedida` | string | Obrigatório; máximo 10 caracteres |
| `precoCusto` | número JSON (`BigDecimal`) | Obrigatório; precisão 12, escala 2 |
| `precoVenda` | número JSON (`BigDecimal`) | Obrigatório; precisão 12, escala 2 |
| `estoqueMinimo` | número JSON (`BigDecimal`) | Default Java `0`; entidade usa `@NotNull`; default SQL `0` |
| `estoqueMaximo` | número JSON ou `null` (`BigDecimal`) | Opcional; precisão 12, escala 3 |
| `imagem` | string ou `null` | Opcional; máximo 255 caracteres |
| `status` | boolean | Default Java `true`; coluna não nula |

O formato serializado de `BigDecimal` como número JSON segue o mapeamento Jackson esperado, mas ainda não foi validado por resposta real. Produto não possui campos de data.

#### Relação com Categoria

O atributo Java é do tipo `Categoria`; o corpo deve representar um objeto, não um ID numérico isolado. Exemplo derivado do modelo Java:

```json
{
	"categoria": {
		"id": 1
	}
}
```

Este formato **não foi confirmado através de uma requisição POST real**. `"categoria": 1` não corresponde ao tipo declarado no modelo Java.

#### `GET /produtos`

Retorna `200 OK` com uma lista JSON de produtos. A forma serializada do objeto relacionado `categoria` ainda precisa de confirmação em runtime.

#### `GET /produtos/{id}`

Retorna `200 OK` com o produto quando encontrado ou `404 Not Found` quando não existir.

#### `POST /produtos`

Recebe um `Produto` e retorna o objeto salvo. O status esperado pelo código atual é `200 OK`. Enviar campos obrigatórios e a relação `categoria` como objeto; omitir o `id` gerado.

#### `PUT /produtos/{id}`

Recebe um objeto `Produto`; retorna `200 OK` com a entidade atualizada ou `404 Not Found` se o ID não existir. A atualização é completa: o service copia os campos recebidos para a entidade persistida. Enviar também os valores que devem ser preservados; campos ausentes podem ser desserializados como `null` e sobrescrever valores existentes.

#### `DELETE /produtos/{id}`

Retorna `204 No Content` quando excluído ou `404 Not Found` quando o produto não existir. Referências em estoque, movimentações, itens de compra/venda e associações com fornecedor podem impedir a exclusão; não há tratamento específico desses conflitos.

#### Validação e erros

O controller não usa `@Valid`, portanto não há validação do request na camada MVC. A integração de Bean Validation na persistência pode validar a entidade ao salvar. Constraints do banco, como `NOT NULL`, tamanhos, unicidade e FKs continuam valendo. Erros de validação, unicidade ou FK não possuem contrato HTTP/JSON padronizado; não se deve prometer `409 Conflict`. O status e o formato concretos ainda precisam ser padronizados futuramente.

### Estoque

A definir

### Compras

A definir

### Vendas

A definir

### Financeiro

A definir

### Dashboard

A definir

### Relatórios

A definir

---

## Paginação

**Estratégia**: A definir

**Parâmetros**: A definir

**Exemplo**: A definir

---

## Filtros e Buscas

**Estratégia**: A definir

**Parâmetros**: A definir

**Exemplo**: A definir

---

## Sorting (Ordenação)

**Estratégia**: A definir

**Parâmetros**: A definir

**Exemplo**: A definir

---

## Validação de Entrada

A definir

---

## Tratamento de Erros

### Códigos de Erro

A definir

### Mensagens de Erro

A definir

### Stack Trace

A definir

---

## Rate Limiting

**Ativado**: A definir

**Limites**: A definir

**Headers**: A definir

---

## CORS (Cross-Origin Resource Sharing)

**Origens Permitidas**: A definir

**Métodos Permitidos**: A definir

**Headers Permitidos**: A definir

---

## Logging e Auditoria

**O que registrar**: A definir

**Nível de detalhe**: A definir

**Retenção**: A definir

---

## Documentação da API

### Ferramentas

- **Swagger/OpenAPI**: A definir
- **Postman Collection**: A definir
- **Documentação Manual**: A definir

### Acesso

A definir

---

## Performance

### Caching

**Estratégia**: A definir

**TTL**: A definir

### Compressão

**Ativada**: A definir

**Tipos**: A definir

### Otimizações

A definir

---

## Segurança

### Validação

A definir

### Sanitização

A definir

### Proteção contra Ataques

- CSRF: A definir
- SQL Injection: A definir
- XSS: A definir
- HTTPS: A definir

---

## Decisões Pendentes

- [ ] Estratégia de versionamento
- [ ] Modelo padrão de respostas (sucesso e erro)
- [ ] Códigos HTTP específicos por cenário
- [ ] Paginação (limit/offset vs. page/size)
- [ ] Filtros padrão
- [ ] Ordenação padrão
- [ ] Rate limiting
- [ ] CORS configuration
- [ ] Compressão GZIP
- [ ] Documentação automática (Swagger)
- [ ] Logging level
- [ ] Auditoria de requisições
- [ ] Timeout das requisições
- [ ] Tamanho máximo de payload

---

*Última atualização: 2026-09-01*
