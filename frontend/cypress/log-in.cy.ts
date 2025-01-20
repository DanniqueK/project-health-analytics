describe("Login Page", () => {
	beforeEach(() => {
		// Visit the login page before each test
		cy.visit("/login");

	});

	it("should display a success message on successful login", () => {

    // This intercept and the one below was broken. I hope you don't mind my fix -M
		// Mock the backend response
		cy.intercept("POST", "/user/login", {
			statusCode: 200, // Simulate success response
			body: {
				userType: "patient"
			}
		}).as("mockedLogin");

		// Fill in the form fields
		cy.get('input[name="bsnOrEmail"]').type("123456789");
		cy.get('input[name="password"]').type("password123");

		// Submit the form
		cy.get('input[type="submit"]').click();

		// Wait for the mocked request to be called
		cy.wait("@mockedLogin");

		// Check if the success message appears
		cy.get(".succesMessage").should("contain", "Success, You are being redirected!");
	});

	it("should display an error message on unsuccessful login", () => {
		// Mock the backend response
		cy.intercept("POST", "/user/login", {
			statusCode: 401, // Simulate unauthorized response
			body: {
				error: "Wrong user/password combination"
			}
		}).as("mockedLogin");

    // This one was also broken, hope you don't mind it. -M
		// Fill in the form fields
		cy.get('input[name="bsnOrEmail"]').type("123456789");
		cy.get('input[name="password"]').type("password123");

		// Submit the form
		cy.get('input[type="submit"]').click();

		// Wait for the mocked request to be called
		cy.wait("@mockedLogin");

		// Check if the error message appears
		cy.get(".errorMessage").should("contain", "Wrong user/password combination");
	});
});
