import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HealthDataService, HealthDataField } from "../../../services/health-data/health-data.service";
import { PatientService } from "../../../services/patient/patient.service";
import { environment } from "../../../../environments/environment";

@Component({
	selector: "app-health-data-list",
	standalone: true,
	imports: [CommonModule],
	templateUrl: "./health-data-list.component.html",
	styleUrls: ["./health-data-list.component.css"]
})
/**
 * @fileoverview HealthDataListComponent is responsible for displaying a list of health data fields for a selected patient.
 * It allows the user to select a specific health data field, which updates the graph and sidebar with the selected data.
 *
 * @module HealthDataListComponent
 * @description This component fetches and displays health data for a selected patient. It subscribes to the selected patient
 * from the PatientService and loads the corresponding health data using the HealthDataService. The component also emits an event
 * when a health data field is selected.
 *
 * @author Dannique klaver
 *
 */
export class HealthDataListComponent implements OnInit {
	/**
	 *  @property {string[]} healthDataFields - Array of health data field names.
	 * @property {string | null} selectedField - The currently selected health data field.
	 * @property {HealthDataField[]} formattedHealthData - Array of formatted health data fields.
	 * @property {string} patientName - The name of the selected patient.
	 */
	healthDataFields: string[] = [];
	selectedField: string | null = null;
	formattedHealthData: HealthDataField[] = [];
	public patientName: string = environment.MESSAGES.NO_PATIENT_SELECTED;

	@Output() fieldSelected = new EventEmitter<HealthDataField>();

	/**
	 * Creates an instance of HealthDataListComponent.
	 * @param {PatientService} patientService - Service to manage patient data.
	 * @param {HealthDataService} healthDataService - Service to manage health data.
	 */
	constructor(
		private patientService: PatientService,
		private healthDataService: HealthDataService
	) {}

	/**
	 * Initializes the component and subscribes to the selected patient.
	 */
	ngOnInit(): void {
		this.patientService.selectedPatient$.subscribe(patient => {
			if (patient?.bsn) {
				this.loadHealthData(patient.bsn);
			}
			if (patient?.name) {
				this.patientName = patient.name;
			}
		});
	}

	/**
	 * Loads health data for the given BSN.
	 * @param {number} bsn - The BSN of the patient.
	 */
	private loadHealthData(bsn: number): void {
		this.healthDataService.getHealthDataByBsn(bsn).subscribe({
			next: data => {
				if (data.length > 0) {
					this.formattedHealthData = data;
					// Get just the names for the buttons
					this.healthDataFields = this.formattedHealthData.map(item => item.name);
				}
			},
			error: error => {
				console.error(environment.MESSAGES.ERROR_FETCH_HEALTH, error);
			}
		});
	}

	/**
	 * Selects a health data field and emits the selected field data.
	 * @param {string} field - The name of the health data field to select.
	 */
	selectField(field: string): void {
		this.selectedField = field;
		const fieldData = this.formattedHealthData.find(item => item.name === field);
		if (fieldData) {
			this.healthDataService.setSelectedFieldData(fieldData);
			this.fieldSelected.emit(fieldData);
		}
	}
}
