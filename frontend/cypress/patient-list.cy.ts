describe("Patient List Page", () => {
	beforeEach(() => {
		// Visit the patient list page before each test
		cy.visit("/mp-portal/patient-list");

    // With our new router, this test is broken. I hope you don't mind this fix. -M
    cy.intercept("GET", "user/role", {
      statusCode: 200,
      body: { userType: "medical_professional" }
    }).as("validCreds");
	});

	it("should search for a patient and show it", () => {
		// Intercept the API call and mock the response
		cy.intercept("GET", "/patient-list", {
			statusCode: 200,
			body: [
				{
					"Bsn": 234567890,
					"Name": "Emma Louise Johnson",
					"DateOfBirth": "1995-09-10T00:00:00"
				},
				{
					"Bsn": 987654321,
					"Name": "John Doe",
					"DateOfBirth": "1990-01-22T00:00:00"
				}
			]
		}).as("mockedList");

		cy.intercept("POST", "/patient-list", {
			statusCode: 200,
			body: [
				{
					"Bsn": 234567890,
					"Name": "Emma Louise Johnson",
					"DateOfBirth": "1995-09-10T00:00:00"
				}
			]
		}).as("mockedSearch");

		// Wait for the mock API request to complete
		cy.wait("@mockedList");

		cy.get('input[name="searchedPatient"]').type("Emma");

		cy.get("#searchButton").click();

		cy.wait("@mockedSearch");

		// Ensure that the table is rendered
		cy.get("table#table").should("be.visible");

		// Now check for the rows based on the correct class 'mat-mdc-row'
		cy.get("table#table tbody tr.mat-mdc-row").should("have.length", 1);

		// Check the contents of the rows to verify they match the mock data
		cy.contains("td.mat-mdc-cell", "Emma Louise Johnson").should("exist");
	});

	it("should show all patients when search is empty", () => {
		// Intercept the API call and mock the response
		cy.intercept("GET", "/patient-list", {
			statusCode: 200,
			body: [
				{
					"Bsn": 234567890,
					"Name": "Emma Louise Johnson",
					"DateOfBirth": "1995-09-10T00:00:00"
				},
				{
					"Bsn": 987654321,
					"Name": "John Doe",
					"DateOfBirth": "1990-01-22T00:00:00"
				}
			]
		}).as("mockedList");

		cy.intercept("POST", "/patient-list", {
			statusCode: 200,
			body: [
				{
					"Bsn": 234567890,
					"Name": "Emma Louise Johnson",
					"DateOfBirth": "1995-09-10T00:00:00"
				}
			]
		}).as("mockedSearch");

		// Wait for the mock API request to complete
		cy.wait("@mockedList");

		cy.get('input[name="searchedPatient"]').type("Emma");

		cy.get("#searchButton").click();

		cy.wait("@mockedSearch");

		// Ensure that the table is rendered
		cy.get("table#table").should("be.visible");

		cy.get('input[name="searchedPatient"]').clear();

		// Now check for the rows based on the correct class 'mat-mdc-row'
		cy.get("table#table tbody tr.mat-mdc-row").should("have.length", 2);

		// Check the contents of the rows to verify they match the mock data
		cy.contains("td.mat-mdc-cell", "Emma Louise Johnson").should("exist");
		cy.contains("td.mat-mdc-cell", "John Doe").should("exist");
	});
});
