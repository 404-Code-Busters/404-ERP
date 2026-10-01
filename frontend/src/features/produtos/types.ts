import type { Categoria } from '../categorias/types'

export interface Produto {
  id: number
  categoria: Categoria | null
  nome: string
  codigoInterno: string
  codigoBarras: string | null
  descricao: string | null
  unidadeMedida: string
  precoCusto: number
  precoVenda: number
  estoqueMinimo: number
  estoqueMaximo: number | null
  imagem: string | null
  status: boolean
}

export interface ProdutoRequest {
  categoria: { id: number }
  nome: string
  codigoInterno: string
  codigoBarras: string | null
  descricao: string | null
  unidadeMedida: string
  precoCusto: number
  precoVenda: number
  estoqueMinimo: number
  estoqueMaximo: number | null
  imagem: string | null
  status: boolean
}

export interface EstoqueProdutoResumo {
  produto: { id: number } | null
  quantidade: number
  atualizadoEm: string
}