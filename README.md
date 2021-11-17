# deberts card game

This project is the own implementation of popular card game in CIS states.<br />

The main goal of this project is to improve my programming skills. In details:

- improve my skills in Java-stuff like Java language, Spring Boot and Maven
- learn docker
- learn JS and its Vue
- learn how to write in english :D

### Project setup

This project is built with parent maven which contains backend and frontend parts.

```
deberts
├─┬ backend     → backend module with Spring Boot code
│ ├── src
│ └── pom.xml
├─┬ frontend    → frontend module with Vue.js code
│ ├── src
│ └── pom.xml
└── pom.xml     → Maven parent pom managing both modules
```

### Actual ToDo's:

1. ~~Run project using docker~~
2. Create deck model
    1. Card
    2. Hand
    3. Deck
    4. Trump
3. Create game model
    1. Player
    2. Round
    3. Party
4. Deal cads
    1. First dealer appear to be chosen randomly
    2. Next dealer is a winner of the last round
    3. Each player gets 6 cards
    4. Trump generation