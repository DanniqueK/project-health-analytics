import { TestBed } from "@angular/core/testing";
import { RegistrationValidation } from "./user-form-validator";
import { AbstractControl, ValidationErrors } from "@angular/forms";

/**
 * @todo, as this method is currently made for the user-form, it cant work with other forms *yet*
 */
describe("SaveNewPatientService", () => {
	let service: RegistrationValidation;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(RegistrationValidation);
	});

	describe("[Method] toolTipCreator", (): void => {
		it("does not create a message when all requirements are met", (done: DoneFn) => {
			// arrange
			const exampleControl: string = "BSN";
			const setValue: string = "928473958";
			const expected: string | null = null;
			service.form.controls[exampleControl].setValue(setValue);

			// act
			const actual: string | null = service.toolTipCreator(exampleControl);

			// assert
			expect(actual).toBe(expected);
			done();
		});

		it("is able to create a message when a required field has no value", (done: DoneFn) => {
			// arrange
			const exampleControl: string = "firstName";
			const setValue: string = "";
			const expected: string | null = "This field cannot be empty";
			service.form.controls[exampleControl].setValue(setValue);

			// act
			const actual: string | null = service.toolTipCreator(exampleControl);

			// assert
			expect(actual).toBe(expected);
			done();
		});

		it("is able to create a message when field has less than it's minimum length", (done: DoneFn) => {
			// arrange
			const exampleControl: string = "postalCode";
			const setValue: string = "1402";
			const validator: AbstractControl = service.form.controls[exampleControl];
			validator.setValue(setValue);

			const minLength: ValidationErrors = validator?.errors?.["minlength"];
			const expected: string | null =
				`Input does not its minimum required length of ${minLength["requiredLength"]} and needs ${minLength["requiredLength"] - minLength["actualLength"]} more`;

			// act
			const actual: string | null = service.toolTipCreator(exampleControl);

			// assert
			expect(actual).toBe(expected);
			done();
		});

		it("is able to create a message when field has more than it's maximum length", (done: DoneFn) => {
			// arrange
			const exampleControl: string = "number";
			const setValue: string = "2098492843";
			const validator: AbstractControl = service.form.controls[exampleControl];
			validator.setValue(setValue);

			const maxLength: ValidationErrors = validator?.errors?.["maxlength"];
			const expected: string | null =
				`Input exceeds its required length of ${maxLength["requiredLength"]} by ${maxLength["actualLength"] - maxLength["requiredLength"]}`;

			// act
			const actual: string | null = service.toolTipCreator(exampleControl);

			// assert
			expect(actual).toBe(expected);
			done();
		});

		it("is able to create a message when field does not meet the regex requirements of a Control", (done: DoneFn) => {
			// arrange
			const exampleControl: string = "postalCode";
			const setValue: string = "&@(!$$";
			const validator: AbstractControl = service.form.controls[exampleControl];
			validator.setValue(setValue);

			const expected: string | null = `Input does not match any expected patterns`;

			// act
			const actual: string | null = service.toolTipCreator(exampleControl);

			// assert
			expect(actual).toBe(expected);
			done();
		});
	});
});
