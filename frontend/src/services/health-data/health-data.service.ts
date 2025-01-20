import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, catchError, map, Observable, of, tap } from "rxjs";
import { environment } from "../../../environments/environment";

/**
 * Interface representing a health data field.
 * this design is neccesary for the chart module
 */
export interface HealthDataField {
	name: string;
	series: { name: string; value: any }[];
}

interface HealthDataQuery {
	bsn: number;
}

@Injectable({
	providedIn: "root"
})
/**
 * @fileoverview HealthDataService is responsible for fetching and formatting health data for patients.
 * It provides methods to retrieve health data by BSN, format the data for display, and manage the selected health data field.
 *
 * @module HealthDataService
 * @description This service fetches and formats health data for patients. It provides methods to retrieve health data by BSN,
 * format the data for display, and manage the selected health data field.
 *
 * @author Dannique Klaver
 */
export class HealthDataService {
	private apiUrl = `${environment.API}/patient/health-data`;
	private selectedFieldDataSource = new BehaviorSubject<HealthDataField | null>(null);
	selectedFieldData$ = this.selectedFieldDataSource.asObservable();

	constructor(private http: HttpClient) {}

	httpOptions = {
		headers: new HttpHeaders({
			"Content-Type": "application/json"
		}),
		withCredentials: true
	};

	/**
	 * Fetches health data for a patient by their BSN.
	 * @param {number} bsn - The BSN of the patient.
	 * @returns {Observable<any[]>} - An observable containing the formatted health data.
	 */
	getHealthDataByBsn(bsn: number): Observable<any[]> {
		const query: HealthDataQuery = { bsn };
		return this.http.post<any[]>(this.apiUrl, query, this.httpOptions).pipe(
			catchError(error => {
				console.error(environment.MESSAGES.ERROR_FETCH_HEALTH, error);
				return of([]);
			}),
			map(data => this.formatHealthData(data))
		);
	}

	/**
	 * Formats a field name from camelCase to space-separated words.
	 * @param {string} field - The field name in camelCase.
	 * @returns {string} - The formatted field name.
	 */
	formatFieldName(field: string): string {
		return field
			.replace(/([A-Z])/g, " $1") // Add space before capital letters
			.replace(/^./, str => str.toUpperCase()); // Capitalize first letter
	}

	/**
	 * Formats health data for display.
	 * @param {any[]} data - The raw health data.
	 * @returns {any[]} - The formatted health data.
	 */
	formatHealthData(data: any[]): any[] {
		if (!data || data.length === 0) {
			return [];
		}

		const fields = Object.keys(data[0]).filter(field => !environment.EXCLUDE_FIELDS.includes(field));

		const formattedData = fields.map(field => ({
			name: this.formatFieldName(field),
			series: data.map(item => ({
				name: new Date(item.dateOfCollection ?? item.DateOfCollection).toLocaleDateString(),
				value: item[field]
			}))
		}));

		return formattedData;
	}

	/**
	 * Retrieves data by field name.
	 * @param {any[]} data - The health data.
	 * @param {string} fieldName - The display name of the field.
	 * @returns {any} - The data for the specified field.
	 */
	getDataByFieldName(data: any[], fieldName: string): any {
		// Convert display name back to camelCase for data access
		const originalFieldName = fieldName
			.toLowerCase()
			.replace(/\s(.)/g, str => str.toUpperCase())
			.replace(/\s/g, "")
			.replace(/^(.)/, str => str.toLowerCase());

		return {
			name: fieldName,
			series: data.map(item => ({
				name: new Date(item.dateOfCollection).toLocaleDateString(),
				value: item[originalFieldName]
			}))
		};
	}

	/**
	 * Sets the selected health data field.
	 * @param {HealthDataField} fieldData - The selected health data field.
	 */
	setSelectedFieldData(data: HealthDataField | null) {
		this.selectedFieldDataSource.next(data);
	}
}
