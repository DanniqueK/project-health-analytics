import { HttpClient, HttpResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { environment } from "../../environments/environment";

@Injectable({
	providedIn: "root"
})
export class LogoutService {
	private url = environment.API + "/user/logout";

	constructor(
		private http: HttpClient,
		private router: Router
	) {}

	/**
	 * Logs out the user by making a DELETE request to the backend.
	 * Redirects the user to the login page after successful logout.
	 *
	 * @returns {Observable<any>} - An observable that completes when the logout request is finished.
	 */
	logOut(): Observable<any> {
		return this.http.delete(this.url, { withCredentials: true }).pipe(
			tap({
				next: () => {
					// Redirect the user after successful logout
					this.router.navigate(["/log-in"]);
				}
			})
		);
	}
}
