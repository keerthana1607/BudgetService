
/**
* Author: Team-B
*
* BudgetController manages budget-related operations,
* including creating, updating, retrieving, and deleting budgets.
*
* It interacts with BudgetService to perform these actions
* and includes error handling for robust responses.
*/

package com.rts.tap.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rts.tap.constants.APIConstants;
import com.rts.tap.constants.MessageConstants;
import com.rts.tap.model.Budget;
import com.rts.tap.service.BudgetService;

@CrossOrigin(APIConstants.FRONT_END_URL)
@RestController
@RequestMapping(APIConstants.BASE_URL)
public class BudgetController {

	private static final Logger logger = LoggerFactory.getLogger(BudgetController.class);
	private final BudgetService budgetService;

	public BudgetController(BudgetService budgetService) {
		this.budgetService = budgetService;
	}

	/**
	 * Adds a new budget.
	 *
	 * @param budget The budget object to be added.
	 * @return ResponseEntity containing the created Budget or an error response.
	 */
	@PostMapping(APIConstants.ADD_BUDGET_URL)
	public ResponseEntity<Budget> addBudget(@RequestBody Budget budget) {
	    logger.info("Received request to add a new budget.");
	    try {
	        Budget createdBudget = budgetService.addBudget(budget);
	        logger.info("Budget created successfully with ID: {}", createdBudget.getBudgetId());
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdBudget);
	    } catch (Exception e) {
	        logger.error("Error while creating budget: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	/**
	 * Retrieves all budgets.
	 *
	 * @return ResponseEntity containing a list of Budgets or an error response.
	 */
	@GetMapping(APIConstants.GET_ALL_BUDGET_URL)
	public ResponseEntity<List<Budget>> getAllBudgets() {
	    logger.info("Received request to fetch all budgets.");
	    try {
	        List<Budget> budgets = budgetService.getAllBudgets();
	        if (budgets != null) { 
	            logger.info("Total budgets retrieved: {}", budgets.size());
	        } else {
	            logger.info("No budgets retrieved.");
	        }
	        return ResponseEntity.ok(budgets);
	    } catch (Exception e) {
	        logger.error("Error while fetching budgets: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

	/**
	 * Retrieves a budget by its ID.
	 *
	 * @param id The ID of the budget to retrieve.
	 * @return ResponseEntity containing the Budget or a not found response.
	 */
	@GetMapping(APIConstants.GET_BUDGET_BY_ID_URL)
	public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
	    logger.info("Received request to fetch budget with ID: {}", id); // Use format specifiers
	    Budget budget = budgetService.getBudgetById(id);
	    
	    if (budget != null) {
	        logger.info("Budget retrieved successfully with ID: {}", id); // Use format specifiers
	        return ResponseEntity.ok(budget);
	    } else {
	        logger.warn("Budget with ID: {} not found.", id); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	/**
	 * Retrieves a budget by its associated sub-requirement ID.
	 *
	 * @param id The sub-requirement ID associated with the budget.
	 * @return ResponseEntity containing the Budget or a not found response.
	 */
	@GetMapping(APIConstants.GET_BUDGET_BY_SUBREQUIREMENTID_URL)
	public ResponseEntity<Budget> getBudgetBySubRequirementId(@PathVariable Long id) {
	    logger.info("Received request to fetch budget by sub-requirement ID: {}", id); // Use format specifiers
	    Budget budget = budgetService.getBudgetBySubRequirementId(id);
	    
	    if (budget != null) {
	        logger.info("Budget retrieved successfully for sub-requirement ID: {}", id); // Use format specifiers
	        return ResponseEntity.ok(budget);
	    } else {
	        logger.warn("Budget for sub-requirement ID: {} not found.", id); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
	/**
	 * Updates an existing budget identified by its ID.
	 *
	 * @param id     The ID of the budget to update.
	 * @param budget The updated budget data.
	 * @return ResponseEntity containing the updated Budget or an error response.
	 */
	@PutMapping(APIConstants.UPDATE_BUDGET_URL)
	public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @RequestBody Budget budget) {
	    logger.info("Received request to update budget with ID: {}", id); // Use format specifiers
	    try {
	        budget.setBudgetId(id); // Set the budget ID for updating
	        Budget updatedBudget = budgetService.updateBudget(budget);
	        logger.info("Budget updated successfully with ID: {}", id); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.OK).body(updatedBudget);
	    } catch (Exception e) {
	        logger.error("Error while updating budget with ID: {} - {}", id, e.getMessage()); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	/**
	 * Deletes a budget identified by its ID.
	 *
	 * @param id The ID of the budget to delete.
	 * @return ResponseEntity containing a success message or an error response.
	 */
	@DeleteMapping(APIConstants.DELETE_BUDGET_URL)
	public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
	    logger.info("Received request to delete budget with ID: {}", id); // Use format specifiers
	    try {
	        budgetService.deleteBudget(id);
	        logger.info("Budget deleted successfully with ID: {}", id); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(MessageConstants.SUCCESS_MESSAGE);
	    } catch (Exception e) {
	        logger.error("Error while deleting budget with ID: {} - {}", id, e.getMessage()); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageConstants.FAILURE_MESSAGE);
	    }
	}

	/**
	 * Retrieves budgets associated with a specific requirement ID.
	 *
	 * @param requirementId The requirement ID for which to fetch budgets.
	 * @return ResponseEntity containing a list of Budgets or a not found response.
	 */
	@GetMapping(APIConstants.GET_BUDGET_BY_REQUIREMENTID_URL)
	public ResponseEntity<List<Budget>> getBudgetByRequirementId(@PathVariable Long requirementId) {
	    logger.info("Received request to fetch budgets for requirement ID: {}", requirementId); // Use format specifiers
	    List<Budget> budget = budgetService.getBudgetByRequirementId(requirementId);
	    if (budget != null && !budget.isEmpty()) {
	        logger.info("Budgets retrieved successfully for requirement ID: {}", requirementId); // Use format specifiers
	        return ResponseEntity.ok(budget);
	    } else {
	        logger.warn("No budgets found for requirement ID: {}", requirementId); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}

	/**
	 * Updates the negotiable field based on requirement ID.
	 *
	 * @param requirementId The requirement ID to update.
	 * @param isNegotiable  The negotiable status.
	 * @return ResponseEntity containing the status of the negotiation update.
	 */
	@PatchMapping(APIConstants.UPDATENEGOTIATEDFIELD)
	public ResponseEntity<Boolean> updateNegotiableByRequirementId(@PathVariable Long requirementId,
	        @RequestParam boolean isNegotiable) {
	    logger.info("Received request to update negotiable status for requirement ID: {}", requirementId); // Use format specifiers
	    try {
	        boolean negotiation = budgetService.updateNegotiableByRequirementId(requirementId, isNegotiable);
	        logger.info("Negotiable status updated successfully for requirement ID: {}", requirementId); // Use format specifiers
	        return ResponseEntity.ok(negotiation);
	    } catch (Exception e) {
	        logger.error("Error while updating negotiable status for requirement ID: {} - {}", requirementId, e.getMessage()); // Use format specifiers
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
}
