describe("Login Page", () => {

  it("should display a success message on successful login", () => {

    cy.visit("/log-in");
    // Mock the backend response
    cy.intercept("POST", "/user/login", {
      statusCode: 200, // Simulate success response
      body: {
        userType: "medical_professional"
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

  it("should redirect to login on successful logout", () => {
    cy.visit("/mp-portal");

    // Mock the backend response for logout
    cy.intercept("DELETE", "/user/logout", {
      statusCode: 200 // Simulate success response
    }).as("mockedLogout");

    cy.intercept("GET", "/user/role", {
      statusCode: 200, // Simulate success response
      body: {
        userType: "medical_professional"
      }
    }).as("mockedRole");

    cy.wait("@mockedRole");

    // Open the mat-menu
    cy.get('button.userDropdown').click();

    // Simulate the button click for logout
    cy.get('a.logout').click();

    // Wait for the mocked request to be called
    cy.wait("@mockedLogout");

    // Check if the URL changes to the login page
    cy.url().should("equal", "http://localhost:4200/log-in");

    // Check if the navbar has only a link to the login page
    cy.get('nav').within(() => {
      cy.get('a').should('have.length', 1);
      cy.get('a').should('have.attr', 'href', '/log-in');
    });
  });
});