package eai.msejdf.web;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class SessionPhaseListener implements PhaseListener
{
	private static final long serialVersionUID = 1L;
	private static final String SESSION_TIMED_OUT = "SessionTimedOut";
	private static final String LOGIN_PAGE = "Login.xhtml";
	private static final String REGISTRATION_PAGE = "RegisterUser.xhtml";

	@Override
	public void afterPhase(PhaseEvent event) 
	{
		FacesContext facesContext = event.getFacesContext();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		boolean isLoginPage = (facesContext.getViewRoot().getViewId().lastIndexOf(SessionPhaseListener.LOGIN_PAGE) > -1);
		boolean isRegistrationPage = (facesContext.getViewRoot().getViewId().lastIndexOf(SessionPhaseListener.REGISTRATION_PAGE) > -1);

		if (null == session)
		{
			if (false != isRegistrationPage)
			{
				return;
			}
			// Session does not exist (yet or has expired)
			NavigationHandler navHandler = facesContext.getApplication().getNavigationHandler();
			navHandler.handleNavigation(facesContext, null, SessionPhaseListener.SESSION_TIMED_OUT);
			return;
		}
		// Session is active
		String activeUser = (String)session.getAttribute(SessionManager.USERNAME_PROPERTY);

		if ((false == isLoginPage) && 
			(false == isRegistrationPage) && 
			((null == activeUser) || ("" == activeUser))) 
		{
			NavigationHandler navHandler = facesContext.getApplication().getNavigationHandler();
			navHandler.handleNavigation(facesContext, null, SessionPhaseListener.SESSION_TIMED_OUT);
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
