import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
	providedIn: "root"
})
export class AuthenticationService {
	private medicalProfessionalSubject: BehaviorSubject<boolean>;
	private patientSubject: BehaviorSubject<boolean>;

	constructor() {
		this.medicalProfessionalSubject = new BehaviorSubject<boolean>(false);
		this.patientSubject = new BehaviorSubject<boolean>(false);
	}

	/**
	 * Returns an observable for the medical professional authentication state.
	 * @returns {Observable<boolean>} - The observable for the medical professional authentication state.
	 */
	authenticateAsMedicalProfessional(): Observable<boolean> {
		return this.medicalProfessionalSubject.asObservable();
	}

	/**
	 * Returns an observable for the patient authentication state.
	 * @returns {Observable<boolean>} - The observable for the patient authentication state.
	 */
	authenticateAsPatient(): Observable<boolean> {
		return this.patientSubject.asObservable();
	}

	/**
	 * Sets the authentication state for a medical professional. Also makes sure that patientSubject is false
	 * @param {boolean} isAuthenticated - The authentication state to set.
	 */
	setMedicalProfessionalAuthenticated(isAuthenticated: boolean): void {
		this.patientSubject = new BehaviorSubject<boolean>(false);
		this.medicalProfessionalSubject.next(isAuthenticated);
	}

	/**
	 * Sets the authentication state for a patient. Also makes sure that medicalProfessionalSubject is false
	 * @param {boolean} isAuthenticated - The authentication state to set.
	 */
	setPatientAuthenticated(isAuthenticated: boolean): void {
		this.medicalProfessionalSubject = new BehaviorSubject<boolean>(false);
		this.patientSubject.next(isAuthenticated);
	}

	/**
	 * Sets the authentication state to not authenticated for both medical professionals and patients.
	 * @returns {boolean} - Returns true if the user is not authenticated.
	 */
	setUserNotAuthenticated(): boolean {
		this.medicalProfessionalSubject.next(false);
		this.patientSubject.next(false);
		return true;
	}
}
