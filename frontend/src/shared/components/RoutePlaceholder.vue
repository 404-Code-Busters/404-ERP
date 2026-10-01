<script setup lang="ts">
import { useRoute } from 'vue-router'

const route = useRoute()
</script>

<template>
  <section class="route-placeholder" aria-labelledby="placeholder-heading">
    <h2 id="placeholder-heading">{{ route.meta.title }}</h2>
    <p>Conteúdo desta seção será implementado em uma etapa posterior.</p>
  </section>
</template>