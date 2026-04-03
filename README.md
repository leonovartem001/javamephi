# Magic Mission Analyzer

Лабораторная работа №1: **Локальный анализатор миссий магов**.

Приложение открывает локальный файл миссии в формате **TXT / JSON / XML**, автоматически определяет формат, преобразует данные в единую внутреннюю модель и показывает структурированную сводку по миссии.

## Что реализовано

- desktop Java-приложение на **Swing**;
- загрузка файла из файловой системы;
- распознавание форматов `.txt`, `.json`, `.xml`;
- единая модель миссии:
  - миссия;
  - цель-проклятие;
  - участники;
  - техники;
  - итог и комментарий;
- читаемый вывод для аналитика;
- тесты для примеров файлов;
- готовая структура для GitHub-репозитория.

## Структура проекта

```text
src/
  main/java/com/example/missions/
  main/resources/samples/
  test/java/com/example/missions/
docs/
```

## Запуск

### Через Maven

```bash
git clone <repo-url>
cd magic-mission-analyzer
mvn clean package
mvn exec:java -Dexec.mainClass="com.example.missions.Main"
```

### Через jar

```bash
java -jar target/magic-mission-analyzer-1.0.0.jar
```

## Данные миссий

В проект добавлены файлы из присланного архива:

- `variant-1/mission-a.txt`
- `variant-1/mission-a.json`
- `variant-1/mission-a.xml`
- `variant-2/mission-b.txt`
- `variant-2/mission-b.json`
- `variant-2/mission-b.xml`

## Что важно для защиты

Программа работает с **единой внутренней моделью**, даже если входные форматы отличаются. Это соответствует требованию лабораторной: аналитик открывает любой файл и получает одинаково понятный результат.

Схема модели находится в файле `docs/mission-structure.md`.
