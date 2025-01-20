beforeEach((): void => {
	cy.visit("/mp-portal/register-patient");
	cy.intercept("GET", "user/role", {
		statusCode: 200,
		body: { userType: "medical_professional" }
	}).as("validCreds");
});

describe("[Goal] Not loading the page", (): void => {
	it("[Cannot] visit the address without the patient role", (): void => {
		cy.intercept("GET", "user/role", {
			statusCode: 403
		}).as("invalidCreds");

		cy.reload();
		cy.url().should("not.equal", "http://localhost:4200/mp-portal/register-patient");
	});
});

describe("[Goals] Loading the page", (): void => {
	it("[Can] visit the address with credentials", (): void => {
		// Lands on the correct page
		cy.title().should("equal", "ITM Patient Registration");

		// Note: Grabbing by the id is bad practice,
		// it would be better if I added data-cy="registration" to all of these components
		cy.get("app-return-button");
		cy.get("[id=infotext-title]").contains("ITM Portal");
		cy.get("[id=infotext-subtext]").invoke("text").should("not.be.empty");
	});

	it("[Can] load all elements necessary for functionality", (): void => {
		// Verifies that the core functionality components exist on the page
		cy.get("app-address-form");
		cy.get("app-mobile-form");

		cy.get('[data-cy="BSN"]').contains("BSN");
		cy.get('[data-cy="Username"]').contains("Username");

		cy.get('[id="cancel"]').contains("Cancel");
		cy.get('[id="save"]').contains("Save");
	});

	it("[Can] navigate to the prior page", (): void => {
		let url: Cypress.Chainable<string> = cy.url();
		cy.get('[id="cancel"] button').click();
		cy.url().should("not.equal", url);

		url = cy.url();
		cy.visit("/mp-portal/register-patient");
		cy.get("app-return-button button").click({ force: true });
		cy.url().should("not.equal", url);
	});
});

describe("[Goals] Filling in valid details in the form", (): void => {
	it("[Can] successfully fill in the form", (): void => {
		const returnedPass: string = "NewTestPassword";

		cy.fixture("registration").then(fixture => {
			cy.get('[type="submit"] button').should("be.disabled");
			{
				// filling in the personal details
				const element: string = "form app-personal-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const personal = fixture.valid.personal;
				form.get('[ng-reflect-name="firstName"]').type(personal.firstName);
				form.get('[ng-reflect-name="middleName"]').type(personal.middleName);
				form.get('[ng-reflect-name="lastName"]').type(personal.lastName);
				form.get('[ng-reflect-name="dateOfBirth"]').type(personal.dateOfBirth);
				form.get('[ng-reflect-name="marrage"]').select(personal.marrage);
				form.get('[ng-reflect-name="insurance"]').type(personal.insurance).blur();
				cy.wait(500);
				cy.get("form app-personal-form select").should("not.have.class", "invalid");
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the address details
				const element: string = "form app-address-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const address = fixture.valid.address;
				form.get('[ng-reflect-name="street"]').type(address.street);
				form.get('[ng-reflect-name="city"]').type(address.city);
				form.get('[ng-reflect-name="province"]').type(address.province);
				form.get('[ng-reflect-name="country"]').type(address.country);
				form.get('[ng-reflect-name="number"]').type(address.number);
				form.get('[ng-reflect-name="postalCode"]').type(address.postalCode).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the mobile phone details
				const element: string = "form app-mobile-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const mobile = fixture.valid.contact;
				form.get('[ng-reflect-name="phone"]').type(mobile.phone);
				form.get('[ng-reflect-name="contactName"]').type(mobile.contactName);
				form.get('[ng-reflect-name="contactPhone"]').type(mobile.contactPhone).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the mobile phone details
				const element: string = 'form [id="identifiers"] input';
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const identifiers = fixture.valid;
				form.get('[ng-reflect-name="BSN"]').type(identifiers.bsn);
				form.get('[ng-reflect-name="userName"]').type(identifiers.username).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			cy.get('[type="submit"] button').should("not.be.disabled");

			cy.intercept("POST", "user/register", {
				statusCode: 200,
				body: { pass: returnedPass }
			}).as("validResponse");

			// check if the pop-up is there also.
			cy.get('[type="submit"] button').click();

			// Verifies that it sends the correct data to the back-end
			cy.wait("@validResponse").then(xhr => {
				expect(xhr.request.body).to.deep.equal(fixture.validRequestBody);
			});

			cy.get("mat-dialog-content p")
				.eq(1)
				.contains(`The Patient's password is ${returnedPass}. This will only be shown once, never again.`);
		});
	});

	it("[Can] successfully fill in the form and handle the server being unable to send a pass", (): void => {
		cy.fixture("registration").then(fixture => {
			cy.get('[type="submit"] button').should("be.disabled");
			{
				// filling in the personal details
				const element: string = "form app-personal-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const personal = fixture.valid.personal;
				form.get('[ng-reflect-name="firstName"]').type(personal.firstName);
				form.get('[ng-reflect-name="middleName"]').type(personal.middleName);
				form.get('[ng-reflect-name="lastName"]').type(personal.lastName);
				form.get('[ng-reflect-name="dateOfBirth"]').type(personal.dateOfBirth);
				form.get('[ng-reflect-name="marrage"]').select(personal.marrage);
				form.get('[ng-reflect-name="insurance"]').type(personal.insurance).blur();
				cy.wait(500);
				cy.get("form app-personal-form select").should("not.have.class", "invalid");
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the address details
				const element: string = "form app-address-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const address = fixture.valid.address;
				form.get('[ng-reflect-name="street"]').type(address.street);
				form.get('[ng-reflect-name="city"]').type(address.city);
				form.get('[ng-reflect-name="province"]').type(address.province);
				form.get('[ng-reflect-name="country"]').type(address.country);
				form.get('[ng-reflect-name="number"]').type(address.number);
				form.get('[ng-reflect-name="postalCode"]').type(address.postalCode).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the mobile phone details
				const element: string = "form app-mobile-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const mobile = fixture.valid.contact;
				form.get('[ng-reflect-name="phone"]').type(mobile.phone);
				form.get('[ng-reflect-name="contactName"]').type(mobile.contactName);
				form.get('[ng-reflect-name="contactPhone"]').type(mobile.contactPhone).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			{
				// filling in the mobile phone details
				const element: string = 'form [id="identifiers"] input';
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
				const identifiers = fixture.valid;
				form.get('[ng-reflect-name="BSN"]').type(identifiers.bsn);
				form.get('[ng-reflect-name="userName"]').type(identifiers.username).blur();
				cy.wait(500); // Goes wayy too fast and hits a race condition. Bad fix I know.
				cy.get(element).each(el => {
					cy.wrap(el).should("not.have.class", "invalid");
				});
			}
			cy.get('[type="submit"] button').should("not.be.disabled");

			cy.intercept("POST", "user/register", {
				statusCode: 200
			}).as("semiValidResponse");

			// check if the pop-up is there also.
			cy.get('[type="submit"] button').click();

			cy.wait("@semiValidResponse");
			cy.get("mat-dialog-content p").contains(
				"New patient has been saved successfully, but server was unable to return a password."
			);
		});
	});

	it("[Can] see both a postal code with or without a space as valid", (): void => {
		cy.fixture("registration").then(fixture => {
			// filling in the address details
			const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get("form app-address-form");
			const address = fixture.valid.address;
			const input = form.get('[ng-reflect-name="postalCode"]');

			input.type(address.postalCodeShort).blur();
			cy.wait(500);
			input.should("not.have.class", "invalid");
			input.clear();
			input.type(address.postalCode).blur();
			cy.wait(500);
			input.should("not.have.class", "invalid");
		});
	});

	describe("[Goals] Filling in invalid details in the form", (): void => {
		it("[Cannot] submit an empty form", (): void => {
			cy.get('[type="submit"] button').should("be.disabled");
		});

		describe("[Goals] Showing a tooltip on invalid inputs", (): void => {
			it("[Can] show a required tooltip on empty required inputs", (): void => {
				cy.get('[type="submit"] button').should("be.disabled");
				const element: string = "form app-personal-form";
				const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);

				form.get('[ng-reflect-name="firstName"]').focus().blur().trigger("mouseenter");

				form.get('div [class="mat-mdc-tooltip-surface mdc-tooltip__surface"]').contains("This field cannot be empty");
				form.get('[type="submit"] button').should("be.disabled");
			});

			it("[Can] show a minimally required tooltip on inputs with too little input", (): void => {
				cy.get('[type="submit"] button').should("be.disabled");
				cy.fixture("registration").then(fixture => {
					const element: string = 'form [id="identifiers"] input';
					const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
					const invalid = fixture.invalid;

					form.get('[ng-reflect-name="BSN"]').type(invalid.BSNTooShort).blur().trigger("mouseenter");

					form
						.get('div [class="mat-mdc-tooltip-surface mdc-tooltip__surface"]')
						.contains("Input does not its minimum required length of 9 and needs 1 more");
					form.get('[type="submit"] button').should("be.disabled");
				});
			});

			it("[Can] show a maximally required tooltip on inputs with too much input", (): void => {
				cy.get('[type="submit"] button').should("be.disabled");
				cy.fixture("registration").then(fixture => {
					const element: string = "form app-personal-form";
					const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
					const invalid = fixture.invalid;

					form.get('[ng-reflect-name="firstName"]').type(invalid.firstNameTooLong).blur().trigger("mouseenter");

					form
						.get('div [class="mat-mdc-tooltip-surface mdc-tooltip__surface"]')
						.contains("Input exceeds its required length of 255 by 1");
					form.get('[type="submit"] button').should("be.disabled");
				});
			});

			it("[Can] show an invalid pattern tooltip on inputs that don't match the regex pattern", (): void => {
				cy.get('[type="submit"] button').should("be.disabled");
				cy.fixture("registration").then(fixture => {
					const element: string = "form app-address-form";
					const form: Cypress.Chainable<JQuery<HTMLElement>> = cy.get(element);
					const invalid = fixture.invalid;

					form.get('[ng-reflect-name="postalCode"]').type(invalid.postalCodeBadMatch).blur().trigger("mouseenter");

					form
						.get('div [class="mat-mdc-tooltip-surface mdc-tooltip__surface"]')
						.contains("Input does not match any expected patterns");
					form.get('[type="submit"] button').should("be.disabled");
				});
			});
		});
	});
});
