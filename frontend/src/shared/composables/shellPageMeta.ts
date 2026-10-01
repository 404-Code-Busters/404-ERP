import { inject, provide, ref, type InjectionKey } from 'vue'

export interface ShellPageMeta {
  title: string
  breadcrumbs: Array<{ label: string; to?: string }>
}

export type SetShellPageMeta = (meta: ShellPageMeta | null) => void

export const shellPageMetaKey: InjectionKey<SetShellPageMeta> = Symbol('shellPageMeta')

export function provideShellPageMeta() {
  const override = ref<ShellPageMeta | null>(null)
  provide(shellPageMetaKey, (meta) => {
    override.value = meta
  })
  return override
}

export function useShellPageMeta() {
  return inject(shellPageMetaKey)
}