<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { ChevronLeft, ChevronRight, Eye, Pencil, Plus, Search, Trash2 } from '@lucide/vue'
import { RouterLink } from 'vue-router'
import ConfirmDialog from '../../../shared/components/ConfirmDialog.vue'
import { excluirProduto, listarProdutos } from '../api/produtoApi'
import ProdutoStatusBadge from '../components/ProdutoStatusBadge.vue'
import type { Produto } from '../types'
import { formatCurrency } from '../utils/numberFormat'

type SortKey = 'nome' | 'codigoInterno' | 'precoVenda'

const pageSize = 25
const produtos = ref<Produto[]>([])
const searchTerm = ref('')
const currentPage = ref(1)
const sortKey = ref<SortKey>('nome')
const sortAscending = ref(true)
const loading = ref(true)
const errorMessage = ref('')
const produtoToDelete = ref<Produto | null>(null)
const deleteError = ref('')
const deleting = ref(false)

const filteredProdutos = computed(() => {
  const query = searchTerm.value.trim().toLocaleLowerCase('pt-BR')
  const matching = produtos.value.filter((produto) => [
    produto.nome,
    produto.codigoInterno,
    produto.codigoBarras ?? '',
  ].some((field) => field.toLocaleLowerCase('pt-BR').includes(query)))

  return matching.sort((first, second) => {
    let comparison = 0
    if (sortKey.value === 'precoVenda') {
      comparison = first.precoVenda - second.precoVenda
    } else {
      comparison = first[sortKey.value].localeCompare(second[sortKey.value], 'pt-BR', { sensitivity: 'base' })
    }
    return sortAscending.value ? comparison : -comparison
  })
})

const pageCount = computed(() => Math.ceil(filteredProdutos.value.length / pageSize))
const visibleProdutos = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredProdutos.value.slice(start, start + pageSize)
})
const showPagination = computed(() => pageCount.value > 1)

watch(searchTerm, () => {
  currentPage.value = 1
})

watch(pageCount, (count) => {
  if (currentPage.value > count) currentPage.value = Math.max(1, count)
})

async function loadProdutos() {
  loading.value = true
  errorMessage.value = ''
  try {
    produtos.value = await listarProdutos()
  } catch (error) {
    errorMessage.value = error instanceof Error
      ? error.message
      : 'Não foi possível carregar os produtos. Tente novamente.'
  } finally {
    loading.value = false
  }
}

function changeSort(key: SortKey) {
  if (sortKey.value === key) {
    sortAscending.value = !sortAscending.value
  } else {
    sortKey.value = key
    sortAscending.value = true
  }
  currentPage.value = 1
}

function sortIndicator(key: SortKey): string {
  if (sortKey.value !== key) return ''
  return sortAscending.value ? ' ↑' : ' ↓'
}

function requestDelete(produto: Produto) {
  produtoToDelete.value = produto
  deleteError.value = ''
}

async function confirmDelete() {
  if (!produtoToDelete.value) return
  deleting.value = true
  deleteError.value = ''

  try {
    await excluirProduto(produtoToDelete.value.id)
    produtos.value = produtos.value.filter((produto) => produto.id !== produtoToDelete.value?.id)
    produtoToDelete.value = null
  } catch (error) {
    deleteError.value = error instanceof Error
      ? error.message
      : 'Não foi possível excluir o produto. Tente novamente.'
  } finally {
    deleting.value = false
  }
}

function previousPage() {
  currentPage.value = Math.max(1, currentPage.value - 1)
}

function nextPage() {
  currentPage.value = Math.min(pageCount.value, currentPage.value + 1)
}

onMounted(loadProdutos)
</script>

<template>
  <section class="product-page" aria-labelledby="product-page-description">
    <div class="product-page-intro">
      <p id="product-page-description" class="product-page-description">
        Consulte e mantenha o catálogo de produtos.
      </p>
      <RouterLink class="button-primary" to="/produtos/novo">
        <Plus :size="17" aria-hidden="true" />
        <span>Novo produto</span>
      </RouterLink>
    </div>

    <label class="product-search">
      <Search :size="17" aria-hidden="true" />
      <span class="visually-hidden">Buscar produtos</span>
      <input v-model="searchTerm" type="search" placeholder="Buscar por nome ou código" autocomplete="off" />
    </label>

    <div v-if="deleteError" class="inline-alert error-alert product-delete-alert" role="alert">
      {{ deleteError }}
    </div>

    <div v-if="loading" class="product-table-frame" role="status" aria-label="Carregando produtos">
      <div class="product-skeleton" aria-hidden="true">
        <div v-for="row in 6" :key="row" class="product-skeleton-row">
          <span v-for="column in 7" :key="column" class="skeleton-block" />
        </div>
      </div>
    </div>

    <section v-else-if="errorMessage" class="product-state error-state" role="alert">
      <h2>Não foi possível carregar os produtos</h2>
      <p>{{ errorMessage }}</p>
      <button class="button-secondary" type="button" @click="loadProdutos">Tentar novamente</button>
    </section>

    <section v-else-if="filteredProdutos.length === 0" class="product-state empty-state">
      <h2>Nenhum produto encontrado.</h2>
      <p v-if="searchTerm">Tente outro termo de busca.</p>
      <RouterLink v-else class="button-primary" to="/produtos/novo">
        <Plus :size="17" aria-hidden="true" />
        <span>Novo produto</span>
      </RouterLink>
    </section>

    <template v-else>
      <div class="product-table-frame desktop-product-table">
        <table class="product-table">
          <colgroup>
            <col class="product-col-name" />
            <col class="product-col-code" />
            <col class="product-col-category" />
            <col class="product-col-unit" />
            <col class="product-col-price" />
            <col class="product-col-status" />
            <col class="product-col-actions" />
          </colgroup>
          <thead>
            <tr>
              <th scope="col"><button class="sort-button" type="button" @click="changeSort('nome')">Nome{{ sortIndicator('nome') }}</button></th>
              <th scope="col"><button class="sort-button" type="button" @click="changeSort('codigoInterno')">Código interno{{ sortIndicator('codigoInterno') }}</button></th>
              <th scope="col">Categoria</th>
              <th scope="col">Unidade</th>
              <th scope="col" class="numeric-cell"><button class="sort-button" type="button" @click="changeSort('precoVenda')">Preço venda{{ sortIndicator('precoVenda') }}</button></th>
              <th scope="col">Status</th>
              <th scope="col"><span class="visually-hidden">Ações</span></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="produto in visibleProdutos" :key="produto.id">
              <td><RouterLink class="product-name-link" :to="`/produtos/${produto.id}`">{{ produto.nome }}</RouterLink></td>
              <td class="product-code-cell">{{ produto.codigoInterno }}</td>
              <td>{{ produto.categoria?.nome ?? 'Categoria não identificada' }}</td>
              <td>{{ produto.unidadeMedida }}</td>
              <td class="numeric-cell">{{ formatCurrency(produto.precoVenda) }}</td>
              <td><ProdutoStatusBadge :active="produto.status" /></td>
              <td>
                <div class="product-row-actions">
                  <RouterLink class="product-icon-action" :to="`/produtos/${produto.id}`" :aria-label="`Visualizar ${produto.nome}`" title="Visualizar">
                    <Eye :size="16" aria-hidden="true" />
                  </RouterLink>
                  <RouterLink class="product-icon-action" :to="`/produtos/${produto.id}/editar`" :aria-label="`Editar ${produto.nome}`" title="Editar">
                    <Pencil :size="16" aria-hidden="true" />
                  </RouterLink>
                  <button class="product-icon-action is-danger" type="button" :aria-label="`Excluir ${produto.nome}`" title="Excluir" @click="requestDelete(produto)">
                    <Trash2 :size="16" aria-hidden="true" />
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <ul class="product-compact-list" aria-label="Produtos">
        <li v-for="produto in visibleProdutos" :key="produto.id" class="product-compact-item">
          <div class="product-compact-main">
            <RouterLink class="product-name-link" :to="`/produtos/${produto.id}`">{{ produto.nome }}</RouterLink>
            <ProdutoStatusBadge :active="produto.status" />
          </div>
          <p>{{ produto.codigoInterno }} · {{ formatCurrency(produto.precoVenda) }}</p>
          <p>{{ produto.categoria?.nome ?? 'Categoria não identificada' }} · {{ produto.unidadeMedida }}</p>
          <div class="product-compact-actions">
            <RouterLink class="button-secondary compact-action" :to="`/produtos/${produto.id}`"><Eye :size="15" aria-hidden="true" />Visualizar</RouterLink>
            <RouterLink class="button-secondary compact-action" :to="`/produtos/${produto.id}/editar`"><Pencil :size="15" aria-hidden="true" />Editar</RouterLink>
            <button class="button-secondary compact-action is-danger" type="button" @click="requestDelete(produto)"><Trash2 :size="15" aria-hidden="true" />Excluir</button>
          </div>
        </li>
      </ul>

      <nav v-if="showPagination" class="local-pagination" aria-label="Paginação de produtos">
        <span class="pagination-summary">
          {{ (currentPage - 1) * pageSize + 1 }}–{{ Math.min(currentPage * pageSize, filteredProdutos.length) }}
          de {{ filteredProdutos.length }}
        </span>
        <div class="pagination-controls">
          <button class="pagination-button" type="button" aria-label="Página anterior" :disabled="currentPage === 1" @click="previousPage"><ChevronLeft :size="17" aria-hidden="true" /></button>
          <span class="pagination-page">{{ currentPage }} / {{ pageCount }}</span>
          <button class="pagination-button" type="button" aria-label="Próxima página" :disabled="currentPage === pageCount" @click="nextPage"><ChevronRight :size="17" aria-hidden="true" /></button>
        </div>
      </nav>
    </template>

    <ConfirmDialog
      v-if="produtoToDelete"
      title="Excluir produto?"
      :message="`O produto “${produtoToDelete.nome}” será excluído. Se houver registros relacionados, o backend poderá recusar a operação.`"
      confirm-label="Excluir produto"
      :loading="deleting"
      @cancel="produtoToDelete = null"
      @confirm="confirmDelete"
    />
  </section>
</template>