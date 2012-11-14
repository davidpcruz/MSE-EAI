package eai.msejdf.timer;

import java.util.List;
import java.util.ArrayList;

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
@Stateless
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
	 * (non-Javadoc)
	 * 
	 * @see eai.msejdf.timer.IDBCleanerRemote#cleanBankTeller()
	 */

	@Schedule(hour = "*", minute = "*/15")
	public void cleanBankTeller() {
		if (logger.isDebugEnabled()) {
			logger.debug("cleanBankTeller() - start"); //$NON-NLS-1$
		}

		List<Long> notUsedBankTellersId = new ArrayList<Long>();
		// Get a list of all Bank Tellers in DB
		Query queryGetBankTellersId = entityManager.createQuery("SELECT bankTeller.id FROM  BankTeller AS bankTeller");
		@SuppressWarnings("unchecked")
		List<Long> completBankTellerListId = queryGetBankTellersId.getResultList();
		// Get a list of the used Bank Tellers
		Query queryGetUsedBankTellersId = entityManager.createQuery("SELECT user.bankTeller.id FROM  User AS user");
		@SuppressWarnings("unchecked")
		List<Long> usedBankTellerListId = queryGetUsedBankTellersId.getResultList();

		
		Query queryUnusedGetUsedBankTellersId = entityManager.createQuery("SELECT user.bankTeller.id FROM  User AS user");
		@SuppressWarnings("unchecked")
		List<Long> unusedGetUsedBankTellersId = queryUnusedGetUsedBankTellersId.getResultList();
		logger.info("test query unused:" + unusedGetUsedBankTellersId);
		
		// Get the list of unused tBankTellerListId
		for (Long bankteler : completBankTellerListId) {
			if (!usedBankTellerListId.contains(bankteler)) {
				notUsedBankTellersId.add(bankteler);
			}
		}

		// If there are unused Bankteller so they should be deleted
		if (0 < notUsedBankTellersId.size()) {
			logger.info("Deleting  Bank Teller with ID:" + notUsedBankTellersId);
			Query deleteQuery = entityManager.createQuery("delete from BankTeller where id in (:notUsedBankTellers) ");
			deleteQuery.setParameter("notUsedBankTellers", notUsedBankTellersId);
			deleteQuery.executeUpdate();
		} else {
			logger.info("cleanBankTeller speaking: No  Bank Teller are being unused.\n I'll skip for now but I will be back");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("cleanBankTeller() - end"); //$NON-NLS-1$
		}
	}

}
