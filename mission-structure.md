# Свиток структуры проклятых данных

## Внутренняя модель приложения

### Mission
- `missionId: String`
- `date: LocalDate`
- `location: String`
- `outcome: MissionOutcome`
- `damageCost: long`
- `curse: Curse`
- `sorcerers: List<Sorcerer>`
- `techniques: List<Technique>`
- `comment: String`

### Curse
- `name: String`
- `threatLevel: ThreatLevel`

### Sorcerer
- `name: String`
- `rank: String`

### Technique
- `name: String`
- `type: String`
- `owner: String`
- `damage: long`

## Связи

- одна `Mission` содержит **одно** `Curse`;
- одна `Mission` содержит **много** `Sorcerer`;
- одна `Mission` содержит **много** `Technique`.

## Логика работы

1. Пользователь выбирает файл миссии.
2. Приложение определяет формат по расширению.
3. Нужный парсер считывает данные.
4. Все данные приводятся к объекту `Mission`.
5. На экран выводится структурированная сводка.
