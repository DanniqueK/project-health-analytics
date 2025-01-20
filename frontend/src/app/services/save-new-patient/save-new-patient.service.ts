import { HttpClient, HttpHeaders, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../../environments/environment";

@Injectable({
	providedIn: "root"
})
export class SaveNewPatientService {
	// Interesting quirk about enviroment! It's automatically switching between development and not!
	private readonly API: string = environment.API ?? "http://localhost:80"; // wow thanks teammate for breaking that
	private readonly API_PATH: string = this.API + "/user/register/";
	private readonly HTTP_Headers: HttpHeaders = new HttpHeaders().set("content-type", "application/json");

	constructor(private readonly http: HttpClient) {}

	/**
	 * @method newPatient sends the patient object to the database and awaits a response with a password on success.
	 * @param patient is the new patient to be registered in our database.
	 * @returns a HttpResponse containing the new password.
	 */
	public newPatient(patient: object): Observable<HttpResponse<{ pass: string }>> {
		return this.http.post<{ pass: string }>(this.API_PATH, patient, {
			headers: this.HTTP_Headers,
			withCredentials: true,
			observe: "response"
		});
	}
}
