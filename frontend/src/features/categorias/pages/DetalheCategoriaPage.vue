<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { RouterLink, useRoute } from 'vue-router'
import { ArrowLeft } from '@lucide/vue'
import { buscarCategoria } from '../api/categoriaApi'
import CategoriaStatusBadge from '../components/CategoriaStatusBadge.vue'
import type { Categoria } from '../types'
import { useShellPageMeta } from '../../../shared/composables/shellPageMeta'

const route = useRoute()
const setShellPageMeta = useShellPageMeta()
const categoria = ref<Categoria | null>(null)
const loading = ref(true)
const notFound = ref(false)
const errorMessage = ref('')

async function loadCategoria() {
  loading.value = true
  categoria.value = null
  notFound.value = false
  errorMessage.value = ''

  try {
    categoria.value = await buscarCategoria(String(route.params.id))
  } catch (error) {
    categoria.value = null
    notFound.value = (error as { status?: number }).status === 404
    errorMessage.value = error instanceof Error
      ? error.message
      : 'Não foi possível carregar a categoria. Tente novamente.'
  } finally {
    loading.value = false
  }
}

function syncShellMeta(value: Categoria | null) {
  if (!value) {
    setShellPageMeta?.(null)
    return
  }

  setShellPageMeta?.({
    title: value.nome,
    breadcrumbs: [
      { label: 'Cadastros' },
      { label: 'Categorias', to: '/categorias' },
      { label: value.nome },
    ],
  })
}

watch(categoria, syncShellMeta, { immediate: true })
watch(() => route.params.id, loadCategoria)

onMounted(loadCategoria)
onBeforeUnmount(() => setShellPageMeta?.(null))
</script>

<template>
  <section class="category-detail-page">
    <div v-if="loading" class="detail-skeleton" role="status" aria-label="Carregando categoria">
      <span class="skeleton-block skeleton-detail-title" />
      <span class="skeleton-block skeleton-detail-line" />
      <span class="skeleton-block skeleton-detail-line short" />
    </div>

    <section v-else-if="errorMessage" class="category-state error-state" role="alert">
      <h2>{{ notFound ? 'Categoria não encontrada' : 'Não foi possível carregar a categoria' }}</h2>
      <p>{{ errorMessage }}</p>
      <div class="state-actions">
        <button v-if="!notFound" class="button-secondary" type="button" @click="loadCategoria">
          Tentar novamente
        </button>
        <RouterLink class="button-secondary" to="/categorias">
          <ArrowLeft :size="16" aria-hidden="true" />
          Voltar para categorias
        </RouterLink>
      </div>
    </section>

    <template v-else-if="categoria">
      <div class="category-detail-heading">
        <CategoriaStatusBadge :active="categoria.status" />
      </div>

      <section class="category-detail-section" aria-labelledby="category-detail-title">
        <h2 id="category-detail-title">Informações principais</h2>
        <dl class="category-detail-grid">
          <div class="detail-field">
            <dt>Nome</dt>
            <dd>{{ categoria.nome }}</dd>
          </div>
          <div class="detail-field detail-description">
            <dt>Descrição</dt>
            <dd>{{ categoria.descricao || '—' }}</dd>
          </div>
          <div class="detail-field">
            <dt>Status</dt>
            <dd><CategoriaStatusBadge :active="categoria.status" /></dd>
          </div>
        </dl>
      </section>

      <RouterLink class="button-secondary detail-back-link" to="/categorias">
        <ArrowLeft :size="16" aria-hidden="true" />
        Voltar para categorias
      </RouterLink>
    </template>
  </section>
</template>