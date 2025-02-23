# Miku User Guide

![Ui of the app Miku.](Ui.png)

Use Miku to keep track of a list of tasks and events you have, as well as track any expenses!

## Adding deadlines

Add any important deadlines you have, indicate the date and any expenses.

`deadline <description> (/expenses <expenses>) /by <date>`
Example: 
`deadline feed cat /expenses 5.25 /by Jan 7th 2025 5pm`


```
Got it. I've added this task:
[D][ ] feed cat /expenses 5.25 (by: Jan 7th 2025 5pm)
Now you have 7 tasks in this list.
```

## Adding todos

Add any important takss you have, indicating any expenses.

`todo <description> (/expenses <expenses>)`
Example: 
`todo feed cat /expenses 5.25`


```
Got it. I've added this task:
[T][ ] feed cat /expenses 5.25
Now you have 7 tasks in this list.
```

## Adding events

Add any important events you have, indicate from when to when and any expenses.

`event <description> (/expenses <expenses>) /from <date> /to <date>`
Example: 
`event feed cat /expenses 5.25 /from Jan 7th 2025 5pm /to Jan 7th 2025 6pm`


```
Got it. I've added this task:
[E][ ] feed cat /expenses 5.25 (from: Jan 7th 2025 5pm to: Jan 7th 2025 6pm)
Now you have 7 tasks in this list.
```