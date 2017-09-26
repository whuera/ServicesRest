package com.app.services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.modelo.AddressContact;
import com.app.modelo.Contacto;
import com.app.modelo.Entidades;
import com.app.modelo.InformacionFinanciera;
import com.app.service.impl.ServiceAddressContact;
import com.app.service.impl.ServiceContact;
import com.app.service.impl.ServiceEntidades;
import com.app.service.impl.ServiceProducts;



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
	
	/**
	 * Gets the all contact by id.
	 *
	 * @param idContact the id contact
	 * @return the all contact by id
	 */
	@RequestMapping(value="/allContactById",method = RequestMethod.GET)
	public @ResponseBody ArrayList<Contacto> getAllContactById(@RequestParam(value="id",required=true) String idContact)
	{		
		ServiceContact contact = new ServiceContact();
		logger.info("generate json object contact.", contact.toString());
				return contact.getContactById(idContact.trim());			
	}
	
	
	/**
	 * Gets the all contact address by id.
	 *
	 * @param idContact the id contact
	 * @return the all contact address by id
	 */
	@RequestMapping(value="/allContactAddressById",method = RequestMethod.GET)
	public @ResponseBody ArrayList<AddressContact> getAllContactAddressById(@RequestParam(value="id",required=true) int idContact)
	{		
		ServiceAddressContact contactAddress = new ServiceAddressContact();
		logger.info("generate json object contact.", contactAddress.toString());
				return contactAddress.getAddressByIdContact(idContact);			
	}
	
	
	/**
	 * Save contact.
	 *
	 * @param entidades the entidades
	 * @return true, if successful
	 */
	
	@RequestMapping(value="/saveContact", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Entidades> saveContact (@RequestBody Entidades entidades)
	{		
		ServiceEntidades entidadesServiceNew = new ServiceEntidades();	
		Entidades entidadesReturn = new Entidades();
		logger.info("generate json object contact.", entidades.toString());
		String idContact = null;
		try {
			idContact = entidadesServiceNew.saveEntidad(entidades);
		} catch (SQLException e) {
			logger.error(e.getMessage());		
		}
		logger.info("id : "+idContact);
		entidadesReturn = entidadesServiceNew.getEntidad(idContact);
		logger.info("contacto creado :"+entidadesReturn.toString());
				return new ResponseEntity<Entidades>(entidadesReturn,HttpStatus.CREATED);					
	}
	
	
	/**
	 * Gets the credentials.
	 *
	 * @param entidades the entidades
	 * @return the credentials
	 */
	@RequestMapping(value="/getCredentials",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody int getCredentials(@RequestBody Entidades entidades)
	{	int valRetur = 0;
		ServiceEntidades serviceEntidades = new ServiceEntidades();		
		valRetur = 	serviceEntidades.getCredentinals(entidades.getLogin(),entidades.getPassword());
		System.out.println("valRetur :: "+valRetur);
		logger.info("generate json object valRetur. ", valRetur);
		return valRetur;
									
	}
	
	/**
	 * Gets the credentials by object.
	 *
	 * @param entidades the entidades
	 * @return the credentials by object
	 */
	@RequestMapping(value="/getCredentialsByObject",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Entidades getCredentialsByObject(@RequestBody Entidades entidades)
	{	
		ServiceEntidades serviceEntidades = new ServiceEntidades();
		Entidades entidadesObject = new Entidades();
		entidadesObject = 	serviceEntidades.getCredentinalsByObject(entidades.getLogin(),entidades.getPassword());
		System.out.println("entidadesObject :: "+entidadesObject.toString());
		logger.info("generate json object entidadesObject. ", entidadesObject.toString());
		return entidadesObject;
									
	}
	
	
	/**
	 * Gets the products finance by id.
	 *
	 * @param idContact the id contact
	 * @return the products finance by id
	 */
	@RequestMapping(value="/getProductsFinanceById",method = RequestMethod.GET)
	public @ResponseBody ArrayList<InformacionFinanciera> getProductsFinanceById(@RequestParam(value="id",required=true) String idContact)
	{		
		ServiceProducts serviceProducts = new ServiceProducts();
		logger.info("generate json object products.");
				return serviceProducts.getProducts(idContact);			
	}
	
	
	
}
