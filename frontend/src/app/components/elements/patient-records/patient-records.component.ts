import { Component, Input, Output, OnChanges, SimpleChanges, OnInit, EventEmitter } from "@angular/core";
import { PatientService } from "../../../services/patient/patient.service";
import { MatTableDataSource, MatTableModule } from "@angular/material/table";
import { Router, RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { environment } from "../../../../environments/environment";

@Component({
	selector: "app-patient-records",
	standalone: true,
	imports: [MatTableModule, RouterModule, CommonModule],
	templateUrl: "./patient-records.component.html",
	styleUrls: ["./patient-records.component.css"]
})
export class PatientRecordsComponent implements OnChanges, OnInit {
	@Input() searchedPatient: string = "";

	// Data source that binds to the table
	protected dataSource = new MatTableDataSource<any>();

	// Columns to display in the table
	protected displayedColumns: string[] = ["name", "birth", "view"];

	constructor(
		private patientService: PatientService,
		private router: Router
	) {}

	/**
	 * Fetches all patients when the component is initialized
	 */
	ngOnInit(): void {
		this.fetchAllPatients();
	}

	/**
	 * @param changes gets the emit from the search bar component
	 * if the search bar is empty, it fetches all patients
	 * if the search bar is not empty, it searches for the patient
	 */
	ngOnChanges(changes: SimpleChanges): void {
		if (changes["searchedPatient"]) {
			if (this.searchedPatient === "") {
				this.fetchAllPatients();
			} else {
				this.searchPatients(this.searchedPatient);
			}
		}
	}

	/**
	 * Fetches all patients from the backend
	 * @returns void
	 */
	fetchAllPatients(): void {
		this.patientService.getPatients().subscribe({
			next: data => {
				if (data.length === 0) {
					this.setDataSource(environment.MESSAGES.NO_ASSIGNED_PATIENTS, true);
				} else {
					this.setDataSource(this.patientService.updateDataSource(data), false);
				}
			},
			error: error => {
				console.error(environment.MESSAGES.ERROR_FETCH_PATIENT_MESSAGE, error);
				this.setDataSource(environment.MESSAGES.ERROR_FETCH_PATIENT_MESSAGE, true);
			}
		});
	}

	/**
	 * Searches for a patient with the given name
	 * @param searchedPatient The name of the patient to search for
	 * @returns void
	 */
	searchPatients(searchedPatient: string): void {
		this.patientService.searchPatient(searchedPatient).subscribe({
			next: data => {
				if (data.length === 0) {
					this.setDataSource(environment.MESSAGES.NO_PATIENT_FOUND, true);
				} else {
					this.setDataSource(this.patientService.updateDataSource(data), false);
				}
			},
			error: error => {
				console.error(environment.MESSAGES.ERROR_SEARCH_PATIENT, error);
				this.setDataSource(environment.MESSAGES.ERROR_FETCH_PATIENT_MESSAGE, true);
			}
		});
	}

	/**
	 * Sets the data source for the table
	 * @param data The data to set in the data source
	 * @param isError Whether the data represents an error message
	 */
	private setDataSource(data: any, isError: boolean): void {
		if (isError) {
			this.dataSource.data = [
				{
					name: data,
					birth: "",
					view: "",
					isError: true
				}
			];
		} else {
			this.dataSource.data = data;
		}
	}

	/**
	 * this emits the selected patient to the parent component
	 * @param bsn The BSN of the selected patient
	 */
	selectPatient(bsn: any, name: any): void {
		this.patientService.setSelectedPatient({ bsn, name });
		this.router.navigate(["/patient-portal/patient-analytics"]);
	}
}
