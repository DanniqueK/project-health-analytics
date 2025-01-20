import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import { ReactiveFormsModule, FormsModule, ControlContainer, FormGroupDirective } from "@angular/forms";
import { RegistrationValidation } from "../../../pages/patient-registration/user-form-validator";
import { MatTooltipModule } from "@angular/material/tooltip";

@Component({
	selector: "app-dropdown",
	standalone: true,
	imports: [CommonModule, ReactiveFormsModule, FormsModule, MatTooltipModule],
	templateUrl: "./dropdown.component.html",
	styleUrl: "./dropdown.component.css",
	viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]
})
export class DropdownComponent {
	@Input() public optionArray: string[] = ["UNSET"];
	@Input() public control: string = "";

	ValidatorForm: RegistrationValidation;

	constructor(ValidatorForm: RegistrationValidation) {
		this.ValidatorForm = ValidatorForm;
	}
}
