package com.rts.tap.serviceimplementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rts.tap.dao.BudgetDao;
import com.rts.tap.exception.AddBudgetException;
import com.rts.tap.exception.BudgetNotFoundException;
import com.rts.tap.exception.DeleteBudgetException;
import com.rts.tap.exception.UpdateBudgetException;
import com.rts.tap.model.Budget;
import com.rts.tap.service.BudgetService;

@Service
public class BudgetServiceImpl implements BudgetService {
	private final BudgetDao budgetDao;
	private static final Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);

	public BudgetServiceImpl(BudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}

	/**
	 * Adds a new budget to the system.
	 *
	 * @param budget the budget object to be added
	 * @return the saved budget
	 */
	@Override
	public Budget addBudget(Budget budget) {
		budget.setNegotiable(false);
		logger.info("Adding budget: {}", budget);
		Budget savebudget= budgetDao.save(budget);
		if(savebudget == null) {
			throw new AddBudgetException("Failed to save budget");
		}
		return savebudget;
	}

	/**
	 * Retrieves all budgets from the system.
	 *
	 * @return a list of all budgets
	 */
	@Override
	public List<Budget> getAllBudgets() {
		logger.info("Fetching all budgets");
		List<Budget> budgetAll= budgetDao.findAll();
		if (budgetAll==null || budgetAll.isEmpty()) {
			throw new BudgetNotFoundException("No budget data are found");
		}
		return budgetAll;
	}

	/**
	 * Retrieves a budget by its ID.
	 *
	 * @param id the ID of the budget to fetch
	 * @return the budget object, or null if not found
	 */
	@Override
	public Budget getBudgetById(Long id) {
		logger.info("Fetching budget by ID: {}", id);
		Budget budgetId= budgetDao.findById(id);
		if (budgetId==null) {
			throw new BudgetNotFoundException("No such ID exist");
		}
		return budgetId;
	}

	/**
	 * Retrieves a budget by its associated sub-requirement ID.
	 *
	 * @param id the sub-requirement ID
	 * @return the associated budget object, or null if not found
	 */
	@Override
	public Budget getBudgetBySubRequirementId(Long id) {
		logger.info("Fetching budget by sub-requirement ID: {}", id);
		Budget subrequimentId =budgetDao.findBySubRequirementId(id);
		if (subrequimentId==null) {
			throw new BudgetNotFoundException("No budget details are found for the subrequirement Id:"+id);
		}
		return subrequimentId;
	}

	/**
	 * Deletes a budget by its ID.
	 *
	 * @param id the ID of the budget to delete
	 */
	@Override
	public void deleteBudget(Long id) {
		logger.info("Deleting budget with ID: {}", id);
		 if (id == null) {
		        throw new DeleteBudgetException("Budget ID cannot be null.");
		    }
		    budgetDao.delete(id);
        }

	/**
	 * Updates an existing budget.
	 *
	 * @param budget the budget object with updated information
	 * @return the updated budget
	 */
	@Override
	public Budget updateBudget(Budget budget) {
		logger.info("Updating budget: {}", budget);
		Budget updatebudget= budgetDao.update(budget);
		if (updatebudget == null) {
	        throw new UpdateBudgetException("Failed to update budget");
	}
		return updatebudget;
	}		

	/**
	 * Retrieves a list of budgets associated with a specific requirement ID.
	 *
	 * @param requirementId the requirement ID
	 * @return a list of associated budgets
	 */
	@Override
	public List<Budget> getBudgetByRequirementId(Long requirementId) {
		logger.info("Fetching budgets by requirement ID: {}", requirementId);
		List<Budget> budgetrequirementID= budgetDao.findByRequirementId(requirementId);
		if (budgetrequirementID==null || budgetrequirementID.isEmpty()) {
			throw new BudgetNotFoundException("No budget details are found the requiment id:"+requirementId);
		}
		return budgetrequirementID;
	}

	/**
	 * Updates the negotiable status for budgets associated with a specific
	 * requirement ID.
	 *
	 * @param requirementId the requirement ID
	 * @param isNegotiable  the new negotiable status
	 * @return true if the update was successful, false otherwise
	 */
	@Override
	public boolean updateNegotiableByRequirementId(Long requirementId, boolean isNegotiable) {
		logger.info("Updating negotiable status for requirement ID: {} to {}", requirementId, isNegotiable);
		boolean negotiateId= budgetDao.updateNegotiableByRequirementId(requirementId, isNegotiable);
		if (!negotiateId) {
			throw new UpdateBudgetException("Cannot be negotiated for the requirement id:"+requirementId);
		}
		return negotiateId;
	}
}