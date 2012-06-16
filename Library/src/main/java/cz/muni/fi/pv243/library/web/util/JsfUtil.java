package cz.muni.fi.pv243.library.web.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * A support class for displaying the messages and items hadnling.
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

	public static SelectItem[] getSelectItems(List<?> entities,
			boolean selectOne) {
		int size = selectOne ? (entities.size() + 1) : entities.size();
		SelectItem[] items = new SelectItem[size];
		int i = 0;

		if (selectOne) {
			items[0] = new SelectItem("", "---");
			i++;
		}

		for (Object x : entities) {
			items[i++] = new SelectItem(x, x.toString());
		}

		return items;
	}

}
