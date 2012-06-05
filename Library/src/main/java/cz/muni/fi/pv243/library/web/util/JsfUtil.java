package cz.muni.fi.pv243.library.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * A support class for displaying the messages.
 */
public class JsfUtil {
	
	private JsfUtil() {
	}

	public static void addSuccessMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

}
