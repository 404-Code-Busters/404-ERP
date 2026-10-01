<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { ArrowLeft, Pencil, Trash2 } from '@lucide/vue'
import ConfirmDialog from '../../../shared/components/ConfirmDialog.vue'
import { useShellPageMeta } from '../../../shared/composables/shellPageMeta'
import { buscarProduto, excluirProduto, listarEstoques } from '../api/produtoApi'
import ProdutoStatusBadge from '../components/ProdutoStatusBadge.vue'
import type { EstoqueProdutoResumo, Produto } from '../types'
import { formatCurrency } from '../utils/numberFormat'

const route = useRoute()
const router = useRouter()
const setShellPageMeta = useShellPageMeta()
const produto = ref<Produto | null>(null)
const estoque = ref<EstoqueProdutoResumo | null>(null)
const loading = ref(true)
const notFound = ref(false)
const errorMessage = ref('')
const deleteDialogOpen = ref(false)
const deleting = ref(false)
const deleteError = ref('')
const formattedStockDate = computed(() => {
  if (!estoque.value?.atualizadoEm) return '—'
  const date = new Date(estoque.value.atualizadoEm)
  return Number.isNaN(date.getTime()) ? estoque.value.atualizadoEm : date.toLocaleString('pt-BR')
})

async function loadProduto() {
  loading.value = true
  produto.value = null
  estoque.value = null
  notFound.value = false
  errorMessage.value = ''
  deleteError.value = ''

  try {
    const loaded = await buscarProduto(String(route.params.id))
    produto.value = loaded
    setShellPageMeta?.({
      title: loaded.nome,
      breadcrumbs: [
        { label: 'Cadastros' },
        { label: 'Produtos', to: '/produtos' },
        { label: loaded.nome },
      ],
    })

    try {
      const stocks = await listarEstoques()
      estoque.value = stocks.find((item) => item.produto?.id === loaded.id) ?? null
    } catch {
      estoque.value = null
    }
  } catch (error) {
    produto.value = null
    notFound.value = (error as { status?: number }).status === 404
    errorMessage.value = error instanceof Error
      ? error.message
      : 'Não foi possível carregar o produto. Tente novamente.'
    setShellPageMeta?.(null)
  } finally {
    loading.value = false
  }
}

async function confirmDelete() {
  if (!produto.value) return
  deleting.value = true
  deleteError.value = ''
  try {
    await excluirProduto(produto.value.id)
    await router.push('/produtos')
  } catch (error) {
    deleteDialogOpen.value = false
    deleteError.value = error instanceof Error
      ? error.message
      : 'Não foi possível excluir o produto. Tente novamente.'
  } finally {
    deleting.value = false
  }
}

watch(() => route.params.id, loadProduto)
onMounted(loadProduto)
onBeforeUnmount(() => setShellPageMeta?.(null))
</script>

<template>
  <section class="product-detail-page">
    <div v-if="loading" class="detail-skeleton" role="status" aria-label="Carregando produto">
      <span class="skeleton-block skeleton-detail-title" />
      <span class="skeleton-block skeleton-detail-line" />
      <span class="skeleton-block skeleton-detail-line short" />
    </div>

    <section v-else-if="errorMessage" class="product-state error-state" role="alert">
      <h2>{{ notFound ? 'Produto não encontrado' : 'Não foi possível carregar o produto' }}</h2>
      <p>{{ errorMessage }}</p>
      <div class="state-actions">
        <button v-if="!notFound" class="button-secondary" type="button" @click="loadProduto">Tentar novamente</button>
        <RouterLink class="button-secondary" to="/produtos"><ArrowLeft :size="16" aria-hidden="true" />Voltar para produtos</RouterLink>
      </div>
    </section>

    <template v-else-if="produto">
      <div class="product-detail-actions">
        <ProdutoStatusBadge :active="produto.status" />
        <div class="product-detail-action-buttons">
          <RouterLink class="button-secondary" :to="`/produtos/${produto.id}/editar`">
            <Pencil :size="16" aria-hidden="true" />Editar
          </RouterLink>
          <button class="button-danger" type="button" @click="deleteDialogOpen = true">
            <Trash2 :size="16" aria-hidden="true" />Excluir
          </button>
        </div>
      </div>

      <div v-if="deleteError" class="inline-alert error-alert product-delete-alert" role="alert">
        {{ deleteError }}
      </div>

      <section class="product-detail-section" aria-labelledby="product-identity-heading">
        <h2 id="product-identity-heading">Identificação</h2>
        <dl class="product-detail-grid">
          <div class="product-detail-field"><dt>Nome</dt><dd>{{ produto.nome }}</dd></div>
          <div class="product-detail-field"><dt>Código interno</dt><dd>{{ produto.codigoInterno }}</dd></div>
          <div class="product-detail-field"><dt>Categoria</dt><dd>{{ produto.categoria?.nome ?? 'Categoria não identificada' }}</dd></div>
          <div class="product-detail-field"><dt>Unidade de medida</dt><dd>{{ produto.unidadeMedida }}</dd></div>
          <div class="product-detail-field"><dt>Código de barras</dt><dd>{{ produto.codigoBarras || '—' }}</dd></div>
        </dl>
      </section>

      <section class="product-detail-section" aria-labelledby="product-commercial-heading">
        <h2 id="product-commercial-heading">Comercial e estoque mínimo</h2>
        <dl class="product-detail-grid">
          <div class="product-detail-field"><dt>Preço de custo</dt><dd>{{ formatCurrency(produto.precoCusto) }}</dd></div>
          <div class="product-detail-field"><dt>Preço de venda</dt><dd>{{ formatCurrency(produto.precoVenda) }}</dd></div>
          <div class="product-detail-field"><dt>Estoque mínimo</dt><dd>{{ produto.estoqueMinimo }}</dd></div>
          <div class="product-detail-field"><dt>Estoque máximo</dt><dd>{{ produto.estoqueMaximo ?? '—' }}</dd></div>
        </dl>
      </section>

      <section class="product-detail-section" aria-labelledby="product-description-heading">
        <h2 id="product-description-heading">Descrição</h2>
        <p class="product-detail-text">{{ produto.descricao || '—' }}</p>
        <dl v-if="produto.imagem" class="product-detail-grid product-reference-grid">
          <div class="product-detail-field"><dt>Imagem / referência</dt><dd class="break-anywhere">{{ produto.imagem }}</dd></div>
        </dl>
      </section>

      <section v-if="estoque" class="product-detail-section" aria-labelledby="product-stock-heading">
        <h2 id="product-stock-heading">Estoque atual</h2>
        <dl class="product-detail-grid">
          <div class="product-detail-field"><dt>Quantidade</dt><dd>{{ estoque.quantidade }}</dd></div>
          <div class="product-detail-field"><dt>Atualizado em</dt><dd>{{ formattedStockDate }}</dd></div>
        </dl>
      </section>

      <RouterLink class="button-secondary product-detail-back" to="/produtos">
        <ArrowLeft :size="16" aria-hidden="true" />Voltar para produtos
      </RouterLink>
    </template>

    <ConfirmDialog
      v-if="deleteDialogOpen && produto"
      title="Excluir produto?"
      :message="`O produto “${produto.nome}” será excluído. Se houver registros relacionados, o backend poderá recusar a operação.`"
      confirm-label="Excluir produto"
      :loading="deleting"
      @cancel="deleteDialogOpen = false"
      @confirm="confirmDelete"
    />
  </section>
</template>