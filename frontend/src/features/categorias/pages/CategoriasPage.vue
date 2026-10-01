<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { ChevronLeft, ChevronRight, Plus, Search } from '@lucide/vue'
import { RouterLink } from 'vue-router'
import { listarCategorias } from '../api/categoriaApi'
import CategoriaStatusBadge from '../components/CategoriaStatusBadge.vue'
import type { Categoria } from '../types'

const pageSize = 25
const categorias = ref<Categoria[]>([])
const searchTerm = ref('')
const currentPage = ref(1)
const loading = ref(true)
const errorMessage = ref('')

const filteredCategorias = computed(() => {
  const query = searchTerm.value.trim().toLocaleLowerCase('pt-BR')
  return categorias.value
    .filter((categoria) => categoria.nome.toLocaleLowerCase('pt-BR').includes(query))
    .sort((first, second) => first.nome.localeCompare(second.nome, 'pt-BR', { sensitivity: 'base' }))
})

const pageCount = computed(() => Math.ceil(filteredCategorias.value.length / pageSize))
const visibleCategorias = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredCategorias.value.slice(start, start + pageSize)
})
const showPagination = computed(() => pageCount.value > 1)

watch(searchTerm, () => {
  currentPage.value = 1
})

watch(pageCount, (count) => {
  if (currentPage.value > count) currentPage.value = Math.max(count, 1)
})

async function loadCategorias() {
  loading.value = true
  errorMessage.value = ''

  try {
    categorias.value = await listarCategorias()
  } catch (error) {
    errorMessage.value = error instanceof Error
      ? error.message
      : 'Não foi possível carregar as categorias. Tente novamente.'
  } finally {
    loading.value = false
  }
}

function previousPage() {
  currentPage.value = Math.max(1, currentPage.value - 1)
}

function nextPage() {
  currentPage.value = Math.min(pageCount.value, currentPage.value + 1)
}

onMounted(loadCategorias)
</script>

<template>
  <section class="category-page" aria-labelledby="category-page-description">
    <div class="category-page-intro">
      <p id="category-page-description" class="category-page-description">
        Organize os produtos por categoria.
      </p>
      <RouterLink class="button-primary" to="/categorias/nova">
        <Plus :size="17" aria-hidden="true" />
        <span>Nova categoria</span>
      </RouterLink>
    </div>

    <label class="category-search">
      <Search :size="17" aria-hidden="true" />
      <span class="visually-hidden">Buscar por nome</span>
      <input
        v-model="searchTerm"
        type="search"
        placeholder="Buscar por nome"
        autocomplete="off"
      />
    </label>

    <div v-if="loading" class="category-table-frame" role="status" aria-label="Carregando categorias">
      <div class="table-skeleton" aria-hidden="true">
        <div v-for="row in 6" :key="row" class="skeleton-row">
          <span class="skeleton-block skeleton-name" />
          <span class="skeleton-block skeleton-description" />
          <span class="skeleton-block skeleton-status" />
        </div>
      </div>
    </div>

    <section v-else-if="errorMessage" class="category-state error-state" role="alert">
      <h2>Não foi possível carregar as categorias</h2>
      <p>{{ errorMessage }}</p>
      <button class="button-secondary" type="button" @click="loadCategorias">
        Tentar novamente
      </button>
    </section>

    <section v-else-if="filteredCategorias.length === 0" class="category-state empty-state">
      <h2>Nenhuma categoria encontrada.</h2>
      <RouterLink class="button-primary" to="/categorias/nova">
        <Plus :size="17" aria-hidden="true" />
        <span>Nova categoria</span>
      </RouterLink>
    </section>

    <template v-else>
      <div class="category-table-frame desktop-category-table">
        <table class="category-table">
          <colgroup>
            <col class="category-col-name" />
            <col class="category-col-description" />
            <col class="category-col-status" />
          </colgroup>
          <thead>
            <tr>
              <th scope="col">Nome</th>
              <th scope="col">Descrição</th>
              <th scope="col">Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="categoria in visibleCategorias" :key="categoria.id">
              <td>
                <RouterLink class="category-name-link" :to="`/categorias/${categoria.id}`">
                  {{ categoria.nome }}
                </RouterLink>
              </td>
              <td>
                <span class="category-description-cell" :title="categoria.descricao ?? ''">
                  {{ categoria.descricao || '—' }}
                </span>
              </td>
              <td><CategoriaStatusBadge :active="categoria.status" /></td>
            </tr>
          </tbody>
        </table>
      </div>

      <ul class="category-compact-list" aria-label="Categorias">
        <li v-for="categoria in visibleCategorias" :key="categoria.id" class="category-compact-row">
          <RouterLink class="category-compact-link" :to="`/categorias/${categoria.id}`">
            <span class="category-compact-main">
              <span class="category-compact-name">{{ categoria.nome }}</span>
              <CategoriaStatusBadge :active="categoria.status" />
            </span>
            <span class="category-compact-description" :title="categoria.descricao ?? ''">
              {{ categoria.descricao || '—' }}
            </span>
          </RouterLink>
        </li>
      </ul>

      <nav v-if="showPagination" class="local-pagination" aria-label="Paginação de categorias">
        <span class="pagination-summary">
          {{ (currentPage - 1) * pageSize + 1 }}–{{ Math.min(currentPage * pageSize, filteredCategorias.length) }}
          de {{ filteredCategorias.length }}
        </span>
        <div class="pagination-controls">
          <button
            class="pagination-button"
            type="button"
            aria-label="Página anterior"
            :disabled="currentPage === 1"
            @click="previousPage"
          >
            <ChevronLeft :size="17" aria-hidden="true" />
          </button>
          <span class="pagination-page">{{ currentPage }} / {{ pageCount }}</span>
          <button
            class="pagination-button"
            type="button"
            aria-label="Próxima página"
            :disabled="currentPage === pageCount"
            @click="nextPage"
          >
            <ChevronRight :size="17" aria-hidden="true" />
          </button>
        </div>
      </nav>
    </template>
  </section>
</template>