import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { catchError } from "rxjs/operators";
import { environment } from "../../../environments/environment";

@Injectable({
	providedIn: "root"
})
export class LogInService {
	private apiUrl = environment.API;

	/**
	 *
	 * @param http the HttpClient
	 */
	constructor(private http: HttpClient) {}

	/**
	 * The httpOptions for the requests
	 */
	httpOptions = {
		headers: new HttpHeaders({ "Content-Type": "application/json" }),
		withCredentials: true
	};

	/**
	 * Here the login of the user gets handled
	 * @param email the email of the user
	 * @param password the password of the user
	 * @returns an observable with the response
	 */
	// eslint-disable-next-line @typescript-eslint/no-explicit-any
	checkLogin(bsnOrEmail: string, password: string): Observable<any> {
		return this.http
			.post(`${this.apiUrl}/user/login`, { bsnOrEmail, password }, this.httpOptions)
			.pipe(catchError(this.handleError("checkLogin", [])));
	}

	/**
	 * Handle Http operation that failed.
	 * Let the app continue.
	 *
	 * @param operation - name of the operation that failed
	 * @param result - optional value to return as the observable result
	 */
	// eslint-disable-next-line @typescript-eslint/no-unused-vars
	private handleError<T>(operation = "operation", result?: T) {
		// eslint-disable-next-line @typescript-eslint/no-explicit-any
		return (error: any): Observable<T> => {
			// TODO: send the error to remote logging infrastructure
			console.error(error.error); // log to console instead
			// Let the app keep running by returning an empty result.
			if (!error.error.error) {
				error.error.error = "An Unknown Error Occurred";
			}
			return of(error.error as T);
		};
	}
}
