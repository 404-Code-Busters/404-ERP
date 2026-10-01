<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import AppHeader from '../shared/components/AppHeader.vue'
import AppSidebar from '../shared/components/AppSidebar.vue'
import { provideShellPageMeta } from '../shared/composables/shellPageMeta'

interface BreadcrumbItem {
  label: string
  to?: string
}

const route = useRoute()
const pageMetaOverride = provideShellPageMeta()
const viewportWidth = ref(window.innerWidth)
const sidebarCollapsed = ref(window.innerWidth < 1280)
const mobileSidebarOpen = ref(false)
const isMobile = computed(() => viewportWidth.value < 768)
const pageTitle = computed(() => pageMetaOverride.value?.title ?? String(route.meta.title ?? '404 ERP'))
const breadcrumbs = computed(
  () => pageMetaOverride.value?.breadcrumbs
    ?? (route.meta.breadcrumbs as BreadcrumbItem[] | undefined)
    ?? [],
)

function updateViewport() {
  const wasMobile = isMobile.value
  viewportWidth.value = window.innerWidth

  if (wasMobile !== (window.innerWidth < 768)) {
    mobileSidebarOpen.value = false
  }

  if (window.innerWidth >= 768) {
    sidebarCollapsed.value = window.innerWidth < 1280
  }
}

function toggleSidebar() {
  if (isMobile.value) {
    mobileSidebarOpen.value = !mobileSidebarOpen.value
    return
  }

  sidebarCollapsed.value = !sidebarCollapsed.value
}

function closeMobileSidebar() {
  mobileSidebarOpen.value = false
}

function handleKeydown(event: KeyboardEvent) {
  if (event.key === 'Escape') {
    closeMobileSidebar()
  }
}

watch(() => route.fullPath, closeMobileSidebar)

onMounted(() => {
  window.addEventListener('resize', updateViewport)
  window.addEventListener('keydown', handleKeydown)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', updateViewport)
  window.removeEventListener('keydown', handleKeydown)
})
</script>

<template>
  <div
    class="app-shell"
    :class="{
      'sidebar-collapsed': sidebarCollapsed && !isMobile,
      'mobile-mode': isMobile,
      'mobile-sidebar-open': mobileSidebarOpen,
    }"
  >
    <AppSidebar
      :collapsed="sidebarCollapsed && !isMobile"
      :mobile="isMobile"
      :mobile-open="mobileSidebarOpen"
      @toggle-collapse="toggleSidebar"
      @close="closeMobileSidebar"
      @navigate="closeMobileSidebar"
    />

    <button
      v-if="isMobile && mobileSidebarOpen"
      class="sidebar-backdrop"
      type="button"
      aria-label="Fechar menu"
      @click="closeMobileSidebar"
    />

    <div class="app-main">
      <AppHeader
        :title="pageTitle"
        :breadcrumbs="breadcrumbs"
        :mobile="isMobile"
        :sidebar-collapsed="sidebarCollapsed"
        @toggle-sidebar="toggleSidebar"
      />

      <main id="main-content" class="app-content" tabindex="-1">
        <div class="content-container">
          <RouterView />
        </div>
      </main>
    </div>
  </div>
</template>