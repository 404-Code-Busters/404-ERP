<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { criarCategoria } from '../api/categoriaApi'

const router = useRouter()
const nome = ref('')
const descricao = ref('')
const status = ref(true)
const nomeError = ref('')
const submitError = ref('')
const submitting = ref(false)

function validateNome(): boolean {
  const value = nome.value.trim()

  if (!value) {
    nomeError.value = 'Informe o nome da categoria.'
    return false
  }

  if (value.length > 100) {
    nomeError.value = 'O nome deve ter no máximo 100 caracteres.'
    return false
  }

  nomeError.value = ''
  return true
}

async function submitForm() {
  submitError.value = ''
  if (!validateNome()) return

  submitting.value = true
  try {
    const categoria = await criarCategoria({
      nome: nome.value.trim(),
      descricao: descricao.value.trim() || null,
      status: status.value,
    })
    await router.push(`/categorias/${categoria.id}`)
  } catch (error) {
    submitError.value = error instanceof Error
      ? error.message
      : 'Não foi possível salvar a categoria. Tente novamente.'
  } finally {
    submitting.value = false
  }
}

function cancel() {
  void router.push('/categorias')
}
</script>

<template>
  <section class="category-form-page">
    <form class="category-form" novalidate @submit.prevent="submitForm">
      <div class="category-form-section">
        <h2>Dados da categoria</h2>

        <div class="form-field">
          <label for="category-name">Nome <span aria-hidden="true">*</span></label>
          <input
            id="category-name"
            v-model="nome"
            class="form-control"
            type="text"
            placeholder="Ex.: Acessórios"
            maxlength="100"
            required
            :aria-invalid="Boolean(nomeError)"
            :aria-describedby="nomeError ? 'category-name-error' : 'category-name-help'"
            @blur="validateNome"
            @input="nomeError && validateNome()"
          />
          <span id="category-name-help" class="field-help">Obrigatório. Máximo de 100 caracteres.</span>
          <span v-if="nomeError" id="category-name-error" class="field-error" role="alert">
            {{ nomeError }}
          </span>
        </div>

        <div class="form-field">
          <label for="category-description">Descrição</label>
          <textarea
            id="category-description"
            v-model="descricao"
            class="form-control form-textarea"
            rows="4"
            placeholder="Descreva a categoria (opcional)"
          />
        </div>

        <div class="form-field status-field">
          <span class="field-label">Status</span>
          <button
            class="status-switch"
            :class="{ 'is-on': status }"
            type="button"
            role="switch"
            :aria-checked="status"
            aria-label="Categoria ativa"
            @click="status = !status"
          >
            <span class="switch-track" aria-hidden="true"><span class="switch-thumb" /></span>
            <span>Ativa</span>
          </button>
        </div>
      </div>

      <div v-if="submitError" class="inline-alert error-alert" role="alert">
        {{ submitError }}
      </div>

      <div class="form-actions">
        <button class="button-secondary" type="button" :disabled="submitting" @click="cancel">
          Cancelar
        </button>
        <button class="button-primary" type="submit" :disabled="submitting">
          <span v-if="submitting" class="button-spinner" aria-hidden="true" />
          {{ submitting ? 'Salvando…' : 'Salvar' }}
        </button>
      </div>
    </form>
  </section>
</template>