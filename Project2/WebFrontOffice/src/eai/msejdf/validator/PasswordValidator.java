package eai.msejdf.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import eai.msejdf.utils.StringUtils;

/**
 * The Class PasswordValidator.
 * Used to validate the confirmation passowrd in forms that suport teh change or creation of a password
 */
public class PasswordValidator implements Validator
{

	private static final String CONFIRM_ATTRIBUTE = "confirm";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException
	{
		
        String originalPass = (String) value;

        // Obtain the component and submitted value of the confirm password component.
        UIInput confirmComponent = (UIInput) component.getAttributes().get(CONFIRM_ATTRIBUTE);
        
        String confirmPass = "";
        if (null != confirmComponent && null != confirmComponent.getSubmittedValue())				// if not it will fail in the next case
        {
        	confirmPass = (String) confirmComponent.getSubmittedValue();
        }
        
        // Check if they both are filled in.
        if (StringUtils.isNullOrEmpty(originalPass) || StringUtils.isNullOrEmpty(confirmPass))
        {
            return; // the required should work
        }

        // Compare the password with the confirm password.
        if (!originalPass.equals(confirmPass)) {
            confirmComponent.setValid(false); // So that it's marked invalid.
            throw new ValidatorException(new FacesMessage("Passwords are not equal."));
        }
		
	}

}
