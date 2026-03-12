
INSERT INTO team (nome) VALUES ('I Lupi di Spring');  -- Genererà team_id = 1
INSERT INTO team (nome) VALUES ('Code Breakers');     -- Genererà team_id = 2
INSERT INTO team (nome) VALUES ('Syntax Error');      -- Genererà team_id = 3

-- Assegniamo Mario e Luca al Team 1 ("I Lupi di Spring")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('mario_rossi', 'mario@example.com', 'password123', 'UTENTE', 1);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('luca_bianchi', 'luca@example.com', 'password456', 'UTENTE', 1);

-- Staff (Nessun team assegnato)
INSERT INTO utente (username, email, password, ruolo)
VALUES ('giudice_master', 'giudice@hackhub.it', 'securePass789', 'GIUDICE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('mentore_esperto', 'mentore@hackhub.it', 'mentor2026', 'MENTORE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('admin_org', 'org@hackhub.it', 'adminPower!', 'ORGANIZZATORE');


-- Assegniamo Anna e Marco al Team 2 ("Code Breakers")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('anna_verdi', 'anna@example.com', 'pass789', 'UTENTE', 2);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('marco_neri', 'marco@example.com', 'pass012', 'UTENTE', 2);

-- Assegniamo Giulia e Paolo al Team 3 ("Syntax Error")
INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('giulia_gialli', 'giulia@example.com', 'pass345', 'UTENTE', 3);

INSERT INTO utente (username, email, password, ruolo, team_id)
VALUES ('paolo_blu', 'paolo@example.com', 'pass678', 'UTENTE', 3);



INSERT INTO utente (username, email, password, ruolo)
VALUES ('elena_fabbri', 'elena@example.com', 'pass999', 'UTENTE');

INSERT INTO utente (username, email, password, ruolo)
VALUES ('simone_conti', 'simone@example.com', 'pass888', 'UTENTE');