package it.prova.gestioneproprietari.dao;

import it.prova.gestioneproprietari.dao.automobile.AutomobileDAOImpl;
import it.prova.gestioneproprietari.dao.automobile.AutomobileDAO;
import it.prova.gestioneproprietari.dao.proprietario.ProprietarioDAO;
import it.prova.gestioneproprietari.dao.proprietario.ProprietarioDAOImpl;
import net.bytebuddy.asm.Advice.Return;

public class MyDaoFactory {

	// rendiamo questo factory SINGLETON
	private static AutomobileDAO automobileDaoInstance = null;
	private static ProprietarioDAO proprietarioDAOInstance = null;

	public static AutomobileDAO getAutomobileDAOInstance() {
		if (automobileDaoInstance == null)
			automobileDaoInstance = new AutomobileDAOImpl();
		return automobileDaoInstance;

	}

	public static ProprietarioDAO getProprietarioDAOInstance() {
		if (proprietarioDAOInstance == null)
			proprietarioDAOInstance = new ProprietarioDAOImpl();
		return proprietarioDAOInstance;
	}

}
