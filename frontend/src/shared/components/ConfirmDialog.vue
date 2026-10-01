<script setup lang="ts">
defineProps<{
  title: string
  message: string
  confirmLabel: string
  loading?: boolean
}>()

const emit = defineEmits<{
  confirm: []
  cancel: []
}>()
</script>

<template>
  <div class="dialog-backdrop" @click.self="emit('cancel')">
    <section class="confirm-dialog" role="alertdialog" aria-modal="true" :aria-label="title">
      <h2>{{ title }}</h2>
      <p>{{ message }}</p>
      <div class="dialog-actions">
        <button class="button-secondary" type="button" :disabled="loading" @click="emit('cancel')">
          Cancelar
        </button>
        <button class="button-danger" type="button" :disabled="loading" @click="emit('confirm')">
          {{ loading ? 'Excluindo…' : confirmLabel }}
        </button>
      </div>
    </section>
  </div>
</template>