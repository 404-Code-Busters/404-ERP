export interface Categoria {
  id: number
  nome: string
  descricao: string | null
  status: boolean
}

export interface NovaCategoria {
  nome: string
  descricao: string | null
  status: boolean
}