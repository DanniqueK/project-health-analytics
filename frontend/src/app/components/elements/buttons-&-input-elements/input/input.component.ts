import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import { ControlContainer, FormGroupDirective, FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RegistrationValidation } from "../../../pages/patient-registration/user-form-validator";
import { MatTooltipModule } from "@angular/material/tooltip";

@Component({
	selector: "app-input",
	standalone: true,
	imports: [CommonModule, ReactiveFormsModule, FormsModule, MatTooltipModule],
	templateUrl: "./input.component.html",
	styleUrl: "./input.component.css",
	viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]
})
export class InputComponent {
	// A bunch of options allowing people to optionally restrict this input, like in a normal input.
	@Input() public type: string = "text";
	@Input() public placeholder: string | undefined;
	@Input() public min: number | undefined;
	@Input() public max: number | undefined;
	@Input() public minlength: number | undefined;
	@Input() public maxlength: number | undefined;

	// By default allowing alphanumerical characters + a few foreign characters.
	// Could be a better default, but it's fine for now
	@Input() public regex: RegExp = /[一-龠]+|[ぁ-ゔ]+|[ァ-ヴー]+|[a-zA-Z0-9]+|[ａ-ｚＡ-Ｚ０-９]+|[々〆〤ヶ]+/u;

	@Input() public control: string = "";
	protected ValidatorForm: RegistrationValidation;

	constructor(ValidatorForm: RegistrationValidation) {
		this.ValidatorForm = ValidatorForm;
	}

	/**
	 * @method regexCheck allows or disallowes user input if it invalidates a provided regex.
	 * @param event
	 * - - -
	 * @todo essentially runs on all forms, even without a regex. Might tweak this to not run unneeded logic
	 * @author Marcus K.
	 */
	protected regexCheck(event: KeyboardEvent): void {
		const regex: RegExp = new RegExp(this.regex);
		const currentInput: string = this.ValidatorForm.form.controls[this.control].value ?? "";
		const cursorPos: number = (event.target as HTMLInputElement).selectionStart!; // Will always be there
		const newInput: string = currentInput.slice(0, cursorPos) + event.key + currentInput.slice(cursorPos);

		const valid: boolean = regex.test(newInput);
		if (valid === false) event.preventDefault();
	}
}
