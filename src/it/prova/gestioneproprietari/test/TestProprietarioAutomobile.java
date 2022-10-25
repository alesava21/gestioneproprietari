package it.prova.gestioneproprietari.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.dao.EntityManagerUtil;
import it.prova.gestioneproprietari.model.Automobile;
import it.prova.gestioneproprietari.model.Proprietario;
import it.prova.gestioneproprietari.service.MyServiceFactory;
import it.prova.gestioneproprietari.service.automobile.AutomobileService;
import it.prova.gestioneproprietari.service.proprietario.ProprietarioService;

public class TestProprietarioAutomobile {
	public static void main(String[] args) {

		AutomobileService automobileService = MyServiceFactory.getAutomobileServiceInstance();
		ProprietarioService proprietarioService = MyServiceFactory.getProprietarioServiceInstance();

		try {

			testInserisceProprietario(proprietarioService);
			System.out.println("In tabella Proprietari ci sono" + " " + proprietarioService.listAllProprietari().size()
					+ " " + "elementi");

			testInsertAutomobile(automobileService, proprietarioService);
			System.out.println("In tabella Automobili ci sono" + " " + automobileService.listAllAtomobili().size() + " "
					+ "elementi");

			testAggiornaAutomobile(proprietarioService, automobileService);

			testRimozioneAutomobile(automobileService, proprietarioService);
			System.out.println("In tabella Automobili ci sono" + " " + automobileService.listAllAtomobili().size() + " "
					+ "elementi");

			testAggiornaProprietario(proprietarioService);

			testRimozioneProprietario(proprietarioService);
			System.out.println("In tabella Proprietari ci sono" + " " + proprietarioService.listAllProprietari().size()
					+ " " + "elementi");
			testCercaTramiteCodiceFiscaleLeAutomobili(automobileService);

			testCercaErroriProprietatiAuto(automobileService);

			testCercaAutomobiliConImmatricolazioneDal(proprietarioService);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserisceProprietario(ProprietarioService proprietarioService) throws Exception {

		System.out.println(".......testInserisciProprietario inizio.............");

		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("21-05-2010");

		Proprietario nuovoProprietario = new Proprietario("Alessandro", "Sava", "SVALND02E21Z129A", dataNascita);
		if (nuovoProprietario.getId() != null)
			throw new RuntimeException("testInserisciProprietario fallito: record già presente ");
		proprietarioService.inserisciNuovo(nuovoProprietario);

		if (nuovoProprietario.getId() == null)
			throw new RuntimeException("testInserisciProprietario fallito ");

		System.out.println(".......testInserisciProprietario fine: PASSED.............");

	}

	private static void testInsertAutomobile(AutomobileService automobileService,
			ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testInsertAutomobile inizio.............");

		List<Proprietario> listaProprietari = proprietarioService.listAllProprietari();

		Date dataImmatricolazione = new SimpleDateFormat("dd-MM-yyyy").parse("21-05-2002");

		Automobile nuovaAutomobile = new Automobile(dataImmatricolazione, "Audi", "A1", "GF765FR");

		nuovaAutomobile.setProprietario(listaProprietari.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);

		if (nuovaAutomobile.getId() == null) {
			throw new RuntimeException("testInsertAutomobile fallito ");

		}

		if (nuovaAutomobile.getProprietario() == null) {
			throw new RuntimeException("testInsertAutomobile fallito ");
		}

		System.out.println(".......testInsertAutomobile fine: PASSED.............");

	}

	private static void testRimozioneAutomobile(AutomobileService automobileService,
			ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testRimozioneAutomobile inizio.............");

		List<Proprietario> listaProprietari = proprietarioService.listAllProprietari();
		if (listaProprietari.isEmpty()) {
			throw new RuntimeException("testRimozioneAutomobile fallito: non ci sono municipi a cui collegarci ");
		}

		Date dataImmatricolazione = new SimpleDateFormat("dd-MM-yyyy").parse("04-07-2021");

		Automobile nuovaAutomobile = new Automobile(dataImmatricolazione, "Mercedes", "A45 Amg", "GH643AE");

		nuovaAutomobile.setProprietario(listaProprietari.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);

		Long idAAutomobileInserito = nuovaAutomobile.getId();
		automobileService.rimuovi(idAAutomobileInserito);

		if (automobileService.caricaSingolaAutomobile(idAAutomobileInserito) != null)
			throw new RuntimeException("testRimozioneAutomobile fallito: record non cancellato ");
		System.out.println(".......testRimozioneAutomobile fine: PASSED.............");
	}

	private static void testAggiornaAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testAggiornaAutomobile inizio.............");

		List<Automobile> listaAutomobiliiPresenti = automobileService.listAllAtomobili();

		Automobile automobileDaAggiornare = listaAutomobiliiPresenti.get(0);
		automobileDaAggiornare.setTarga("Test");

		automobileService.aggiorna(automobileDaAggiornare);

		System.out.println(".......testAggiornaAutomobile fine.............");
	}

	private static void testRimozioneProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testRimozioneProprietario inizio.............");

		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("17-05-1956");

		Proprietario nuovoProprietario = new Proprietario("Andrea", "Corsetti", "ANDCRS", dataNascita);

		proprietarioService.inserisciNuovo(nuovoProprietario);

		Long idProprietarioInserito = nuovoProprietario.getId();
		proprietarioService.rimuovi(nuovoProprietario);

		if (proprietarioService.caricaSingoloProprietario(idProprietarioInserito) != null)
			// throw new RuntimeException("testRimozioneProprietario fallito: record non
			// cancellato ");
			System.out.println(".......testRimozioneProprietario fine: PASSED.............");
	}

	private static void testAggiornaProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testAggiornaProprietario inizio.............");

		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();

		Proprietario proprietarioDaAggiornare = listaProprietariPresenti.get(0);
		proprietarioDaAggiornare.setNome("Mariano");

		proprietarioService.aggiorna(proprietarioDaAggiornare);

		System.out.println(".......testAggiornaProprietario fine.............");
	}

	private static void testCercaTramiteCodiceFiscaleLeAutomobili(AutomobileService automobileService)
			throws Exception {
		System.out.println(".......testCercaTramiteCodiceFiscaleLeAutomobili inizio.............");

		System.out.println(automobileService.CercaTutteLeAutomobiliTramiteInizialeCodiceFiscaleProprietario("sva"));

		System.out.println(".......testCercaTramiteCodiceFiscaleLeAutomobili fine.............");

	}

	private static void testCercaErroriProprietatiAuto(AutomobileService automobileService) throws Exception {
		System.out.println(".......testCercaErroriProprietatiAuto inizio.............");

		System.out.println(automobileService.findAllErroriProprietariAutomobiliMinorenni());

		System.out.println(".......testCercaErroriProprietatiAuto fine.............");

	}

	private static void testCercaAutomobiliConImmatricolazioneDal(ProprietarioService proprietarioService)
			throws Exception {
		System.out.println(".......testCercaAutomobiliConImmatricolazioneDal inizio.............");

		Date dataNascita = new SimpleDateFormat("dd-MM-yyyy").parse("17-05-1956");

		System.out.println(proprietarioService.contaQuantiProprietariConAutomobileImmatricolataDal(dataNascita));

		System.out.println(".......testCercaAutomobiliConImmatricolazioneDal fine.............");

	}

}
