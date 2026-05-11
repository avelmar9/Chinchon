# Chinchón - Proyecto Java

## Descripción del proyecto

Este proyecto implementa el juego de cartas **Chinchón** utilizando programación orientada a objetos en Java.

El objetivo principal es simular partidas entre jugadores humanos y jugadores automáticos, permitiendo validar combinaciones de cartas, gestionar rondas y controlar el desarrollo completo de la partida.

El proyecto está organizado siguiendo una arquitectura sencilla basada en clases de dominio.

---

# Objetivos del proyecto

- Aplicar conceptos de Programación Orientada a Objetos.
- Modelar un juego de cartas real mediante clases Java.
- Implementar validaciones de combinaciones y reglas del juego.
- Gestionar interacción por consola.
- Practicar pruebas unitarias con JUnit.

---

# Tecnologías utilizadas

- Java
- JUnit 5
- Eclipse

---

# Estructura del proyecto

```text
src/
 ├── app/
 │    └── Main.java
 │
 └── domain/
      ├── AIPlayer.java
      ├── Card.java
      ├── CombinationValidator.java
      ├── ConsoleInput.java
      ├── Deck.java
      ├── DiscardDeck.java
      ├── Game.java
      ├── GameSetUp.java
      ├── GameSetUpBuilder.java
      ├── Hand.java
      ├── HumanPlayer.java
      ├── Player.java
      ├── Round.java
      ├── Suit.java
      └── Value.java
```

---

# Explicación de las clases

## Main

Clase principal del proyecto.

Responsabilidades:

- Iniciar la aplicación.
- Crear la configuración inicial.
- Lanzar la partida.

---

## Card

Representa una carta de la baraja española.

Atributos principales:

- Palo (`Suit`)
- Valor (`Value`)

Funciones:

- Obtener información de la carta.
- Mostrar la carta en formato legible.

---

## Suit

Enumeración que representa los palos de la baraja española.

Ejemplos:

- CLUBS
- CUPS
- GOLD
- SWORDS

---

## Value

Enumeración que representa los valores posibles de las cartas.

Ejemplos:

- ONE
- TWO
- THREE
- ...
- SEVEN
- TEN
- ELEVEN
- TWELVE

Incluye además el valor numérico utilizado para validar escaleras.

---

## Deck

Representa el mazo principal del juego.

Responsabilidades:

- Crear las cartas.
- Barajar el mazo.
- Repartir cartas.
- Controlar las cartas restantes.

---

## DiscardDeck

Representa el montón de descarte.

Funciones:

- Añadir cartas descartadas.
- Obtener la carta superior.
- Gestionar el descarte durante la partida.

---

## Hand

Representa la mano de un jugador.

Funciones:

- Guardar cartas.
- Añadir cartas.
- Eliminar cartas.
- Mostrar la mano.

---

## Player

Clase base de los jugadores.

Contiene:

- Nombre del jugador.
- Mano de cartas.
- Puntuación.

Define el comportamiento general de cualquier jugador.

---

## HumanPlayer

Jugador controlado por una persona.

Responsabilidades:

- Leer acciones desde consola.
- Elegir cartas.
- Realizar jugadas.

---

## AIPlayer

Jugador automático controlado por la máquina.

Funciones:

- Tomar decisiones automáticamente.
- Evaluar cartas.
- Elegir descartes.

---

## Round

Controla el desarrollo de una ronda.

Responsabilidades:

- Gestionar turnos.
- Comprobar combinaciones.
- Controlar cuándo termina una ronda.

---

## Game

Clase principal de lógica del juego.

Funciones:

- Gestionar jugadores.
- Iniciar rondas.
- Controlar el flujo completo de la partida.
- Determinar ganador.

---

## GameSetUp

Representa la configuración inicial de la partida.

Puede incluir:

- Número de jugadores.
- Tipo de jugadores.
- Configuración general.

---

## GameSetUpBuilder

Implementa el patrón Builder para crear configuraciones de partida de forma flexible.

Ventajas:

- Código más limpio.
- Configuración modular.
- Mayor legibilidad.

---

## ConsoleInput

Clase auxiliar para leer datos desde consola.

Funciones:

- Validar entradas.
- Evitar errores de lectura.
- Centralizar la interacción con el usuario.

---

## CombinationValidator

Clase encargada de validar combinaciones de cartas.

Es una de las clases más importantes del proyecto.

Funciones principales:

### isValidCombination()

Comprueba si una combinación es válida.

Valida:

- Grupos de cartas iguales.
- Escaleras.
- Tamaño mínimo de grupo.

---

### areEquals()

Comprueba si todas las cartas tienen el mismo valor.



---

### isStraight()

Comprueba si las cartas forman una escalera.

Reglas:

- Todas deben ser del mismo palo.
- Deben ser consecutivas.
- Se permite el salto especial del 7 al 10.



---

### isChinchon()

Comprueba si las 7 cartas forman un chinchón.

Condiciones:

- Deben existir exactamente 7 cartas.
- Deben formar una escalera válida.

---

# Relaciones entre clases

## Herencia

La herencia permite reutilizar comportamiento común entre clases.

Relaciones utilizadas en el proyecto:

- `Player` → `HumanPlayer`
- `Player` → `AIPlayer`

Los jugadores humanos y automáticos heredan atributos y métodos generales definidos en `Player`.

---

## Dependencia

La dependencia ocurre cuando una clase utiliza otra temporalmente para realizar alguna operación.

Relaciones de dependencia del proyecto:

- `CombinationValidator` → `Card`
- `GameSetUpBuilder` → `HumanPlayer`
- `GameSetUpBuilder` → `AIPlayer`
- `GameSetUpBuilder` → `Game`
- `GameSetUpBuilder` → `ConsoleInput`
- `GameSetUp` → `GameSetUpBuilder`
- `GameSetUp` → `Game`
- `Main` → `GameSetUp`
- `Main` → `ConsoleInput`

---

## Composición

La composición representa una relación fuerte donde una clase contiene otra y controla completamente su ciclo de vida.

Relaciones de composición:

- `Hand` → `Card`
- `Deck` → `Card`
- `DiscardDeck` → `Card`
- `Round` → `Deck`
- `Round` → `DiscardDeck`
- `Player` → `Hand`
- `Player` → `CombinationValidator`

En estas relaciones, los objetos contenidos forman parte esencial de la clase principal.

---

## Agregación

La agregación representa una relación más débil entre objetos.

Relaciones de agregación:

- `Game` → `Player`
- `Round` → `Player`
- `GameSetUp` → `ConsoleInput`
- `HumanPlayer` → `ConsoleInput`

Los objetos relacionados pueden existir independientemente.

---

## Asociación

La asociación representa una relación estructural entre clases.

Relaciones de asociación:

- `Card` → `Suit`
- `Card` → `Value`

Cada carta está asociada a un palo y un valor.

---

# Pruebas unitarias

El proyecto incluye pruebas unitarias utilizando JUnit 5.

## Objetivo de los tests

Verificar que las reglas del juego funcionan correctamente.

Se comprueban:

- Grupos válidos.
- Grupos inválidos.
- Escaleras.
- Chinchón.
- Casos con palos incorrectos.

---

