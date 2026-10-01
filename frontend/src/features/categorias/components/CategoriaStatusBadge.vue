<script setup lang="ts">
defineProps<{
  active: boolean
}>()
</script>

<template>
  <span class="category-status" :class="active ? 'is-active' : 'is-inactive'">
    <span class="category-status-dot" aria-hidden="true" />
    {{ active ? 'Ativa' : 'Inativa' }}
  </span>
</template>