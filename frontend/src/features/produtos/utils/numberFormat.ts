export function formatCurrency(value: number | null | undefined): string {
  if (value === null || value === undefined || !Number.isFinite(value)) return '—'

  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(value)
}

export function parseDecimal(value: string, scale: number, precision = 12): number | null {
  const trimmed = value.trim()
  if (!trimmed) return null

  const compact = trimmed.replace(/\s/g, '')
  const normalized = compact.includes(',')
    ? compact.replace(/\./g, '').replace(',', '.')
    : compact
  if (!/^-?\d+(\.\d+)?$/.test(normalized)) return null

  const [integerPart, decimalPart = ''] = normalized.split('.')
  const decimals = decimalPart.length
  if (decimals > scale) return null
  if (integerPart.replace('-', '').length > precision - scale) return null

  const parsed = Number(normalized)
  return Number.isFinite(parsed) ? parsed : null
}

export function formatDecimalInput(value: number | null | undefined, scale: number): string {
  if (value === null || value === undefined || !Number.isFinite(value)) return ''

  return new Intl.NumberFormat('pt-BR', {
    minimumFractionDigits: scale,
    maximumFractionDigits: scale,
    useGrouping: false,
  }).format(value)
}