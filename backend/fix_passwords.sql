-- Fix passwords to Qwe1234 (BCrypt hash)
UPDATE users SET password='$2b$10$m8lm348q0VWqPuxXOdEfSOqvnvbAh2rEkLYi8/JP8q5nqNnEfC2Oq' WHERE id=1;
UPDATE users SET password='$2b$10$m8lm348q0VWqPuxXOdEfSOqvnvbAh2rEkLYi8/JP8q5nqNnEfC2Oq' WHERE id=2;
UPDATE users SET password='$2b$10$m8lm348q0VWqPuxXOdEfSOqvnvbAh2rEkLYi8/JP8q5nqNnEfC2Oq' WHERE id=3;
