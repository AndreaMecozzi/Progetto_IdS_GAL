-- Inserimento UTENTE 1 (Partecipante)
INSERT INTO utente (username, email, password, ruolo) 
VALUES ('mario_rossi', 'mario@example.com', 'password123', 'UTENTE');

-- Inserimento UTENTE 2 (Partecipante)
INSERT INTO utente (username, email, password, ruolo) 
VALUES ('luca_bianchi', 'luca@example.com', 'password456', 'UTENTE');

-- Inserimento GIUDICE
INSERT INTO utente (username, email, password, ruolo) 
VALUES ('giudice_master', 'giudice@hackhub.it', 'securePass789', 'GIUDICE');

-- Inserimento MENTORE
INSERT INTO utente (username, email, password, ruolo) 
VALUES ('mentore_esperto', 'mentore@hackhub.it', 'mentor2026', 'MENTORE');

-- Inserimento ORGANIZZATORE
INSERT INTO utente (username, email, password, ruolo) 
VALUES ('admin_org', 'org@hackhub.it', 'adminPower!', 'ORGANIZZATORE');