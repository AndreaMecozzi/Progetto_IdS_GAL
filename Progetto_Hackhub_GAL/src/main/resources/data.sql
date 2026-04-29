INSERT INTO team (nome) VALUES ('I Lupi di Spring');
INSERT INTO team (nome) VALUES ('Code Breakers');
INSERT INTO team (nome) VALUES ('Syntax Error');

-- =========================================================================
-- ATTENZIONE: Per facilitare i test su Postman, TUTTI gli utenti in questo
-- file hanno la stessa password in chiaro: password123
-- =========================================================================

-- Assegniamo Mario e Luca al Team 1 ("I Lupi di Spring")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('mario_rossi', 'mario@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 1);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('luca_bianchi', 'luca@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 1);

-- Staff (Nessun team assegnato)
INSERT INTO utente (username, email, password, ruolo)
VALUES ('giudice_master', 'giudice@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'GIUDICE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('mentore_esperto', 'mentore@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'MENTORE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('mentore_2_prova', 'mentore2@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'MENTORE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('admin_org', 'org@hackhub.it', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'ORGANIZZATORE');

-- Assegniamo Anna e Marco al Team 2 ("Code Breakers")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('anna_verdi', 'anna@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 2);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('marco_neri', 'marco@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 2);

-- Assegniamo Giulia e Paolo al Team 3 ("Syntax Error")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('giulia_gialli', 'giulia@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 3);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('paolo_blu', 'paolo@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE', 3);

-- Altri utenti senza team
INSERT INTO utente (username, email, password, ruolo)
VALUES ('elena_fabbri', 'elena@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('simone_conti', 'simone@example.com', '$2a$10$kypbnGGCpJ7UQlysnqzJG.6H.dUewn7UPVWA3Ip.E.8U4jlVnFNnu', 'UTENTE');