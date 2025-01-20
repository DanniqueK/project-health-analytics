import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable, of } from "rxjs";
import { environment } from "../../../environments/environment";

/**
 * Interface representing a selected patient.
 */
export interface SelectedPatient {
	bsn?: number;
	name?: string;
}

interface SearchQuery {
	searchQuery: string;
}

@Injectable({
	providedIn: "root"
})
/**
 * @fileoverview PatientService is responsible for managing patient data, including fetching patient lists, searching for patients, and managing the selected patient.
 *
 * @module PatientService
 * @description This service manages patient data, including fetching patient lists, searching for patients, and managing the selected patient.
 *
 * @author Dannique Klaver
 * @author Dax Riool
 */
export class PatientService {
	private apiUrl = `${environment.API}/patient-list`;
	private selectedPatientSource = new BehaviorSubject<SelectedPatient | null>(null);
	selectedPatient$ = this.selectedPatientSource.asObservable();
	constructor(private http: HttpClient) {}

	httpOptions = {
		headers: new HttpHeaders({
			"Content-Type": "application/json"
		}),
		withCredentials: true
	};
	/**
	 * Sets the selected patient.
	 * @param {SelectedPatient} patient - The selected patient.
	 */
	setSelectedPatient(patient: SelectedPatient | null) {
		this.selectedPatientSource.next(patient);
	}

	/**
	 * Fetches the list of patients.
	 * @returns {Observable<any[]>} - An observable containing the list of patients.
	 */
	getPatients(): Observable<any[]> {
		return this.http.get<any[]>(this.apiUrl, this.httpOptions);
	}

	/**
	 * Searches for patients based on the search query.
	 * @param {string} searchQuery - The search query.
	 * @returns {Observable<any[]>} - An observable containing the search results.
	 */
	searchPatient(searchQuery: string): Observable<any[]> {
		const query: SearchQuery = { searchQuery };
		return this.http.post<any[]>(this.apiUrl, query, this.httpOptions);
	}

	/**
	 * Updates the data source with the given data.
	 * The data source is used to display the data in the table.
	 * @param {any[]} data - The data to update the data source with.
	 * @returns {any[]} - The updated data source.
	 */
	updateDataSource(data: any[]): any[] {
		return data.map(patient => {
			const name = patient.Name ?? patient.name ?? ""; // Handle both 'Name' and 'name'
			const bsn = patient.Bsn ?? patient.bsn; // Handle both 'Bsn' and 'bsn'
			const birth = (patient.DateOfBirth ?? patient.dateOfBirth ?? "").split("T")[0]; // Handle both 'DateOfBirth' and 'dateOfBirth'

			return {
				name,
				bsn,
				birth,
				view: "View"
			};
		});
	}
}
