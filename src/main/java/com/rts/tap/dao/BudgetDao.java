package com.rts.tap.dao;

import java.util.List;

import com.rts.tap.model.Budget;

public interface BudgetDao {
	Budget save(Budget budget);

	Budget findById(Long id);

	Budget findBySubRequirementId(Long id);

	List<Budget> findByRequirementId(Long requirementId);

	Budget update(Budget budget);

	List<Budget> findAll();

	void delete(Long id);

	boolean updateNegotiableByRequirementId(Long requirementId, boolean isNegotiable);
}