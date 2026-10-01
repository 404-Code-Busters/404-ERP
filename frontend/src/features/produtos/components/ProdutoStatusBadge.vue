<script setup lang="ts">
defineProps<{
  active: boolean
}>()
</script>

<template>
  <span class="product-status-badge" :class="active ? 'is-active' : 'is-inactive'">
    <span class="product-status-dot" aria-hidden="true" />
    {{ active ? 'Ativo' : 'Inativo' }}
  </span>
</template>