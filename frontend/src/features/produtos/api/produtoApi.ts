import type { Categoria } from '../../categorias/types'
import type { EstoqueProdutoResumo, Produto, ProdutoRequest } from '../types'

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080').replace(/\/$/, '')

export class ProdutoApiError extends Error {
  readonly status?: number

  constructor(message: string, status?: number) {
    super(message)
    this.name = 'ProdutoApiError'
    this.status = status
  }
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  let response: Response

  try {
    response = await fetch(`${API_BASE_URL}${path}`, init)
  } catch {
    throw new ProdutoApiError('Não foi possível conectar ao servidor. Tente novamente.')
  }

  if (!response.ok) {
    if (response.status === 404) {
      throw new ProdutoApiError('O produto solicitado não foi encontrado.', 404)
    }
    if (response.status === 409) {
      throw new ProdutoApiError('Não foi possível concluir por causa de um conflito com outros dados.', 409)
    }
    throw new ProdutoApiError('Não foi possível concluir a operação. Tente novamente.', response.status)
  }

  if (response.status === 204) return undefined as T
  return response.json() as Promise<T>
}

export function listarProdutos(): Promise<Produto[]> {
  return request<Produto[]>('/produtos')
}

export function buscarProduto(id: string | number): Promise<Produto> {
  return request<Produto>(`/produtos/${encodeURIComponent(String(id))}`)
}

export function criarProduto(produto: ProdutoRequest): Promise<Produto> {
  return request<Produto>('/produtos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(produto),
  })
}

export function atualizarProduto(id: string | number, produto: ProdutoRequest): Promise<Produto> {
  return request<Produto>(`/produtos/${encodeURIComponent(String(id))}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(produto),
  })
}

export function excluirProduto(id: string | number): Promise<void> {
  return request<void>(`/produtos/${encodeURIComponent(String(id))}`, { method: 'DELETE' })
}

export function listarCategorias(): Promise<Categoria[]> {
  return request<Categoria[]>('/categorias')
}

export function listarEstoques(): Promise<EstoqueProdutoResumo[]> {
  return request<EstoqueProdutoResumo[]>('/estoques')
}