import type { Categoria, NovaCategoria } from '../types'

const API_BASE_URL = (import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080').replace(/\/$/, '')

export class CategoriaApiError extends Error {
  readonly status?: number

  constructor(
    message: string,
    status?: number,
  ) {
    super(message)
    this.name = 'CategoriaApiError'
    this.status = status
  }
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  let response: Response

  try {
    response = await fetch(`${API_BASE_URL}${path}`, init)
  } catch {
    throw new CategoriaApiError('Não foi possível conectar ao servidor. Tente novamente.')
  }

  if (!response.ok) {
    if (response.status === 404) {
      throw new CategoriaApiError('A categoria solicitada não foi encontrada.', 404)
    }

    throw new CategoriaApiError('Não foi possível concluir a operação. Tente novamente.', response.status)
  }

  return response.json() as Promise<T>
}

export function listarCategorias(): Promise<Categoria[]> {
  return request<Categoria[]>('/categorias')
}

export function buscarCategoria(id: string | number): Promise<Categoria> {
  return request<Categoria>(`/categorias/${encodeURIComponent(String(id))}`)
}

export function criarCategoria(categoria: NovaCategoria): Promise<Categoria> {
  return request<Categoria>('/categorias', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(categoria),
  })
}