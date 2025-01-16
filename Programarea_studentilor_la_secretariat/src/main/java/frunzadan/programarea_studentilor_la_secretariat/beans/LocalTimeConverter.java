import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import java.time.LocalTime;

@FacesConverter("localTimeConverter")
public class LocalTimeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return LocalTime.parse(value); // Convertim String în LocalTime
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof LocalTime) {
            return ((LocalTime) value).toString(); // Convertim LocalTime în String
        }
        return "";
    }
}
