-- Получить всех студентов, возраст которых находится между 10 и 20
-- (можно подставить любые числа, главное, чтобы нижняя граница была меньше верхней).

SELECT *
FROM student
WHERE age BETWEEN 10 AND 13;

-- Получить всех студентов, но отобразить только список их имен.

SELECT name
FROM student

-- Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).

SELECT *
FROM student
WHERE name LIKE '%о%';

-- Получить всех студентов, у которых возраст меньше идентификатора.

SELECT *
FROM student
WHERE age < id;

-- Получить всех студентов упорядоченных по возрасту.

SELECT *
FROM student
ORDER BY age;