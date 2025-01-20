import { Injectable } from "@angular/core";
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from "@angular/forms";

@Injectable({
	providedIn: "root"
})
export class RegistrationValidation {
	// Not at all a great formGroup, I want to split it in three but I've only had issues with that.
	public form: FormGroup = new FormGroup({
		// Patient Data
		firstName: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		middleName: new FormControl(null, Validators.maxLength(255)),
		lastName: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		dateOfBirth: new FormControl(
			new Date(new Date().getTime() - new Date().getTimezoneOffset() * 60000).toISOString().split(".")[0],
			Validators.required
		),
		marrage: new FormControl("", [Validators.maxLength(255), Validators.required]),
		insurance: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),

		// Patient Address
		street: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		city: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		province: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		country: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		number: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(6)]),
		postalCode: new FormControl(null, [
			Validators.required,
			Validators.minLength(6),
			Validators.maxLength(7),
			// Has to be 6 to 7 characters, of which the first 4 are numbers, theres an optional space and the last two are not numbers.
			// There's also an issue with how angular is interpreting it, but I'll leave it be for now.
			Validators.pattern(/(?=.{6,7}$)(\d{4})(\s)?([a-zA-Z]{2})/)
		]),

		// Contact info
		phone: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]),
		contactName: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		contactPhone: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(20)]),

		// Identifiers
		BSN: new FormControl(null, [Validators.required, Validators.minLength(9), Validators.maxLength(9)]),
		userName: new FormControl(null, [Validators.required, Validators.minLength(1), Validators.maxLength(16)])
	});

	/**
	 * @method toolTipCreator runs methods to find an accompanying tool tip in case there's a form validation error
	 * @param control is the name of the control you want a tooltip from in the form above.
	 * @returns the first error message provided, if none are provided it defaults to the final message (being null)
	 * - - -
	 * @todo I might one day wanna make this re-usable by anyone with any form.
	 * @author Marcus K.
	 */
	public toolTipCreator(control: string): string | null {
		const validator: AbstractControl = this.form.controls[control];

		return (
			this.checkRequired(validator) ??
			this.checkMinLength(validator) ??
			this.checkMaxLength(validator) ??
			this.checkRegex(validator)
		);
	}

	/**
	 * @method checkRequired verifies if there's a "required" validation error, and responds pased upon that.
	 * @param validator is the control to validate upon
	 * @returns either a validation error message, or nothing at all
	 * - - -
	 * @author Marcus K.
	 */
	private checkRequired(validator: AbstractControl): string | null {
		const returnMessage: string = "This field cannot be empty";
		return validator.hasError("required") ? returnMessage : null;
	}

	/**
	 * @method checkMinLength verifies if there's a "minlength" validation error, and responds pased upon that.
	 * @param validator is the control to validate upon
	 * @returns either a validation error message, or nothing at all
	 * - - -
	 * @author Marcus K.
	 */
	private checkMinLength(validator: AbstractControl) {
		const mnLength: ValidationErrors | null = validator?.errors?.["minlength"];

		return mnLength
			? `Input does not its minimum required length of ${mnLength["requiredLength"]} and needs ${mnLength["requiredLength"] - mnLength["actualLength"]} more`
			: null;
	}

	/**
	 * @method checkMaxLength verifies if there's a "maxlength" validation error, and responds pased upon that.
	 * @param validator is the control to validate upon
	 * @returns either a validation error message, or nothing at all
	 * - - -
	 * @author Marcus K.
	 */
	private checkMaxLength(validator: AbstractControl): string | null {
		const mxLength: ValidationErrors | null = validator?.errors?.["maxlength"];

		return mxLength
			? `Input exceeds its required length of ${mxLength["requiredLength"]} by ${mxLength["actualLength"] - mxLength["requiredLength"]}`
			: null;
	}

	/**
	 * @method checkRegex verifies if there's a "pattern" validation error, and responds pased upon that.
	 * @param validator is the control to validate upon
	 * @returns either a validation error message, or nothing at all
	 * - - -
	 * @author Marcus K.
	 */
	private checkRegex(validator: AbstractControl): string | null {
		const mxLength: ValidationErrors | null = validator?.errors?.["pattern"];

		return mxLength ? `Input does not match any expected patterns` : null;
	}
}
