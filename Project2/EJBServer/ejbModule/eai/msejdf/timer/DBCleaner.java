package eai.msejdf.timer;

import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Bean to Clean the Database It will delete all the BankTellers that are in DB
 * but no user are using
 * 
 * @author joaofcr
 * 
 */
@Stateless(name="DBCleaner")
public class DBCleaner {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DBCleaner.class);

	@PersistenceContext(unitName = "JPAEAI")
	// TODO: Check if it can be placed in a config file and update name
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public DBCleaner() {
	}

	/*
	 * Bean to Clean the Database It will delete all the BankTellers that are in
	 * DB but no user are using
	 */
	@Schedule(hour = "*", minute = "*/30", second = "0")
	public void cleanBankTeller() {
		if (logger.isDebugEnabled()) {
			logger.debug("cleanBankTeller() - start"); //$NON-NLS-1$
		}

		// Get a list of all Bank Tellers in DB
		Query queryGetBankTellersId = entityManager.createQuery("SELECT bankTeller.id FROM  BankTeller AS bankTeller");
		@SuppressWarnings("unchecked")
		List<Long> bankTellerListId = queryGetBankTellersId.getResultList();

		// Get a list of the used Bank Tellers
		Query queryGetUsedBankTellersId = entityManager.createQuery("SELECT user.bankTeller.id FROM  User AS user");
		@SuppressWarnings("unchecked")
		List<Long> usedBankTellerListId = queryGetUsedBankTellersId.getResultList();

		// Get the list of unused BankTellerListId
		bankTellerListId.removeAll(usedBankTellerListId);

		// If there are unused Bankteller so they should be deleted
		if (0 < bankTellerListId.size()) {
			logger.info("Deleting  Bank Teller with ID:" + bankTellerListId);
			Query deleteQuery = entityManager.createQuery("delete from BankTeller where id in (:notUsedBankTellers) ");
			deleteQuery.setParameter("notUsedBankTellers", bankTellerListId);
			deleteQuery.executeUpdate();
		} else {
			logger.info("cleanBankTeller speaking: No  Bank Teller are being unused. I'll skip for now but I will be back");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cleanBankTeller() - end"); //$NON-NLS-1$
		}
	}

}
