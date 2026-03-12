package unicam.ids2526.gal.progetto_hackhub_gal.core;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// mewttendo autoApply = true lo applica a tutti i campi StatoHackathon automaticamente
@Converter(autoApply = true)
public class StatoHackathonConverter implements AttributeConverter<StatoHackathon, String> {

    @Override
    public String convertToDatabaseColumn(StatoHackathon stato) {
        if (stato == null) {
            return null;
        }
        return stato.getNomeStato();
    }

    @Override
    public StatoHackathon convertToEntityAttribute(String caso) {
        if (caso == null) {
            return null;
        }


        return switch (caso) {
            case "IN_ISCRIZIONE" -> new InIscrizione();
            case "IN_CORSO" -> new InCorso();
            case "IN_VALUTAZIONE" -> new InValutazione();
            case "CONCLUSO" -> new Concluso();
            default -> throw new IllegalArgumentException("Stato non riconosciuto: " + caso);
        };
    }
}
