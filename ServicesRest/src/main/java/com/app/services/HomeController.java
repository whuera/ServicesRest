package com.app.services;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.modelo.Contacto;
import com.app.service.impl.ServiceContact;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 *
	 * @param locale the locale
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * Obtener persona.
	 *
	 * @param id the id
	 * @return the persona
	 */
	@RequestMapping(value="/persona",method = RequestMethod.GET)
	public @ResponseBody Persona obtenerPersona(@RequestParam(value="id",required=true) int id)
	{
		Persona persona = new Persona();
		if (id == 1){

		persona.setNombres("William");
		persona.setApellidos("Huera");
		persona.setEdad(20);
		logger.info("generate json object.", persona.toString());
		}else if(id == 2) {
			persona.setNombres("eduardo");
			persona.setApellidos("Huera");
			persona.setEdad(20);
			logger.info("generate json object.", persona.toString());
		}
		return persona;
		
		
	}
	
	/**
	 * Gets the all contacts.
	 *
	 * @return the all contacts
	 */
	@RequestMapping(value="/allContacts",method = RequestMethod.GET)
	public @ResponseBody ArrayList<Contacto> getAllContacts()
	{		
		ServiceContact contact = new ServiceContact();
		logger.info("generate json object contact.", contact.toString());
				return contact.getInformationAllPersons();			
	}
	
}