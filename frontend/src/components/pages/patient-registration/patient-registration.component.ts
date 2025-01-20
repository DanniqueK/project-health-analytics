import { Component, inject, model, signal } from "@angular/core";
import { faPassport, faSpinner, IconDefinition } from "@fortawesome/free-solid-svg-icons";
import { InputComponent } from "../../elements/buttons-&-input-elements/input/input.component";
import { FormGroup, ReactiveFormsModule } from "@angular/forms";
import { Location, NgIf, NgOptimizedImage } from "@angular/common";
import { ButtonComponent } from "../../elements/buttons-&-input-elements/button/button.component";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { ReturnButtonComponent } from "../../elements/buttons-&-input-elements/return-button/return-button.component";
import { RegistrationValidation } from "./user-form-validator";
import { PersonalFormComponent } from "./forms/personal-form/personal-form.component";
import { AddressFormComponent } from "./forms/address-form/address-form.component";
import { MobileFormComponent } from "./forms/mobile-form/mobile-form.component";
import { SaveNewPatientService } from "../../../services/save-new-patient/save-new-patient.service";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { MatDialog } from "@angular/material/dialog";
import { DialogBoxComponent } from "../../elements/dialog-box/dialog-box.component";

@Component({
	selector: "app-patient-registration",
	standalone: true,
	imports: [
		ReactiveFormsModule,
		FontAwesomeModule,
		NgOptimizedImage,
		ReturnButtonComponent,
		InputComponent,
		ButtonComponent,
		PersonalFormComponent,
		AddressFormComponent,
		MobileFormComponent,
		NgIf
	],
	templateUrl: "./patient-registration.component.html",
	styleUrl: "./patient-registration.component.css"
})
export class PatientRegistrationComponent {
	protected passportIcon: IconDefinition = faPassport;
	protected spinner: IconDefinition = faSpinner;
	protected processing: boolean = false;

	// Looks weird, but this is how angularmaterials does it
	private readonly dialog: MatDialog = inject(MatDialog);

	protected defaultValues;
	protected form: FormGroup;
	protected validation: RegistrationValidation;
	protected numbers: RegExp = /^\d+$/;

	constructor(
		protected readonly location: Location,
		protected readonly validatorform: RegistrationValidation,
		private readonly newPatientService: SaveNewPatientService
	) {
		this.form = validatorform.form;
		this.defaultValues = validatorform.form.value;
		this.validation = validatorform;
	}

	/**
	 * @method onSubmit runs all logic needed to send and recieve a POST request with the new patient data.
	 * - - -
	 * @author Marcus K.
	 */
	protected onSubmit(): void {
		this.form.markAllAsTouched();
		this.processing = true;

		// TODO Still create the object.
		if (this.form.valid)
			this.newPatientService.newPatient(this.form.value).subscribe({
				next: (response: HttpResponse<{ pass: string }>): void => this.goodResponse(response),
				error: (err: HttpErrorResponse): void => this.badResponse(err)
			});
		else {
			this.openDialog("The form is invalid, make sure you've filled in everything correctly.");
			this.processing = false;
		}
	}

	/**
	 * @method goodResponse opens up a Dialog box whenever the server returns a 2xx value, (hopefully) telling the MP the generated password, as well as resets the fields.
	 * @param response is the valid http response
	 * - - -
	 * @author Marcus K.
	 */
	private goodResponse(response: HttpResponse<{ pass: string }>): void {
		if (response.body)
			this.openDialog(
				"New patient has been saved successfully!",
				`The Patient's password is ${response.body.pass}. This will only be shown once, never again.`
			);
		else this.openDialog("New patient has been saved successfully, but server was unable to return a password.");
		this.processing = false;
	}

	/**
	 * @method goodResponse opens up a Dialog box whenever the server returns any other value, helping the MP figure out what went wrong server-side.
	 * @param err is the error http response
	 * - - -
	 * @author Marcus K.
	 */
	private badResponse(err: HttpErrorResponse): void {
		if (err.status == 400) {
			this.openDialog("Your input is invalid due to the following reason:", err.message);
		} else if (err.status == 403 || err.status == 401) {
			this.openDialog("You do not have permission to perform this action, try reauthenticating.");
		} else {
			this.openDialog("The server is unable to handle this request, please try again later.");
		}
		this.processing = false;
	}

	/**
	 * @method goodResponse uses the Angular Materials Dialog component to display messages on screen.
	 * @param message is the primary message
	 * @param reason is the secondary message or the reason why this message is here
	 * - - -
	 * @author Marcus K.
	 */
	private openDialog(message: string, reason?: string): void {
		this.dialog.open(DialogBoxComponent, {
			data: { message, reason },
			// Adding this so that an mp doesn't accidentally click this away.
			disableClose: true
		});
	}

	// For whatever reason, pressing enter on any formfield also triggers the return buttons
	// This isn't a great fix, and it doesn't fix the reverse happening with submit, but it works?
	weirdBugFix(event: KeyboardEvent): void {
		if (event.key === "Enter") {
			event.preventDefault();
		}
	}
}
