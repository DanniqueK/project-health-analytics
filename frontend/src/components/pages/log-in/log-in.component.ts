import { NgIf } from "@angular/common";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms"; // Import FormsModule
import { Router } from "@angular/router";
import { LogInService } from "../../../services/log-in/log-in.service";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { environment } from "../../../../environments/environment";

@Component({
	selector: "app-log-in",
	standalone: true,
	imports: [FormsModule, NgIf, MatInputModule, MatFormFieldModule],
	templateUrl: "./log-in.component.html",
	styleUrl: "./log-in.component.css"
})
export class LogInComponent {
	// The email or bsn of the user
	bsnOrEmail: string | null = null;
	// The password of the user
	password: string | null = null;
	// The error message element
	errorMessage: undefined | string = undefined;
	// The success message element
	succesMessage: undefined | string = undefined;

	constructor(
		private router: Router,
		private loginService: LogInService
	) {}

	ngOnInit() {}

	/**
	 * In this function the user input gets checked for errors
	 */
	checkUserInput() {
		// Check if the email and password are filled in
		if (!this.bsnOrEmail || !this.password) {
			this.errorMessage = environment.MESSAGES.FILL_OUT_FIELDS;
		} else if (this.checkEmailInput(this.bsnOrEmail) || this.checkBsnInput(this.bsnOrEmail)) {
			this.checkLogin(this.bsnOrEmail, this.password);
		} else {
			this.errorMessage = environment.MESSAGES.INVALID_EMAIL_BSN;
		}
	}

	/**
	 * Checks if the input contains only numbers.
	 * @param str The string to check.
	 * @returns True if the string contains only numbers, false otherwise.
	 */
	checkBsnInput(str: string): boolean {
		const numberPattern = /^\d+$/;
		if (numberPattern.test(str) && str.length == 9) {
			return true;
		}
		return false;
	}

	checkEmailInput(email: string): boolean {
		if (!email.includes(".") || !email.includes("@")) {
			return false;
		}
		return true;
	}

	/**
	 * @addition The function takes the response from the backend annd redirects them to the response page
	 * In this function the login gets checked
	 * @param email The email of the user
	 * @param password The password of the user
	 */
	checkLogin(bsnOrEmail: string, password: string) {
		this.loginService.checkLogin(bsnOrEmail, password).subscribe({
			next: (response: any) => {
				if (response.error != null) {
					this.errorMessage = response.error;
					this.succesMessage = undefined;
				} else {
					this.succesMessage = environment.MESSAGES.SUCCESS_REDIRECT;
					this.errorMessage = undefined;

					setTimeout(() => {
						this.redirectUser(response.userType);
					}, 1000);
				}
			}
		});
	}

	redirectUser(userType: string) {
		if (userType == "patient") {
			this.router.navigate(["/patient-portal"]);
		} else if (userType == "medical_professional") {
			this.router.navigate(["/mp-portal"]);
		}
	}
}
