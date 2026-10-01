<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { buscarProduto, criarProduto, listarCategorias, atualizarProduto } from '../api/produtoApi'
import type { Categoria } from '../../categorias/types'
import type { Produto, ProdutoRequest } from '../types'
import { formatDecimalInput, parseDecimal } from '../utils/numberFormat'

const route = useRoute()
const router = useRouter()
const editing = computed(() => route.name === 'produto-editar')
const categorias = ref<Categoria[]>([])
const loading = ref(true)
const submitting = ref(false)
const pageError = ref('')
const submitError = ref('')
const errors = ref<Record<string, string>>({})

const form = reactive({
  categoriaId: '',
  nome: '',
  codigoInterno: '',
  codigoBarras: '',
  descricao: '',
  unidadeMedida: '',
  precoCusto: '',
  precoVenda: '',
  estoqueMinimo: '0,000',
  estoqueMaximo: '',
  imagem: '',
  status: true,
})

function fillForm(produto: Produto) {
  form.categoriaId = produto.categoria?.id ? String(produto.categoria.id) : ''
  form.nome = produto.nome ?? ''
  form.codigoInterno = produto.codigoInterno ?? ''
  form.codigoBarras = produto.codigoBarras ?? ''
  form.descricao = produto.descricao ?? ''
  form.unidadeMedida = produto.unidadeMedida ?? ''
  form.precoCusto = formatDecimalInput(produto.precoCusto, 2)
  form.precoVenda = formatDecimalInput(produto.precoVenda, 2)
  form.estoqueMinimo = formatDecimalInput(produto.estoqueMinimo, 3)
  form.estoqueMaximo = formatDecimalInput(produto.estoqueMaximo, 3)
  form.imagem = produto.imagem ?? ''
  form.status = produto.status
}

async function loadFormData() {
  loading.value = true
  pageError.value = ''
  try {
    const categoryPromise = listarCategorias()
    if (editing.value) {
      const [categoryList, produto] = await Promise.all([
        categoryPromise,
        buscarProduto(String(route.params.id)),
      ])
      categorias.value = categoryList
      fillForm(produto)
    } else {
      categorias.value = await categoryPromise
    }
  } catch (error) {
    pageError.value = error instanceof Error
      ? error.message
      : 'Não foi possível carregar os dados necessários. Tente novamente.'
  } finally {
    loading.value = false
  }
}

watch(() => route.params.id, () => {
  if (editing.value) void loadFormData()
})

function validateForm(): boolean {
  const nextErrors: Record<string, string> = {}
  const requiredText = [
    ['nome', form.nome, 'Informe o nome do produto.'],
    ['codigoInterno', form.codigoInterno, 'Informe o código interno.'],
    ['unidadeMedida', form.unidadeMedida, 'Informe a unidade de medida.'],
  ] as const

  for (const [field, value, message] of requiredText) {
    if (!value.trim()) nextErrors[field] = message
  }

  if (form.nome.trim().length > 150) nextErrors.nome = 'Use no máximo 150 caracteres.'
  if (form.codigoInterno.trim().length > 50) nextErrors.codigoInterno = 'Use no máximo 50 caracteres.'
  if (form.codigoBarras.trim().length > 50) nextErrors.codigoBarras = 'Use no máximo 50 caracteres.'
  if (form.unidadeMedida.trim().length > 10) nextErrors.unidadeMedida = 'Use no máximo 10 caracteres.'
  if (form.imagem.trim().length > 255) nextErrors.imagem = 'Use no máximo 255 caracteres.'

  if (!form.categoriaId || !categorias.value.some((categoria) => categoria.id === Number(form.categoriaId))) {
    nextErrors.categoriaId = 'Selecione uma categoria válida.'
  }

  if (parseDecimal(form.precoCusto, 2) === null) nextErrors.precoCusto = 'Use até 10 dígitos inteiros e 2 casas decimais.'
  if (parseDecimal(form.precoVenda, 2) === null) nextErrors.precoVenda = 'Use até 10 dígitos inteiros e 2 casas decimais.'
  if (parseDecimal(form.estoqueMinimo, 3) === null) nextErrors.estoqueMinimo = 'Use até 9 dígitos inteiros e 3 casas decimais.'
  if (form.estoqueMaximo.trim() && parseDecimal(form.estoqueMaximo, 3) === null) {
    nextErrors.estoqueMaximo = 'Use até 9 dígitos inteiros e 3 casas decimais.'
  }

  errors.value = nextErrors
  return Object.keys(nextErrors).length === 0
}

function buildPayload(): ProdutoRequest {
  return {
    categoria: { id: Number(form.categoriaId) },
    nome: form.nome.trim(),
    codigoInterno: form.codigoInterno.trim(),
    codigoBarras: form.codigoBarras.trim() || null,
    descricao: form.descricao.trim() || null,
    unidadeMedida: form.unidadeMedida.trim(),
    precoCusto: parseDecimal(form.precoCusto, 2) as number,
    precoVenda: parseDecimal(form.precoVenda, 2) as number,
    estoqueMinimo: parseDecimal(form.estoqueMinimo, 3) as number,
    estoqueMaximo: form.estoqueMaximo.trim() ? parseDecimal(form.estoqueMaximo, 3) : null,
    imagem: form.imagem.trim() || null,
    status: form.status,
  }
}

async function submitForm() {
  submitError.value = ''
  if (!validateForm()) return

  submitting.value = true
  try {
    const payload = buildPayload()
    const saved = editing.value
      ? await atualizarProduto(String(route.params.id), payload)
      : await criarProduto(payload)
    await router.push(`/produtos/${saved.id}`)
  } catch (error) {
    submitError.value = error instanceof Error
      ? error.message
      : 'Não foi possível salvar o produto. Tente novamente.'
  } finally {
    submitting.value = false
  }
}

function cancel() {
  if (editing.value) {
    void router.push(`/produtos/${String(route.params.id)}`)
  } else {
    void router.push('/produtos')
  }
}

function retry() {
  void loadFormData()
}

function validateOnInput(field: string) {
  if (errors.value[field]) validateForm()
}

function formatDecimalField(field: 'precoCusto' | 'precoVenda' | 'estoqueMinimo' | 'estoqueMaximo', scale: number) {
  const value = parseDecimal(form[field], scale)
  if (value !== null) form[field] = formatDecimalInput(value, scale)
}

onMounted(loadFormData)
</script>

<template>
  <section class="product-form-page">
    <section v-if="loading" class="product-form-state" role="status" aria-label="Carregando dados do formulário">
      <span class="skeleton-block skeleton-detail-title" />
      <span class="skeleton-block skeleton-detail-line" />
      <span class="skeleton-block skeleton-detail-line short" />
    </section>

    <section v-else-if="pageError" class="product-state error-state" role="alert">
      <h2>Não foi possível carregar o formulário</h2>
      <p>{{ pageError }}</p>
      <button class="button-secondary" type="button" @click="retry">Tentar novamente</button>
    </section>

    <form v-else class="product-form" novalidate @submit.prevent="submitForm">
      <section class="product-form-section">
        <h2>Identificação</h2>
        <div class="product-form-grid">
          <div class="product-field">
            <label for="product-category">Categoria <span aria-hidden="true">*</span></label>
            <select
              id="product-category"
              v-model="form.categoriaId"
              class="form-control"
              required
              :aria-invalid="Boolean(errors.categoriaId)"
              @change="validateOnInput('categoriaId')"
            >
              <option value="" disabled>Selecione uma categoria</option>
              <option v-for="categoria in categorias" :key="categoria.id" :value="String(categoria.id)">
                {{ categoria.nome }}
              </option>
            </select>
            <span v-if="!categorias.length" class="field-help">Nenhuma categoria disponível.</span>
            <span v-if="errors.categoriaId" class="field-error" role="alert">{{ errors.categoriaId }}</span>
          </div>

          <div class="product-field">
            <label for="product-name">Nome <span aria-hidden="true">*</span></label>
            <input id="product-name" v-model="form.nome" class="form-control" maxlength="150" required :aria-invalid="Boolean(errors.nome)" @input="validateOnInput('nome')" />
            <span v-if="errors.nome" class="field-error" role="alert">{{ errors.nome }}</span>
          </div>

          <div class="product-field">
            <label for="product-internal-code">Código interno <span aria-hidden="true">*</span></label>
            <input id="product-internal-code" v-model="form.codigoInterno" class="form-control" maxlength="50" required :aria-invalid="Boolean(errors.codigoInterno)" @input="validateOnInput('codigoInterno')" />
            <span v-if="errors.codigoInterno" class="field-error" role="alert">{{ errors.codigoInterno }}</span>
          </div>

          <div class="product-field">
            <label for="product-barcode">Código de barras</label>
            <input id="product-barcode" v-model="form.codigoBarras" class="form-control" maxlength="50" :aria-invalid="Boolean(errors.codigoBarras)" @input="validateOnInput('codigoBarras')" />
            <span v-if="errors.codigoBarras" class="field-error" role="alert">{{ errors.codigoBarras }}</span>
          </div>

          <div class="product-field">
            <label for="product-unit">Unidade de medida <span aria-hidden="true">*</span></label>
            <input id="product-unit" v-model="form.unidadeMedida" class="form-control" maxlength="10" required :aria-invalid="Boolean(errors.unidadeMedida)" @input="validateOnInput('unidadeMedida')" />
            <span v-if="errors.unidadeMedida" class="field-error" role="alert">{{ errors.unidadeMedida }}</span>
          </div>
        </div>
      </section>

      <section class="product-form-section">
        <h2>Descrição</h2>
        <div class="product-form-grid">
          <div class="product-field is-full-width">
            <label for="product-description">Descrição</label>
            <textarea id="product-description" v-model="form.descricao" class="form-control form-textarea" rows="4" />
          </div>
          <div class="product-field is-full-width">
            <label for="product-image">Imagem (URL ou referência)</label>
            <input id="product-image" v-model="form.imagem" class="form-control" maxlength="255" :aria-invalid="Boolean(errors.imagem)" @input="validateOnInput('imagem')" />
            <span class="field-help">Informe uma referência textual. Envio de arquivo não está disponível.</span>
            <span v-if="errors.imagem" class="field-error" role="alert">{{ errors.imagem }}</span>
          </div>
        </div>
      </section>

      <section class="product-form-section">
        <h2>Comercial e estoque</h2>
        <div class="product-form-grid">
          <div class="product-field">
            <label for="product-cost">Preço de custo <span aria-hidden="true">*</span></label>
            <div class="currency-control"><span aria-hidden="true">R$</span><input id="product-cost" v-model="form.precoCusto" class="form-control" type="text" inputmode="decimal" required :aria-invalid="Boolean(errors.precoCusto)" @input="validateOnInput('precoCusto')" @blur="formatDecimalField('precoCusto', 2)" /></div>
            <span v-if="errors.precoCusto" class="field-error" role="alert">{{ errors.precoCusto }}</span>
          </div>

          <div class="product-field">
            <label for="product-price">Preço de venda <span aria-hidden="true">*</span></label>
            <div class="currency-control"><span aria-hidden="true">R$</span><input id="product-price" v-model="form.precoVenda" class="form-control" type="text" inputmode="decimal" required :aria-invalid="Boolean(errors.precoVenda)" @input="validateOnInput('precoVenda')" @blur="formatDecimalField('precoVenda', 2)" /></div>
            <span v-if="errors.precoVenda" class="field-error" role="alert">{{ errors.precoVenda }}</span>
          </div>

          <div class="product-field">
            <label for="product-stock-min">Estoque mínimo <span aria-hidden="true">*</span></label>
            <input id="product-stock-min" v-model="form.estoqueMinimo" class="form-control" type="text" inputmode="decimal" required :aria-invalid="Boolean(errors.estoqueMinimo)" @input="validateOnInput('estoqueMinimo')" @blur="formatDecimalField('estoqueMinimo', 3)" />
            <span v-if="errors.estoqueMinimo" class="field-error" role="alert">{{ errors.estoqueMinimo }}</span>
          </div>

          <div class="product-field">
            <label for="product-stock-max">Estoque máximo</label>
            <input id="product-stock-max" v-model="form.estoqueMaximo" class="form-control" type="text" inputmode="decimal" :aria-invalid="Boolean(errors.estoqueMaximo)" @input="validateOnInput('estoqueMaximo')" @blur="formatDecimalField('estoqueMaximo', 3)" />
            <span v-if="errors.estoqueMaximo" class="field-error" role="alert">{{ errors.estoqueMaximo }}</span>
          </div>

          <div class="product-field product-status-field">
            <span class="field-label">Status</span>
            <button class="status-switch" :class="{ 'is-on': form.status }" type="button" role="switch" :aria-checked="form.status" aria-label="Produto ativo" @click="form.status = !form.status">
              <span class="switch-track" aria-hidden="true"><span class="switch-thumb" /></span>
              <span>Ativo</span>
            </button>
          </div>
        </div>
      </section>

      <div v-if="submitError" class="inline-alert error-alert" role="alert">{{ submitError }}</div>

      <div class="form-actions">
        <button class="button-secondary" type="button" :disabled="submitting" @click="cancel">Cancelar</button>
        <button class="button-primary" type="submit" :disabled="submitting">
          <span v-if="submitting" class="button-spinner" aria-hidden="true" />
          {{ submitting ? 'Salvando…' : 'Salvar produto' }}
        </button>
      </div>
    </form>
  </section>
</template>