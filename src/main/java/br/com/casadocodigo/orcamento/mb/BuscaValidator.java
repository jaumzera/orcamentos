package br.com.casadocodigo.orcamento.mb;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jaumzera
 */
@FacesValidator("buscaValidator")
public class BuscaValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String busca = ((String) value).replaceAll("[%'\"]", "");
        if(busca.length() < 3) {
            throw new ValidatorException(
                    new FacesMessage("Informe pelo menos 3 caracteres"));
        }
    }
}
