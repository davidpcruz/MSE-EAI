package eai.msejdf.web.backoffice.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import eai.msejdf.persistence.Company;

/**
 * The Class CompanySelectedItemConverter.
 * Used for converting companies into Faces UI and viceversa
 */
@ManagedBean(name="companySelectedConverter")
public class CompanySelectedItemConverter implements Converter {
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Company) value).getId().toString();
    }

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
	{
		Company toReturn = new Company();
		toReturn.setId(Long.valueOf(value));
		
		return toReturn;
	}
}
