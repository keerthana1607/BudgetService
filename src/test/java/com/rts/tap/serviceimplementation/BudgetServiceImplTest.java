package com.rts.tap.serviceimplementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rts.tap.dao.BudgetDao;
import com.rts.tap.model.Budget;
import com.rts.tap.model.Requirement;
import com.rts.tap.model.SubRequirements;

class BudgetServiceImplTest {

    @Mock
    private BudgetDao budgetDao;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private Budget budget;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize a Budget instance with sample data
        Requirement requirement = new Requirement(); // Assuming you have a Requirement class
        SubRequirements subRequirement = new SubRequirements(); // Assuming you have a SubRequirements class
        
        budget = new Budget();
        budget.setBudgetId(1L);
        budget.setAnalyzedMinimumCTC(50000);
        budget.setAnalyzedMaximumCTC(70000);
        budget.setBudgetForResource(60000);
        budget.setRequirement(requirement);
        budget.setSubRequirementId(subRequirement);
        budget.setYearsOfExperience("5+");
        budget.setNegotiable(false);
    }

    @Test
    void testAddBudget() {
        when(budgetDao.save(any(Budget.class))).thenReturn(budget);
        
        Budget savedBudget = budgetService.addBudget(budget);
        
        assertNotNull(savedBudget);
        assertEquals(budget.getBudgetId(), savedBudget.getBudgetId());
        assertEquals(budget.getAnalyzedMinimumCTC(), savedBudget.getAnalyzedMinimumCTC());
        assertEquals(budget.getAnalyzedMaximumCTC(), savedBudget.getAnalyzedMaximumCTC());
        assertEquals(budget.getBudgetForResource(), savedBudget.getBudgetForResource());
        assertEquals(budget.getYearsOfExperience(), savedBudget.getYearsOfExperience());
        assertFalse(savedBudget.isNegotiable());
        verify(budgetDao, times(1)).save(any(Budget.class));
    }

    @Test
    void testGetAllBudgets() {
        when(budgetDao.findAll()).thenReturn(Collections.singletonList(budget));
        
        List<Budget> budgets = budgetService.getAllBudgets();
        
        assertNotNull(budgets);
        assertEquals(1, budgets.size());
        assertEquals(budget.getBudgetId(), budgets.get(0).getBudgetId());
        verify(budgetDao, times(1)).findAll();
    }

    @Test
    void testGetBudgetById() {
        when(budgetDao.findById(1L)).thenReturn(budget);
        
        Budget foundBudget = budgetService.getBudgetById(1L);
        
        assertNotNull(foundBudget);
        assertEquals(budget.getBudgetId(), foundBudget.getBudgetId());
        verify(budgetDao, times(1)).findById(1L);
    }

    @Test
    void testGetBudgetBySubRequirementId() {
        when(budgetDao.findBySubRequirementId(1L)).thenReturn(budget);
        
        Budget foundBudget = budgetService.getBudgetBySubRequirementId(1L);
        
        assertNotNull(foundBudget);
        assertEquals(budget.getBudgetId(), foundBudget.getBudgetId());
        verify(budgetDao, times(1)).findBySubRequirementId(1L);
    }

    @Test
    void testDeleteBudget() {
        doNothing().when(budgetDao).delete(1L);
        
        budgetService.deleteBudget(1L);
        
        verify(budgetDao, times(1)).delete(1L);
    }

    @Test
    void testUpdateBudget() {
        when(budgetDao.update(any(Budget.class))).thenReturn(budget);
        
        Budget updatedBudget = budgetService.updateBudget(budget);
        
        assertNotNull(updatedBudget);
        assertEquals(budget.getBudgetId(), updatedBudget.getBudgetId());
        verify(budgetDao, times(1)).update(any(Budget.class));
    }

    @Test
    void testGetBudgetByRequirementId() {
        when(budgetDao.findByRequirementId(1L)).thenReturn(Collections.singletonList(budget));
        
        List<Budget> budgets = budgetService.getBudgetByRequirementId(1L);
        
        assertNotNull(budgets);
        assertEquals(1, budgets.size());
        assertEquals(budget.getBudgetId(), budgets.get(0).getBudgetId());
        verify(budgetDao, times(1)).findByRequirementId(1L);
    }

    @Test
    void testUpdateNegotiableByRequirementId() {
        when(budgetDao.updateNegotiableByRequirementId(eq(1L), anyBoolean())).thenReturn(true);
        
        boolean updated = budgetService.updateNegotiableByRequirementId(1L, true);
        
        assertTrue(updated);
        verify(budgetDao, times(1)).updateNegotiableByRequirementId(eq(1L), anyBoolean());
    }
}