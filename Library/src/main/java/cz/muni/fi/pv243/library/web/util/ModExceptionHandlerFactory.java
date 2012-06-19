package cz.muni.fi.pv243.library.web.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ModExceptionHandlerFactory extends
		javax.faces.context.ExceptionHandlerFactory {

	private final ExceptionHandlerFactory parent;

	public ModExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandler(this.parent.getExceptionHandler());
	}

}
