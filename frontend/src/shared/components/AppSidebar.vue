<script setup lang="ts">
import {
  ArrowLeftRight,
  Boxes,
  Package,
  PanelLeftClose,
  PanelLeftOpen,
  Receipt,
  ShoppingCart,
  Tag,
  Truck,
  Users,
} from '@lucide/vue'
import { useRoute } from 'vue-router'

defineProps<{
  collapsed: boolean
  mobile: boolean
  mobileOpen: boolean
}>()

const emit = defineEmits<{
  'toggle-collapse': []
  close: []
  navigate: []
}>()

const route = useRoute()

const groups = [
  {
    label: 'Cadastros',
    items: [
      { label: 'Categorias', path: '/categorias', key: 'categorias', icon: Tag },
      { label: 'Produtos', path: '/produtos', key: 'produtos', icon: Package },
      { label: 'Clientes', path: '/clientes', key: 'clientes', icon: Users },
      { label: 'Fornecedores', path: '/fornecedores', key: 'fornecedores', icon: Truck },
    ],
  },
  {
    label: 'Operações',
    items: [
      { label: 'Estoque', path: '/estoque', key: 'estoque', icon: Boxes },
      {
        label: 'Movimentações',
        path: '/estoque/movimentacoes',
        key: 'movimentacoes',
        icon: ArrowLeftRight,
      },
      { label: 'Compras', path: '/compras', key: 'compras', icon: Receipt },
      { label: 'Vendas', path: '/vendas', key: 'vendas', icon: ShoppingCart },
    ],
  },
]
</script>

<template>
  <aside
    class="app-sidebar"
    :class="{
      'is-collapsed': collapsed,
      'is-mobile': mobile,
      'is-open': mobileOpen,
    }"
    :aria-hidden="mobile && !mobileOpen"
    :inert="mobile && !mobileOpen"
  >
    <div class="sidebar-brand">
      <RouterLink class="brand-link" to="/categorias" aria-label="404 ERP - início" @click="emit('navigate')">
        <span class="brand-mark" aria-hidden="true">404</span>
        <span v-if="!collapsed" class="brand-name">404 ERP</span>
      </RouterLink>

      <button
        v-if="!mobile"
        class="icon-button sidebar-collapse-button"
        type="button"
        :aria-label="collapsed ? 'Expandir menu lateral' : 'Recolher menu lateral'"
        :title="collapsed ? 'Expandir menu lateral' : 'Recolher menu lateral'"
        @click="emit('toggle-collapse')"
      >
        <component
          :is="collapsed ? PanelLeftOpen : PanelLeftClose"
          :size="18"
          aria-hidden="true"
        />
      </button>

      <button
        v-if="mobile"
        class="icon-button sidebar-close-button"
        type="button"
        aria-label="Fechar menu"
        @click="emit('close')"
      >
        <PanelLeftClose :size="18" aria-hidden="true" />
      </button>
    </div>

    <nav class="sidebar-navigation" aria-label="Navegação principal">
      <section v-for="group in groups" :key="group.label" class="sidebar-group">
        <h2 v-if="!collapsed" class="sidebar-group-label">{{ group.label }}</h2>
        <ul class="sidebar-list">
          <li v-for="item in group.items" :key="item.key">
            <RouterLink
              class="sidebar-link"
              :class="{ 'is-active': route.meta.navKey === item.key }"
              :to="item.path"
              :title="collapsed ? item.label : undefined"
              :aria-label="collapsed ? item.label : undefined"
              :aria-current="route.meta.navKey === item.key ? 'page' : undefined"
              @click="emit('navigate')"
            >
              <component :is="item.icon" class="sidebar-icon" :size="19" aria-hidden="true" />
              <span v-if="!collapsed" class="sidebar-link-label">{{ item.label }}</span>
            </RouterLink>
          </li>
        </ul>
      </section>
    </nav>
  </aside>
</template>