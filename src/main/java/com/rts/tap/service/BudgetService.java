package com.rts.tap.service;

import java.util.List;

import com.rts.tap.model.Budget;

public interface BudgetService {

	Budget addBudget(Budget budget);

	List<Budget> getAllBudgets();

	Budget getBudgetById(Long id);

	Budget getBudgetBySubRequirementId(Long id);

	List<Budget> getBudgetByRequirementId(Long requirementId);

	Budget updateBudget(Budget budget);

	void deleteBudget(Long id);

	boolean updateNegotiableByRequirementId(Long requirementId, boolean isNegotiable);
}