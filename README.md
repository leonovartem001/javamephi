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


Схема модели находится в файле `mission-structure.md`.
