# Hackhub

**HackHub** è una piattaforma web per la gestione di hackathon sviluppata in **Java** con **Spring Boot**. Gli **hackathon** sono eventi di gruppo ai quali possono partecipare dei **team**. Ogni hackathon segue un ciclo di vita con quattro stati: in iscrizione, in corso, in valutazione e concluso. La piattaforma supporta l'organizzazione degli hackathon, la registrazione dei team, il caricamento delle sottomissioni e la valutazione da parte dei giudici per queste ultime.


## Come è organizzato il codice

Tutto il codice sorgente principale si trova sotto `unicam.ids2526.gal.progetto_hackhub_gal`. È stato suddiviso in layer ben distinti per separare le responsabilità:

### Il cuore del sistema: `core` (Domain Layer)
Questo è il centro dell'applicazione. Qui dentro c'è solo logica di business pura e le entità di dominio, senza dipendenze da database o framework esterni. È organizzato in vari sotto-domini:
*   **Hackathon**: gestisce l'entità principale dell'evento. Per gestire i vari stati in cui si può trovare un hackathon in modo sicuro e pulito, è stato implementato lo **State Pattern** (tramite `StatoHackathon` e classi come `InIscrizione`, `InCorso`, ecc.).
*   **Utenti e Team**: gestisce l'entità `Utente` e `Team`. Per la creazione degli utenti è stato utilizzato il **Builder Pattern** in modo da avere una costruzione degli oggetti più flessibile e validata.
*   **Inviti, Sottomissioni e Valutazioni**: gestiscono rispettivamente gli inviti per entrare nei team, l'invio dei progetti e le valutazioni dei giudici.
*   **Segnalazioni e Supporto**: entità dedicate alla gestione dei ticket di assistenza o di problemi.

### `application` (Application Layer)
Questo livello fa da tramite tra l'interfaccia esposta all'esterno e il dominio.
*   **Handlers**: contiene i casi d'uso veri e propri (es. `HackathonHandler`, `TeamHandler`), implementati utilizzando il pattern **Singleton**. Queste classi orchestrano le operazioni, interagendo con i repository e applicando le regole definite nel `core`.
*   **DTO**: contiene i Data Transfer Object, ovvero strutture dati semplici utilizzate esclusivamente per scambiare informazioni in ingresso e in uscita tra i vari layer, mantenendo così le entità di dominio "pure" e isolate dall'esterno.

### `infrastructure` e `presentation`
*   **Infrastructure**: si occupa dell'accesso ai dati e delle tecnologie esterne. Qui si trovano i Repository (che estendono `JpaRepository` di Spring Data) e task schedulati come l'`HackathonScheduler` (che torna utile per far avanzare in automatico gli stati degli hackathon in base alle date).
*   **Presentation**: è il livello più esterno. Contiene i vari REST Controller (`HackathonController`, ecc.) che espongono le API. Il loro unico compito è ricevere le richieste HTTP, fare qualche controllo di base e delegare il lavoro agli handler, oltre che restituire le risposte HTTP.

### `security`
Tutta la gestione dell'autenticazione e autorizzazione è stata isolata all'interno del package `security`. Si utilizza **Spring Security** e i **JWT (JSON Web Tokens)**. Sono presenti le configurazioni di sicurezza e i filtri (`JwtAuthenticationFilter`) che controllano la validità dei token ad ogni chiamata API.

## Pattern e Tecnologie utilizzate
Le fondamenta tecniche del progetto sono:
*   **Java** e **Spring Boot** per l'infrastruttura di base.
*   **Spring Data JPA** per dialogare col database.
*   **Pattern architetturali**: Clean Architecture e separazione in layer.
*   **Design Pattern**: State Pattern (fondamentale per la logica dell'hackathon), Builder Pattern (per la creazione degli utenti) e Singleton (per gli handler e per la gestione del JWT).

## Test API
Per il test dei metodi tramite Postman, occorre eseguire il login per il rispettivo ruolo in modo da ottenere il token JWT per l'accesso.
Questo token dovrà poi essere inserito nella sezione **Authorization -> Bearer Token** del metodo API da testare.