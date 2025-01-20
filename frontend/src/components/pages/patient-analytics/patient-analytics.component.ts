import { Component, Input, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router } from "@angular/router";
import { HealthDataListComponent } from "../../elements/health-data-list/health-data-list.component";
import { PatientService, SelectedPatient } from "../../../services/patient/patient.service";
import { environment } from "../../../../environments/environment";
import { NgxChartsModule } from "@swimlane/ngx-charts";
import { PatientGraphsComponent } from "../../elements/patient-graphs/patient-graphs.component";
import { AnalyticsSidebarComponent } from "../../elements/analytics-sidebar/analytics-sidebar.component";

@Component({
	selector: "app-patient-analytics",
	standalone: true,
	imports: [CommonModule, HealthDataListComponent, PatientGraphsComponent, NgxChartsModule, AnalyticsSidebarComponent],
	templateUrl: "./patient-analytics.component.html",
	styleUrls: ["./patient-analytics.component.css"]
})
/**
 * @fileoverview PatientAnalyticsComponent is responsible for displaying patient analytics, including a list of health data fields and corresponding graphs.
 * It allows the user to select a specific health data field, which updates the graph and sidebar with the selected data.
 *
 * @module PatientAnalyticsComponent
 * @description This component fetches and displays patient analytics for a selected patient. It subscribes to the selected patient
 * from the PatientService and loads the corresponding health data using the HealthDataService. The component also handles the selection
 * of health data fields and updates the graph accordingly.
 *
 * @author Dannique Klaver
 * @author Rowin Schoon
 *
 */
export class PatientAnalyticsComponent implements OnInit {
	selectedPatient: SelectedPatient | null = null;
	selectedFieldData: any;
	errorMessage: string = "";

	/**
	 * Creates an instance of PatientAnalyticsComponent.
	 * @param {PatientService} patientService - Service to manage patient data.
	 * @param {Router} router - Router service for navigation.
	 */
	constructor(
		private patientService: PatientService,
		private router: Router
	) {}

	/**
	 * Initializes the component and subscribes to the selected patient.
	 */
	ngOnInit() {
		this.patientService.selectedPatient$.subscribe(patient => {
			this.selectedPatient = patient;
			if (!patient || patient.bsn === 0) {
				this.errorMessage = environment.MESSAGES.NO_PATIENT_SELECTED_ERROR;
			} else {
				this.errorMessage = "";
			}
		});
	}

	/**
	 * Handles the selection of a health data field and updates the selected field data.
	 * @param {any} fieldData - The data of the selected health field.
	 */
	onFieldSelected(fieldData: any) {
		this.selectedFieldData = fieldData;
	}
}
