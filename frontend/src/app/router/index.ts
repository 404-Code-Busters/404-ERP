import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import CategoriasPage from '../../features/categorias/pages/CategoriasPage.vue'
import NovaCategoriaPage from '../../features/categorias/pages/NovaCategoriaPage.vue'
import DetalheCategoriaPage from '../../features/categorias/pages/DetalheCategoriaPage.vue'
import ProdutosPage from '../../features/produtos/pages/ProdutosPage.vue'
import ProdutoFormPage from '../../features/produtos/pages/ProdutoFormPage.vue'
import DetalheProdutoPage from '../../features/produtos/pages/DetalheProdutoPage.vue'
import RoutePlaceholder from '../../shared/components/RoutePlaceholder.vue'

interface BreadcrumbItem {
  label: string
  to?: string
}

function placeholderRoute(
  path: string,
  name: string,
  title: string,
  breadcrumbs: BreadcrumbItem[],
  navKey?: string,
): RouteRecordRaw {
  return {
    path,
    name,
    component: RoutePlaceholder,
    meta: { title, breadcrumbs, navKey },
  }
}

const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/categorias' },
  {
    path: '/categorias',
    name: 'categorias-lista',
    component: CategoriasPage,
    meta: {
      title: 'Categorias',
      breadcrumbs: [{ label: 'Cadastros' }, { label: 'Categorias' }],
      navKey: 'categorias',
    },
  },
  {
    path: '/categorias/nova',
    name: 'categoria-nova',
    component: NovaCategoriaPage,
    meta: {
      title: 'Nova categoria',
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Categorias', to: '/categorias' },
        { label: 'Nova categoria' },
      ],
      navKey: 'categorias',
    },
  },
  {
    path: '/categorias/:id',
    name: 'categoria-detalhe',
    component: DetalheCategoriaPage,
    meta: {
      title: 'Detalhe da categoria',
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Categorias', to: '/categorias' },
        { label: 'Detalhe' },
      ],
      navKey: 'categorias',
    },
  },
  placeholderRoute('/clientes', 'clientes-lista', 'Clientes', [
    { label: 'Cadastros' },
    { label: 'Clientes' },
  ], 'clientes'),
  placeholderRoute('/clientes/novo', 'cliente-novo', 'Novo cliente', [
    { label: 'Cadastros' },
    { label: 'Clientes', to: '/clientes' },
    { label: 'Novo cliente' },
  ], 'clientes'),
  placeholderRoute('/clientes/:id/editar', 'cliente-editar', 'Editar cliente', [
    { label: 'Cadastros' },
    { label: 'Clientes', to: '/clientes' },
    { label: 'Editar' },
  ], 'clientes'),
  placeholderRoute('/clientes/:id', 'cliente-detalhe', 'Detalhe do cliente', [
    { label: 'Cadastros' },
    { label: 'Clientes', to: '/clientes' },
    { label: 'Detalhe' },
  ], 'clientes'),
  placeholderRoute('/fornecedores', 'fornecedores-lista', 'Fornecedores', [
    { label: 'Cadastros' },
    { label: 'Fornecedores' },
  ], 'fornecedores'),
  placeholderRoute('/fornecedores/novo', 'fornecedor-novo', 'Novo fornecedor', [
    { label: 'Cadastros' },
    { label: 'Fornecedores', to: '/fornecedores' },
    { label: 'Novo fornecedor' },
  ], 'fornecedores'),
  placeholderRoute('/fornecedores/:id/editar', 'fornecedor-editar', 'Editar fornecedor', [
    { label: 'Cadastros' },
    { label: 'Fornecedores', to: '/fornecedores' },
    { label: 'Editar' },
  ], 'fornecedores'),
  placeholderRoute('/fornecedores/:id', 'fornecedor-detalhe', 'Detalhe do fornecedor', [
    { label: 'Cadastros' },
    { label: 'Fornecedores', to: '/fornecedores' },
    { label: 'Detalhe' },
  ], 'fornecedores'),
  {
    path: '/produtos',
    name: 'produtos-lista',
    component: ProdutosPage,
    meta: {
      title: 'Produtos',
      breadcrumbs: [{ label: 'Cadastros' }, { label: 'Produtos' }],
      navKey: 'produtos',
    },
  },
  {
    path: '/produtos/novo',
    name: 'produto-novo',
    component: ProdutoFormPage,
    meta: {
      title: 'Novo produto',
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Produtos', to: '/produtos' },
        { label: 'Novo produto' },
      ],
      navKey: 'produtos',
    },
  },
  {
    path: '/produtos/:id/editar',
    name: 'produto-editar',
    component: ProdutoFormPage,
    meta: {
      title: 'Editar produto',
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Produtos', to: '/produtos' },
        { label: 'Editar' },
      ],
      navKey: 'produtos',
      editing: true,
    },
  },
  {
    path: '/produtos/:id',
    name: 'produto-detalhe',
    component: DetalheProdutoPage,
    meta: {
      title: 'Detalhe do produto',
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Produtos', to: '/produtos' },
        { label: 'Detalhe' },
      ],
      navKey: 'produtos',
    },
  },
  placeholderRoute('/estoque', 'estoque-lista', 'Estoque', [
    { label: 'Operações' },
    { label: 'Estoque' },
  ], 'estoque'),
  placeholderRoute('/estoque/novo', 'estoque-novo', 'Novo registro de estoque', [
    { label: 'Operações' },
    { label: 'Estoque', to: '/estoque' },
    { label: 'Novo registro' },
  ], 'estoque'),
  placeholderRoute('/estoque/movimentacoes', 'movimentacoes-lista', 'Movimentações de estoque', [
    { label: 'Operações' },
    { label: 'Estoque', to: '/estoque' },
    { label: 'Movimentações' },
  ], 'movimentacoes'),
  placeholderRoute('/estoque/movimentacoes/nova', 'movimentacao-nova', 'Nova movimentação', [
    { label: 'Operações' },
    { label: 'Estoque', to: '/estoque' },
    { label: 'Movimentações', to: '/estoque/movimentacoes' },
    { label: 'Nova movimentação' },
  ], 'movimentacoes'),
  placeholderRoute('/compras', 'compras-lista', 'Compras', [
    { label: 'Operações' },
    { label: 'Compras' },
  ], 'compras'),
  placeholderRoute('/compras/nova', 'compra-nova', 'Nova compra', [
    { label: 'Operações' },
    { label: 'Compras', to: '/compras' },
    { label: 'Nova compra' },
  ], 'compras'),
  placeholderRoute('/compras/:id/editar', 'compra-editar', 'Editar compra', [
    { label: 'Operações' },
    { label: 'Compras', to: '/compras' },
    { label: 'Editar' },
  ], 'compras'),
  placeholderRoute('/compras/:id', 'compra-detalhe', 'Detalhe da compra', [
    { label: 'Operações' },
    { label: 'Compras', to: '/compras' },
    { label: 'Detalhe' },
  ], 'compras'),
  placeholderRoute('/vendas', 'vendas-lista', 'Vendas', [
    { label: 'Operações' },
    { label: 'Vendas' },
  ], 'vendas'),
  placeholderRoute('/vendas/nova', 'venda-nova', 'Nova venda', [
    { label: 'Operações' },
    { label: 'Vendas', to: '/vendas' },
    { label: 'Nova venda' },
  ], 'vendas'),
  placeholderRoute('/vendas/:id/editar', 'venda-editar', 'Editar venda', [
    { label: 'Operações' },
    { label: 'Vendas', to: '/vendas' },
    { label: 'Editar' },
  ], 'vendas'),
  placeholderRoute('/vendas/:id', 'venda-detalhe', 'Detalhe da venda', [
    { label: 'Operações' },
    { label: 'Vendas', to: '/vendas' },
    { label: 'Detalhe' },
  ], 'vendas'),
  {
    path: '/:pathMatch(.*)*',
    name: 'nao-encontrada',
    component: RoutePlaceholder,
    meta: {
      title: 'Página não encontrada',
      breadcrumbs: [{ label: '404' }],
    },
  },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})