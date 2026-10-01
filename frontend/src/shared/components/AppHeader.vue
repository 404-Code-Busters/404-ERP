<script setup lang="ts">
import { Menu } from '@lucide/vue'
import Breadcrumbs from './Breadcrumbs.vue'

interface BreadcrumbItem {
  label: string
  to?: string
}

defineProps<{
  title: string
  breadcrumbs: BreadcrumbItem[]
  mobile: boolean
  sidebarCollapsed: boolean
}>()

const emit = defineEmits<{
  'toggle-sidebar': []
}>()
</script>

<template>
  <header class="app-header">
    <button
      class="icon-button header-menu-button"
      type="button"
      :aria-label="mobile ? 'Abrir menu' : sidebarCollapsed ? 'Expandir menu lateral' : 'Recolher menu lateral'"
      @click="emit('toggle-sidebar')"
    >
      <Menu :size="20" aria-hidden="true" />
    </button>

    <div class="header-heading">
      <Breadcrumbs :items="breadcrumbs" />
      <h1 class="header-title">{{ title }}</h1>
    </div>

    <div class="header-actions">
      <slot name="actions" />
    </div>
  </header>
</template>