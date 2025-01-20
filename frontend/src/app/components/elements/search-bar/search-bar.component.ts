import { Component, EventEmitter, Output } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MatInputModule } from "@angular/material/input";
import { MatFormFieldModule } from "@angular/material/form-field";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { environment } from "../../../../environments/environment";

@Component({
	selector: "app-search-bar",
	standalone: true,
	imports: [FormsModule, MatInputModule, MatFormFieldModule, FontAwesomeModule],

	templateUrl: "./search-bar.component.html",
	styleUrl: "./search-bar.component.css"
})
export class SearchBarComponent {
	// The search icon
	fasearch = faSearch;
	// Output the search event
	@Output() search = new EventEmitter<string>();
	searchedPatient: string = "";
	errorMessage: string = "";

	constructor() {}

	/**
	 * This function is called when
	 * the input field changes.
	 * It emits the search event.
	 * @returns void
	 * @memberof SearchBarComponent
	 */
	onInputChange() {
		if (this.searchedPatient === "") {
			this.errorMessage = "";
			this.search.emit(this.searchedPatient);
		}
	}

	/**
	 * This function is called when
	 * the search button is clicked.
	 * It emits the search event.
	 * @returns void
	 * @memberof SearchBarComponent
	 */
	searchPatient() {
		if (this.searchedPatient === "") {
			this.errorMessage = environment.MESSAGES.ENTER_PATIENT_NAME;
		} else {
			this.errorMessage = "";
			this.search.emit(this.searchedPatient);
		}
	}

	/**
	 * This function is called when
	 * a key is pressed in the input field.
	 * It triggers the search if the Enter key is pressed.
	 * @param event The keyboard event
	 * @returns void
	 * @memberof SearchBarComponent
	 */
	onKeyDown(event: KeyboardEvent) {
		if (event.key === "Enter") {
			this.searchPatient();
		}
	}
}
