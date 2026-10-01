<script setup lang="ts">
interface BreadcrumbItem {
  label: string
  to?: string
}

defineProps<{
  items: BreadcrumbItem[]
}>()
</script>

<template>
  <nav v-if="items.length" class="breadcrumbs" aria-label="Trilha de navegação">
    <ol>
      <li v-for="(item, index) in items" :key="`${item.label}-${index}`">
        <RouterLink v-if="item.to" :to="item.to">{{ item.label }}</RouterLink>
        <span v-else :aria-current="index === items.length - 1 ? 'page' : undefined">
          {{ item.label }}
        </span>
      </li>
    </ol>
  </nav>
</template>