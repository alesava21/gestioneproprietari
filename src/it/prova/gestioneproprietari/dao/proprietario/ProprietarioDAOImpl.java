package it.prova.gestioneproprietari.dao.proprietario;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.JApplet;

import it.prova.gestioneproprietari.model.Proprietario;
import javassist.expr.NewArray;

public class ProprietarioDAOImpl implements ProprietarioDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Proprietario> list() throws Exception {
		return entityManager.createQuery("from Proprietario", Proprietario.class).getResultList();
	}

	@Override
	public Proprietario get(Long id) throws Exception {
		return entityManager.find(Proprietario.class, id);
	}

	@Override
	public void update(Proprietario proprietarioInstance) throws Exception {
		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}

		proprietarioInstance = entityManager.merge(proprietarioInstance);

	}

	@Override
	public void insert(Proprietario proprietarioInstance) throws Exception {
		// TODO Auto-generated method stub

		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(proprietarioInstance);

	}

	@Override
	public void delete(Proprietario proprietarioInstance) throws Exception {
		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(proprietarioInstance));

	}

	@Override
	public int countProprietariConAutomobileImmatricolataDal(Date annoImmatricolazione) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"select count(p) from Proprietario p join p.automobili a where a.annoImmatricolazione > ?1",
				Long.class);
		return query.setParameter(1, new java.sql.Date(annoImmatricolazione.getYear())).getSingleResult().intValue();
	}
}
