package unicam.ids2526.gal.progetto_hackhub_gal;

/**
 * Rappresenta una valutazione emessa da un giudice per una determinata sottomissione.
 * La valutazione comprende un voto (intero da 1 a 30) ed una breve descrizione
 */
public record Valutazione (int voto, String descrizione){}
