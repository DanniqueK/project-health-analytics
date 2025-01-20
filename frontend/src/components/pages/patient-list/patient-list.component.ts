import { Component } from "@angular/core";
import { SearchBarComponent } from "../../elements/search-bar/search-bar.component";
import { PatientRecordsComponent } from "../../elements/patient-records/patient-records.component";

@Component({
	selector: "app-patient-list",
	standalone: true,
	imports: [SearchBarComponent, PatientRecordsComponent],
	templateUrl: "./patient-list.component.html",
	styleUrl: "./patient-list.component.css"
})
export class PatientListComponent {
	searchedPatient: string = "";

	onSearch(searchedPatient: string) {
		this.searchedPatient = searchedPatient;
	}
}
