package com.rts.tap.daoimplementation;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Repository;

import com.rts.tap.dao.BudgetDao;
import com.rts.tap.model.Budget;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class BudgetDaoImpl implements BudgetDao {
	private EntityManager entityManager;
	
	  private static final Logger logger = LoggerFactory.getLogger(BudgetDaoImpl.class); 

	public BudgetDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Budget save(Budget budget) {
		entityManager.persist(budget);
		return budget;
	}

	@Override
	public Budget findById(Long id) {
		return entityManager.find(Budget.class, id);
	}

	@Override
	public Budget update(Budget budget) {
		return entityManager.merge(budget);
	}

	@Override
	public List<Budget> findAll() {
		return entityManager.createQuery("SELECT c FROM Budget c", Budget.class).getResultList();
	}

	@Override
	public void delete(Long id) {
		Budget budget = entityManager.find(Budget.class, id);
		if (budget != null) {
			entityManager.remove(budget);
		}
	}

	@Override
	public Budget findBySubRequirementId(Long id) {
		return entityManager
				.createQuery("SELECT b FROM Budget b WHERE b.subRequirementId.subRequirementId = :id", Budget.class)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	public List<Budget> findByRequirementId(Long requirementId) {
		return entityManager
				.createQuery("SELECT b FROM Budget b WHERE b.requirement.requirementId = :requirementId", Budget.class)
				.setParameter("requirementId", requirementId).getResultList();

	}

	@Override
	public boolean updateNegotiableByRequirementId(Long requirementId, boolean isNegotiable) {
		String jpql = "UPDATE Budget b SET b.isNegotiable = :isNegotiable WHERE b.requirement.id = :requirementId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("isNegotiable", isNegotiable);
		query.setParameter("requirementId", requirementId);

		int updatedCount = query.executeUpdate();
		
		
	logger.info("Number of budgets updated: {}", updatedCount); // Replace System.out with logger
		return isNegotiable;
	}
}