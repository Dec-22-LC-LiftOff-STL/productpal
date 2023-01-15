 -- Part 1: Test it with SQL
-- id INTEGER PRIMARY KEY ,
-- employer varchar (255),
-- name varchar (255),
-- skills varchar (255);
-- Part 2: Test it with SQL
SELECT name FROM category WHERE description = "St. Louis City";
-- Part 3: Test it with SQL
DROP TABLE product;
-- Part 4: Test it with SQL
SELECT * FROM skill
INNER JOIN product_skills ON skill.id = product_skills.skills_id
WHERE product_skills.jobs_id IS NOT NULL
ORDER BY name ASC;