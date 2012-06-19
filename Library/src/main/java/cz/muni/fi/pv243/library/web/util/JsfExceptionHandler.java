package cz.muni.fi.pv243.library.web.util;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private Logger log = Logger.getLogger(this.getClass());

	final private ExceptionHandler wrapped;

	public JsfExceptionHandler(final ExceptionHandler wrapped) {
		super();
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> iterator = this.getUnhandledExceptionQueuedEvents().iterator();
		while (iterator.hasNext()) {
			final ExceptionQueuedEvent exceptionQueuedEvent = iterator.next();
			final Throwable exception = exceptionQueuedEvent.getContext()
					.getException();
			this.log.error("Exception caught: ", exception);
			if (!(exception instanceof FacesException)) {
				String showError = "showErrorOutcome";
				FacesContext facesContext = FacesContext.getCurrentInstance();
				NavigationHandler navigationHandler = facesContext
						.getApplication().getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null,
						showError);
				iterator.remove();
			}
			this.getWrapped().handle();
		}
	}

}
