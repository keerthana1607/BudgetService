package com.rts.tap.constants;

public class APIConstants {
	
	private APIConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
	
	public static final String BASE_URL = "/tap";

	public static final String FRONT_END_URL = "http://localhost:3000";

	public static final String ADD_BUDGET_URL = "/budget/createbudget";
	public static final String GET_ALL_BUDGET_URL = "/budget/getallbudget";

	public static final String GET_BUDGET_BY_ID_URL = "/budget/getbudget/{id}";
	public static final String GET_BUDGET_BY_SUBREQUIREMENTID_URL = "/budget/getbudgetBySubrequirementId/{id}";
	public static final String UPDATE_BUDGET_URL = "/budget/updatebudget/{id}";

	public static final String DELETE_BUDGET_URL = "/budget/deletebudget/{id}";
	public static final String GET_BUDGET_BY_REQUIREMENTID_URL = "/budget/getBudgetByRequirementId/{requirementId}";
	public static final String UPDATENEGOTIATEDFIELD = "/budget/negotiable/{requirementId}";

}
